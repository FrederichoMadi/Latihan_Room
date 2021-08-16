package com.fredericho.room_crud.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredericho.room_crud.data.MahasiswaRepository
import com.fredericho.room_crud.data.entity.Mahasiswa
import kotlinx.coroutines.launch

class AddViewModel(private val repository: MahasiswaRepository) : ViewModel() {

    fun insertData(mahasiswa : Mahasiswa) =
        viewModelScope.launch {
            repository.insertData(mahasiswa)
        }


}