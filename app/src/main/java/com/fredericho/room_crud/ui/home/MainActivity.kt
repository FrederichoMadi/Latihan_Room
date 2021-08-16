package com.fredericho.room_crud.ui.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fredericho.room_crud.R
import com.fredericho.room_crud.data.entity.Mahasiswa
import com.fredericho.room_crud.databinding.ActivityMainBinding
import com.fredericho.room_crud.ui.add.AddActivity
import com.fredericho.room_crud.ui.detail.DetailActivity
import com.fredericho.room_crud.ui.home.MainViewModel
import com.fredericho.room_crud.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : DataAdapter
    private lateinit var viewModel : MainViewModel

    //atribut
    private var listData = mutableListOf<Mahasiswa>()
    private var ivImage : ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivImage = findViewById(R.id.iv_add_image)

        val factory = MainViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.getAllData().observe(this) {
            adapter = DataAdapter(it as MutableList<Mahasiswa>)
            Log.d("MainActivity", "onSuccess : $it")
            binding.rvData.layoutManager = LinearLayoutManager(this)
            binding.rvData.adapter = adapter
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

}