package com.example.mad

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.ArticlesModal
import kotlinx.android.synthetic.main.activity_add_articles_admin.*
import kotlinx.android.synthetic.main.activity_update_articles_admin.*
import kotlinx.android.synthetic.main.activity_update_articles_admin.cancel_button
import java.text.SimpleDateFormat
import java.util.*

class UpdateArticlesAdmin : AppCompatActivity() {

    var article:ArticlesModal = ArticlesModal();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_articles_admin)

        val etStartDateNE = findViewById<EditText>(R.id.dateEdit)
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
                this@UpdateArticlesAdmin,
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
        val db=DbHelperArticles(this)

        article=db.getArticles(id)

        articleTypeEdit.setText(article.article_Title)
        dateEdit.setText(article.article_Date)
        articleDescriptionEdit.setText(article.article_description)


        //cancel

        cancel_button.setOnClickListener {
            startActivity(Intent(this, ArtcleReadAdmin::class.java))
        }

        //update
        articleUpdate.setOnClickListener{

            var title=articleTypeEdit.text.toString()
            var date=dateEdit.text.toString()
            var discription=articleDescriptionEdit.text.toString()


            article=ArticlesModal(id,title,date,discription)

            var success=db.updateArticles(article)

            if(success == true){
                Toast.makeText(this,"Update Succesfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,ArtcleReadAdmin::class.java))
            }else{
                Toast.makeText(this,"Update Unsuccesfully", Toast.LENGTH_LONG).show()
            }

        }

    }
}