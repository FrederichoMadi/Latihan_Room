package com.fredericho.room_crud.data.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.BLOB
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Entity
@Parcelize
data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val id : Int = 0,

    @NotNull
    @ColumnInfo(name = "name")
    val name : String,

    @NotNull
    @ColumnInfo(name = "nim")
    val nim : String,

    @NotNull
    @ColumnInfo(name = "image")
    val image : Bitmap,

    @NotNull
    @ColumnInfo(name = "semester")
    val semester : String,

    @NotNull
    @ColumnInfo(name = "description")
    val description : String
) : Parcelable