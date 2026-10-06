package com.example.mad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapter.CropsUserAdapter
import com.example.mad.Database.DbHelperCrop
import com.example.mad.Model.CropModel
import com.example.mad.databinding.ActivityCropsReadUserBinding

class CropsReadUser : AppCompatActivity() {

    private lateinit var binding: ActivityCropsReadUserBinding
    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter: CropsUserAdapter?= null
    var DbHelp: DbHelperCrop?=null

    var cropsList:List<CropModel> = ArrayList<CropModel>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropsReadUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        DbHelp = DbHelperCrop(this)
        val db = DbHelperCrop(this)

        fetchlist()

        binding.imageButton.setOnClickListener{
            finish()
        }
    }
    private fun fetchlist(){

        cropsList=DbHelp!!.getAllCrops()
        Adapter= CropsUserAdapter(cropsList,applicationContext);
        linierlayoutManager= LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter=Adapter
        Adapter!!.notifyDataSetChanged()
    }
}