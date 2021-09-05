package com.example.githubuser2.ui.views.detail

import android.content.Context
import androidx.lifecycle.*
import com.example.githubuser2.R
import com.example.githubuser2.model.responses.UserItem
import com.example.githubuser2.model.responses.toUserEntity
import com.example.githubuser2.repository.contract.UserContractRepo
import com.example.githubuser2.repository.local.UserEntity
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModelFactory(private val repository: UserContractRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}

class DetailViewModel(
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

    private val _user = MutableLiveData<UserItem>()
    val user: LiveData<UserItem>
        get() = _user

    fun getDetailUser(username: String) {
        setLoading(true)
        Timber.tag("getDetailUser").d("init")
        viewModelScope.launch {
            try {
                val response = repository.getDetailUser(username)
                _user.postValue(response)
                Timber.tag("getDetailUser").d("success : $response")
                setLoading(false)

            } catch (e: Exception) {
                e.printStackTrace()
                Timber.tag("getDetailUser").d("error : $e")
                setLoading(false)
            }
        }
    }

    private val _followers = MutableLiveData<List<UserItem>>()
    val followers: LiveData<List<UserItem>>
        get() = _followers

    fun getUserFollowers(username: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = repository.getUserFollower(username)
                _followers.postValue(response)
                setLoading(false)
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.tag("getUserFollowers").d("error : ${e.localizedMessage}")
                setLoading(false)
            }
        }
    }


    private val _following = MutableLiveData<List<UserItem>>()
    val following: LiveData<List<UserItem>>
        get() = _following

    fun getUserFollowing(username: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = repository.getUserFollowing(username)
                _following.postValue(response)
                setLoading(false)
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.tag("getUserFollowing").d("error : ${e.localizedMessage}")
                setLoading(false)
            }
        }
    }

    private val _addFav = MutableLiveData<String>()
    val onAddFav: LiveData<String>
        get() = _addFav

    fun addFavorite(context: Context, userItem: UserItem) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val request = userItem.toUserEntity()
                repository.insertUserFav(request)
                _addFav.postValue(context.getString(R.string.success_add_fav))
                Timber.tag("addUserFav").d("Success")
                setLoading(false)
            } catch (e: Exception) {
                e.printStackTrace()
                _addFav.postValue("Error, ${e.localizedMessage}")
                Timber.tag("addUserFav").d("error : ${e.localizedMessage}")
                setLoading(false)
            }
        }
    }

    private val _removeFav = MutableLiveData<String>()
    val onRemoveFav: LiveData<String>
        get() = _removeFav

    fun removeFavorite(context: Context, userId: Int) {
        setLoading(true)
        viewModelScope.launch {
            try {
                repository.deleteUserFav(userId)
                _removeFav.postValue(context.getString(R.string.success_remove_fav))
                Timber.tag("removeUserFav").d("Success")
                setLoading(false)
            } catch (e: Exception) {
                e.printStackTrace()
                _removeFav.postValue("Error, ${e.localizedMessage}")
                Timber.tag("removeUserFav").d("error : ${e.localizedMessage}")
                setLoading(false)
            }
        }
    }

    private val _findUserFav = MutableLiveData<UserEntity?>()
    val onFindFav: LiveData<UserEntity?>
        get() = _findUserFav

    fun findUserFavorite(userId: Int) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = repository.findUserFav(userId)
                _findUserFav.postValue(response)
                setLoading(false)
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.tag("removeUserFav").d("error : ${e.localizedMessage}")
                setLoading(false)
            }
        }
    }


}