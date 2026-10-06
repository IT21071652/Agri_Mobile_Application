package com.example.mad

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.NewsModal
import com.example.mad.databinding.ActivityUpdateNewsAdminBinding
import java.text.SimpleDateFormat
import java.util.*

class UpdateNewsAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNewsAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNewsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etStartDateNE = findViewById<EditText>(R.id.newsdateEdit)
        etStartDateNE.isFocusable = false

        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            etStartDateNE.setText(dateFormat.format(calendar.time))
        }

        etStartDateNE.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@UpdateNewsAdmin,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

//fetch data
        val value = intent.getStringExtra("id")
        val id =value!!.toInt()
        val db= DbHelperArticles(this)

        var news=NewsModal()

        news=db.getNews(id)

        binding.newsTypeEdit.setText(news.news_Title)
        binding.newsdateEdit.setText(news.news_Date)
        binding.newsDescriptionEdit.setText(news.news_description)
        binding.newsSitelinkEdit.setText(news.news_sitelink)


        //cancel

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this, NewsReadAdmin::class.java))
        }

        //update
        binding.updatenewsbtn.setOnClickListener{

            var newstitle=binding.newsTypeEdit.text.toString()
            var newsdate=binding.newsdateEdit.text.toString()
            var newsdiscription=binding.newsDescriptionEdit.text.toString()
            var newssitelink=binding.newsSitelinkEdit.text.toString()


            news= NewsModal(id,newstitle,newsdate,newsdiscription,newssitelink)

            var success=db.updateNews(news)

            if(success == true){
                Toast.makeText(this,"Update Succesfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,NewsReadAdmin::class.java))
            }else{
                Toast.makeText(this,"Update Unsuccesfully", Toast.LENGTH_LONG).show()
            }

        }

    }
}