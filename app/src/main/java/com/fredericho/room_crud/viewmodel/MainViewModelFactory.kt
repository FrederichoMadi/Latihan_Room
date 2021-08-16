package com.fredericho.room_crud.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fredericho.room_crud.data.MahasiswaRepository
import com.fredericho.room_crud.ui.add.AddViewModel
import com.fredericho.room_crud.ui.detail.DetailViewModel
import com.fredericho.room_crud.ui.home.MainViewModel
import java.lang.IllegalStateException
import java.lang.reflect.InvocationTargetException

class MainViewModelFactory(private val repository: MahasiswaRepository) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        var instance : MainViewModelFactory? = null

        fun getInstance(context : Context) : MainViewModelFactory =
            instance ?: synchronized(this){
                instance ?: MainViewModelFactory(
                    MahasiswaRepository.getInstance(context)!!
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}