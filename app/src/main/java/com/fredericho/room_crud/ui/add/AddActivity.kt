package com.fredericho.room_crud.ui.add

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.fredericho.room_crud.data.entity.Mahasiswa
import com.fredericho.room_crud.databinding.ActivityAddBinding
import com.fredericho.room_crud.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBinding
    private lateinit var viewModel : AddViewModel

    private lateinit var fileUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddViewModel::class.java]

        binding.ivAddImage.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }


        binding.btnSave.setOnClickListener {
            addData()
        }

    }

    private fun addData() {
        with(binding){
            val name = edtName.text.toString()
            val nim = edtNim.text.toString()
            val semester = edtSemester.text.toString()
            val description = edtDescription.text.toString()

            when{
                name.isEmpty() -> {
                    edtName.error = "Field ini kosong"
                    edtName.requestFocus()
                }
                nim.isEmpty() -> {
                    edtNim.error = "Field ini kosong"
                    edtNim.requestFocus()
                }
                semester.isEmpty() -> {
                    edtSemester.error = "Field ini kosong"
                    edtSemester.requestFocus()
                }
                description.isEmpty() -> {
                    edtDescription.error = "Field ini kosong"
                    edtDescription.requestFocus()
                }
                else -> {
                    saveData(name, nim, semester, description)
                }
            }

        }
    }

    private fun saveData(
        name: String,
        nim: String,
        semester: String,
        description: String
    ) {

        lifecycleScope.launch {
            val mahasiswa = Mahasiswa(
                name = name,
                nim = nim,
                semester = semester,
                description = description,
                image = getBitmap()
            )
            viewModel.insertData(mahasiswa)
            Log.d("AddActivity", "onSuccess : Success add data")
        }
        Toast.makeText(this, "Data baru berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        onBackPressed()

    }

    private suspend fun getBitmap() : Bitmap {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(fileUri)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK){
            fileUri = data?.data!!
            val fileUri = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
            Glide.with(this)
                .load(fileUri)
                .circleCrop()
                .into(binding.ivAddImage)
        } else {
            Toast.makeText(this, "Cancel add Image", Toast.LENGTH_SHORT).show()
        }
    }
}