package com.andersen.rick_and_morty.ui.characterList

import androidx.lifecycle.*
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.ChatacterFilters
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.usecase.character.GetCharacterListUseCase
import com.andersen.rick_and_morty.domain.usecase.character.GetCharactersDaoUseCase
import com.andersen.rick_and_morty.domain.usecase.character.SaveCharacterUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class ListCharacterViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val getCharactersDaoUseCase: GetCharactersDaoUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase,
) : ViewModel() {

    private val filter = ChatacterFilters("", "", "", "", "")
    private val filterQuery = MutableStateFlow(filter)


    private var currentPage = 1
    private var isLoading = false

    private val loadCharactersFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val mutableState = MutableStateFlow<LceState<List<Character>>>(LceState.Loading)
    val state: StateFlow<LceState<List<Character>>>
        get() = mutableState


    val charactersFlow = filterQuery.onEach {
            currentPage = 1
            isLoading = false
        }.flatMapLatest { query ->
            characterDataFlow(query)
        }.onStart {
            onLoadCharacters()
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    fun onLoadCharacters() {
        loadCharactersFlow.tryEmit(Unit)
    }

    fun onFilterChanged(filter: ChatacterFilters) {
        filterQuery.value = filter

    }

    fun getFilter(): ChatacterFilters {
        return filterQuery.value
    }

    private fun characterDataFlow(filterQuery: ChatacterFilters): Flow<List<Character>> {
        return loadCharactersFlow.filter { !isLoading }.onEach {
                isLoading = true
                mutableState.value = LceState.Loading
            }.map {
                getCharacterListUseCase.invoke(filterQuery, currentPage).apply { isLoading = false }
                    .fold(onSuccess = {
                        saveCharacterUseCase.invoke(it)
                        currentPage++
                        val filteredData = it.filter { character ->
                            val nameMatches = character.name!!.contains(
                                filterQuery.namePrefix,
                                ignoreCase = true
                            )
                            nameMatches
                        }
                        mutableState.value = LceState.Content(filteredData)

                        filteredData

                    }, onFailure = {
                        mutableState.value = LceState.Error(it)
                        emptyList()

                    })
            }.onStart {
                val filteredData = getCharactersDaoUseCase.invoke(filterQuery).filter { character ->
                    val nameMatches =
                        character.name!!.contains(filterQuery.namePrefix, ignoreCase = true)
                    nameMatches
                }
                emit(filteredData)


            }.runningReduce { accumulator, value ->
                accumulator + value
            }.scan(emptyList<Character>()) { accumulator, value ->
                val newElements = value.filter { newValue ->
                    !accumulator.any { it.id == newValue.id }
                }
                accumulator + newElements
            }.distinctUntilChanged()
    }

    fun setSearchQuery(query: String) {
        val currentFilter = filterQuery.value
        filterQuery.value = currentFilter.copy(namePrefix = query)
    }

}
