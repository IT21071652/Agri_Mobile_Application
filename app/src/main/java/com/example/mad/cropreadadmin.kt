package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapter.CropsAdapter
import com.example.mad.Database.DbHelperCrop
import com.example.mad.Model.CropModel
import kotlinx.android.synthetic.main.activity_cropreadadmin.*

class cropreadadmin : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter:CropsAdapter ?= null
    var DbHelp:DbHelperCrop ?=null

    var articleList:List<CropModel> = ArrayList<CropModel>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cropreadadmin)
        recyclerView = findViewById(R.id.recyclerView)
        DbHelp = DbHelperCrop(this)
        val db = DbHelperCrop(this)

        fetchlist()

        var deleteBtn=findViewById<Button>(R.id.deletebtn)

        var addArticlebtn=findViewById<Button>(R.id.addArticle)

        addArticlebtn.setOnClickListener{
            startActivity(Intent(this,AddCrop::class.java))


        }

        deleteBtn.setOnClickListener{
            var id = editNumber.text.toString()
            println(id)

            val iD = id.toInt()//Casting
            val success = db.deleteCrop(iD)

            println(iD)

            if (success == true){
                Toast.makeText(this,"Delete Successfully",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,cropreadadmin::class.java))
            }else{
                Toast.makeText(this,"Delete Unsuccessfully",Toast.LENGTH_LONG).show()
            }
        }

        updatebtn.setOnClickListener{
            var id = editNumber.text.toString()
            println(id)
            val intent = Intent(this, UpdateCropsAdmin::class.java)
            intent.putExtra("id", id)//bind the Id value and send update page
            startActivity(intent)
        }

    }
    private fun fetchlist(){

        articleList=DbHelp!!.getAllCrops()
        Adapter= CropsAdapter(articleList,applicationContext);
        linierlayoutManager= LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter=Adapter
        Adapter!!.notifyDataSetChanged()

    }
}