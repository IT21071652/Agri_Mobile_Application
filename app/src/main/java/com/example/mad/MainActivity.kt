package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*button.setOnClickListener {
            startActivity(Intent(this, AddCrop::class.java))
        }

        button2.setOnClickListener {
            startActivity(Intent(this, cropreadadmin::class.java))
        }

        button3.setOnClickListener {
            startActivity(Intent(this, CropsReadUser::class.java))
        }*/

        button5.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }

        /*button6.setOnClickListener {
            startActivity(Intent(this, ArticleAdminMain::class.java))
        }*/

        button7.setOnClickListener {
            startActivity(Intent(this, AdminHomepage::class.java))
        }
    }
}