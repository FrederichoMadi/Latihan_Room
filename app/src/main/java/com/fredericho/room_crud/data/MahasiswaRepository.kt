package com.fredericho.room_crud.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.fredericho.room_crud.data.dao.MahasiswaDao
import com.fredericho.room_crud.data.database.MahasiswaDatabase
import com.fredericho.room_crud.data.entity.Mahasiswa

class MahasiswaRepository (private val dao : MahasiswaDao) {

    companion object {
        @Volatile
        private var instance : MahasiswaRepository? = null

        fun getInstance(context: Context) : MahasiswaRepository? {
            return instance ?: synchronized(MahasiswaRepository::class.java){
                if (instance == null){
                    val database = MahasiswaDatabase.getInstance(context)
                    instance = MahasiswaRepository(database.mahasiswaDao())
                }
                return instance
            }
        }
    }

    fun getAllData() : LiveData<List<Mahasiswa>> {
        return dao.getAllData()
    }

    fun getDataById(id : Int) : LiveData<Mahasiswa>{
        return dao.getDataById(id)
    }

    fun searcData(name : String) : LiveData<Mahasiswa> {
        return dao.searchData(name)
    }

    suspend fun insertData(mahasiswa: Mahasiswa) = dao.insertData(mahasiswa)

    suspend fun deleteData(mahasiswa: Mahasiswa) = dao.deleteData(mahasiswa)

    suspend fun upadteData(mahasiswa: Mahasiswa) = dao.updateData(mahasiswa)

}