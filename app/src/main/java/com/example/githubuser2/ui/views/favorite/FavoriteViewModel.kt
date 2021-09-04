package com.example.githubuser2.ui.views.favorite

import androidx.lifecycle.*
import com.example.githubuser2.repository.contract.UserContractRepo
import com.example.githubuser2.repository.local.UserEntity
import kotlinx.coroutines.launch

class FavoriteViewModelFactory(private val repository: UserContractRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}

class FavoriteViewModel(
    private val repository: UserContractRepo
) : ViewModel() {

    private val _userFav = MutableLiveData<List<UserEntity>>()
    val userFav: LiveData<List<UserEntity>>
        get() = _userFav

    fun getAllUserFav() {
        viewModelScope.launch {
            val allUserFav = repository.getAllUserFav()
            _userFav.postValue(allUserFav)
        }
    }

}