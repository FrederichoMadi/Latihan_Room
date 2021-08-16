package com.fredericho.room_crud.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.fredericho.room_crud.R
import com.fredericho.room_crud.data.entity.Mahasiswa
import com.fredericho.room_crud.databinding.ActivityDetailBinding
import com.fredericho.room_crud.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel : DetailViewModel

    //property
    private var dataId : Int = 0
    private var fileUri : Uri? = null
    private lateinit var imageData : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        dataId = intent.getIntExtra(EXTRA_DATA, 0)

        viewModel.setDataId(dataId)
        viewModel.mahasiswaId.observe(this) { mahasiswa ->
            if (mahasiswa != null){
                with(binding){
                    edtName.setText(mahasiswa.name)
                    edtNim.setText(mahasiswa.nim)
                    edtSemester.setText(mahasiswa.semester)
                    edtDescription.setText(mahasiswa.description)
                    Glide.with(this@DetailActivity)
                        .load(mahasiswa.image)
                        .circleCrop()
                        .into(ivAddImage)

                    imageData = mahasiswa.image
                }
            }
        }

        binding.ivAddImage.setOnClickListener {
            val image = Intent()
            image.setType("image/*")
            image.setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(image, "Select Picture"), 1)
        }

        binding.btnSave.setOnClickListener {
            updateData()
        }

    }

    private fun updateData() {
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
            viewModel.mahasiswaId.observe(this@DetailActivity) {
                if (it != null){
                    lifecycleScope.launch {
                    val mahasiswa = Mahasiswa(
                        id = it.id,
                        name = name,
                        nim = nim,
                        semester = semester,
                        description = description,
                        image = getBitmap()
                    )
                        viewModel.updateData(mahasiswa)
                    }
                }
            Log.d("AddActivity", "onSuccess : Success add data")
        }
        Toast.makeText(this, "Data baru berhasil diubah", Toast.LENGTH_SHORT).show()
        onBackPressed()

    }

    private suspend fun getBitmap() : Bitmap {
        Log.d("DetailActivity", "ImageBitmap : $imageData")
        return if (fileUri == null){
            val loading = ImageLoader(this)
            val request = ImageRequest.Builder(this)
                .data(imageData)
                .build()
            val result = (loading.execute(request) as SuccessResult).drawable
            (result as BitmapDrawable).bitmap
        } else {
            val loading = ImageLoader(this)
            val request = ImageRequest.Builder(this)
                .data(fileUri)
                .build()
            val result = (loading.execute(request) as SuccessResult).drawable
            (result as BitmapDrawable).bitmap
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_delete -> {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Hapus data")
                    .setMessage("Yakin Hapus data ini?")
                    .setPositiveButton("Ya") { _, _ ->
                        viewModel.deleteData()
                        onBackPressed()
                    }
                    .setNegativeButton("Tidak"){dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK){
            fileUri = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
            Glide.with(this)
                .load(bitmap)
                .circleCrop()
                .into(binding.ivAddImage)
        }
    }
}