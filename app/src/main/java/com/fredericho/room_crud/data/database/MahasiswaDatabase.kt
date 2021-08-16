package com.fredericho.room_crud.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fredericho.room_crud.Converter
import com.fredericho.room_crud.data.dao.MahasiswaDao
import com.fredericho.room_crud.data.entity.Mahasiswa

@Database( entities = [Mahasiswa::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MahasiswaDatabase : RoomDatabase() {
    abstract fun mahasiswaDao() : MahasiswaDao

    companion object{

        @Volatile
        private var instance : MahasiswaDatabase? = null

        fun getInstance(context: Context) : MahasiswaDatabase{
            return synchronized(this){
                 instance ?: Room.databaseBuilder(
                    context,
                    MahasiswaDatabase::class.java,
                    "mahasiswa.db"
                ).build()
            }
        }
    }
}