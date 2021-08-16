package com.fredericho.room_crud.ui.detail

import androidx.lifecycle.*
import com.fredericho.room_crud.data.MahasiswaRepository
import com.fredericho.room_crud.data.entity.Mahasiswa
import kotlinx.coroutines.launch

class DetailViewModel(private val repository : MahasiswaRepository) : ViewModel() {

    private var _dataId = MutableLiveData<Int>()

    private var _data = _dataId.switchMap {
        repository.getDataById(it)
    }

    val mahasiswaId : LiveData<Mahasiswa> = _data
    
    fun setDataId(id : Int){
        if (id == _dataId.value){
            return
        }
        _dataId.value = id
    }

    fun updateData(mahasiswa: Mahasiswa) =
        viewModelScope.launch {
            repository.upadteData(mahasiswa)
        }

    fun deleteData() {
        viewModelScope.launch {
            mahasiswaId.value.let {
                repository.deleteData(it!!)
            }
        }
    }

}