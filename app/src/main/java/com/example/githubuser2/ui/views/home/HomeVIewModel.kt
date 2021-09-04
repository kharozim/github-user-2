package com.example.githubuser2.ui.views.home

import androidx.lifecycle.*
import com.example.githubuser2.model.responses.UserResponse
import com.example.githubuser2.repository.contract.UserContractRepo
import com.example.githubuser2.repository.local.UserEntity
import kotlinx.coroutines.launch

class HomeViewModelFactory(private val repository: UserContractRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeVIewModel(repository) as T
    }
}

class HomeVIewModel(
    private val repository: UserContractRepo
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _loading

    private fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            try {
                _loading.postValue(isLoading)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _userList = MutableLiveData<UserResponse>()
    val userList: LiveData<UserResponse>
        get() = _userList

    fun searchUser(username: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = repository.searchUser(username)
                _userList.postValue(response)
                setLoading(false)
            } catch (e: Exception) {
                setLoading(false)
                e.printStackTrace()
            }
        }
    }
}
