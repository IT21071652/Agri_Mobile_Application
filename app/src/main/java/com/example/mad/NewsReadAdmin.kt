package com.example.mad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapter.NewsAdminAdapter
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.NewsModal
import com.example.mad.databinding.ActivityNewsReadAdminBinding


class NewsReadAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityNewsReadAdminBinding
    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter: NewsAdminAdapter?= null
    var DbHelp: DbHelperArticles?=null

    var newsList:List<NewsModal> = ArrayList<NewsModal>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsReadAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView
        DbHelp = DbHelperArticles(this)
        val db = DbHelperArticles(this)

        fetchlist()

        var deleteNewsBtn = binding.deletenewsbtn

        var addNewsbtn = binding.addNews

        addNewsbtn.setOnClickListener{
            startActivity(Intent(this,AddNewsAdmin::class.java))


        }

        deleteNewsBtn.setOnClickListener{
            var id = binding.editNumber.text.toString()
            println(id)

            val iD = id.toInt()//Casting
            val success = db.deleteNews(iD)

            println(iD)

            if (success == true){
                Toast.makeText(this,"Delete Successfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,NewsReadAdmin::class.java))
            }else{
                Toast.makeText(this,"Delete Unsuccessfully", Toast.LENGTH_LONG).show()
            }
        }

        binding.updatenewsBtn.setOnClickListener{
            var id = binding.editNumber.text.toString()
            println(id)
            val intent = Intent(this, UpdateNewsAdmin::class.java)
            intent.putExtra("id", id)//bind the Id value and send update page
            startActivity(intent)
        }

    }
    private fun fetchlist(){

        newsList=DbHelp!!.getAllNews()
        Adapter= NewsAdminAdapter(newsList,applicationContext);
        linierlayoutManager= LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter=Adapter
        Adapter!!.notifyDataSetChanged()

    }
}