package com.fredericho.room_crud.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fredericho.room_crud.data.entity.Mahasiswa

@Dao
interface MahasiswaDao {

    @Query("SELECT * FROM mahasiswa")
    fun getAllData() : LiveData<List<Mahasiswa>>

    @Query("SELECT * FROM mahasiswa WHERE id = :id")
    fun getDataById(id : Int) : LiveData<Mahasiswa>

    @Query("SELECT * FROM mahasiswa WHERE name LIKE :name ")
    fun searchData(name : String) : LiveData<Mahasiswa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(mahasiswa: Mahasiswa)

    @Delete
    suspend fun deleteData(mahasiswa: Mahasiswa)

    @Update
    suspend fun updateData(mahasiswa: Mahasiswa)
}