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
import kotlinx.android.synthetic.main.activity_crops_read_user.*

class CropsReadUser : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter: CropsUserAdapter?= null
    var DbHelp: DbHelperCrop?=null

    var cropsList:List<CropModel> = ArrayList<CropModel>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crops_read_user)

        recyclerView = findViewById(R.id.recyclerView)
        DbHelp = DbHelperCrop(this)
        val db = DbHelperCrop(this)

        fetchlist()

        imageButton.setOnClickListener{
            startActivity(Intent(this,Homepage::class.java))
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