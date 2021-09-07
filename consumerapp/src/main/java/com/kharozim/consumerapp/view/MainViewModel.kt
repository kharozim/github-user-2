package com.kharozim.consumerapp.view

import DBContract
import MapperUtil
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kharozim.consumerapp.model.UserModel

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<UserModel>>()
    val listUser: LiveData<List<UserModel>>
        get() = _listUser

    fun getAllUserFav(context: Context) {
        val cursor = context.contentResolver.query(
            DBContract.FavUserColums.CONTENT_URI,
            null, null, null, null
        )

        val listConvert = MapperUtil.cursorToArraylist(cursor)
        _listUser.postValue(listConvert)
    }

}