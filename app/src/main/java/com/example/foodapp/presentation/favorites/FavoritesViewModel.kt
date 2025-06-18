package com.example.foodapp.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.usecase.GetFavoriteRecipesUseCase
import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.usecase.GetFavoritesSortedByDateAscUseCase
import com.example.foodapp.domain.usecase.GetFavoritesSortedByDateDescUseCase
import com.example.foodapp.domain.usecase.GetFavoritesSortedByNameAscUseCase
import com.example.foodapp.domain.usecase.GetFavoritesSortedByNameDescUseCase
import com.example.foodapp.domain.usecase.ToggleFavoriteRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase,
    private val getFavoritesSortedByNameAscUseCase: GetFavoritesSortedByNameAscUseCase,
    private val getFavoritesSortedByNameDescUseCase: GetFavoritesSortedByNameDescUseCase,
    private val getFavoritesSortedByDateAscUseCase: GetFavoritesSortedByDateAscUseCase,
    private val getFavoritesSortedByDateDescUseCase: GetFavoritesSortedByDateDescUseCase,
) : ViewModel() {

    enum class SortType {
        DEFAULT,
        NAME_ASC,
        NAME_DESC,
        DATE_ASC,
        DATE_DESC
    }

    private val _sortType = MutableStateFlow(SortType.DEFAULT)

    val sortTypeLabel: StateFlow<String> = _sortType.map { sortType ->
        when(sortType) {
            SortType.DEFAULT -> "Random"
            SortType.NAME_ASC -> "Name Ascending"
            SortType.NAME_DESC -> "Name Descending"
            SortType.DATE_ASC -> "Date Ascending"
            SortType.DATE_DESC -> "Date Descending"
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, "Default")

    @OptIn(ExperimentalCoroutinesApi::class)
    val favoriteRecipes: StateFlow<List<RecipeDomainModel>> = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.DEFAULT -> getFavoriteRecipesUseCase.invokeStream(Unit)
                SortType.NAME_ASC -> getFavoritesSortedByNameAscUseCase.invokeStream(Unit)
                SortType.NAME_DESC -> getFavoritesSortedByNameDescUseCase.invokeStream(Unit)
                SortType.DATE_ASC -> getFavoritesSortedByDateAscUseCase.invokeStream(Unit)
                SortType.DATE_DESC -> getFavoritesSortedByDateDescUseCase.invokeStream(Unit)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSortType(type: SortType) {
        Log.d("FavoritesViewModel", "Sort type changed: $type")
        _sortType.value = type
    }

    fun removeFavoriteRecipe(recipe: RecipeDomainModel) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)
        }
    }
}
