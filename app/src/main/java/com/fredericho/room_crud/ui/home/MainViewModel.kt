package com.fredericho.room_crud.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.fredericho.room_crud.data.MahasiswaRepository
import com.fredericho.room_crud.data.entity.Mahasiswa

class MainViewModel(private val repository : MahasiswaRepository) : ViewModel() {

    fun getAllData() : LiveData<List<Mahasiswa>> = repository.getAllData()

}