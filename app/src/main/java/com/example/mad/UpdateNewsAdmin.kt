package com.example.mad

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.NewsModal
import kotlinx.android.synthetic.main.activity_update_articles_admin.*
import kotlinx.android.synthetic.main.activity_update_news_admin.*
import kotlinx.android.synthetic.main.activity_update_news_admin.cancel_button
import java.text.SimpleDateFormat
import java.util.*

class UpdateNewsAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_news_admin)

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

        newsTypeEdit.setText(news.news_Title).toString()
        newsdateEdit.setText(news.news_Date).toString()
        newsDescriptionEdit.setText(news.news_description).toString()
        newsSitelinkEdit.setText(news.news_sitelink).toString()


        //cancel

        cancel_button.setOnClickListener {
            startActivity(Intent(this, NewsReadAdmin::class.java))
        }

        //update
        updatenewsbtn.setOnClickListener{

            var newstitle=newsTypeEdit.text.toString()
            var newsdate=newsdateEdit.text.toString()
            var newsdiscription=newsDescriptionEdit.text.toString()
            var newssitelink=newsSitelinkEdit.text.toString()


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