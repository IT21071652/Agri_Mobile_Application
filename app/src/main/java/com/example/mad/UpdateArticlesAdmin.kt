package com.example.mad

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.ArticlesModal
import com.example.mad.databinding.ActivityUpdateArticlesAdminBinding
import java.text.SimpleDateFormat
import java.util.*

class UpdateArticlesAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateArticlesAdminBinding
    var article:ArticlesModal = ArticlesModal();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateArticlesAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.articleTypeEdit.setText(article.article_Title)
        binding.dateEdit.setText(article.article_Date)
        binding.articleDescriptionEdit.setText(article.article_description)


        //cancel

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this, ArtcleReadAdmin::class.java))
        }

        //update
        binding.articleUpdate.setOnClickListener{

            var title=binding.articleTypeEdit.text.toString()
            var date=binding.dateEdit.text.toString()
            var discription=binding.articleDescriptionEdit.text.toString()


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