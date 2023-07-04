package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        imageView.setOnClickListener{
            startActivity(Intent(this,CropsReadUser::class.java))
        }

        imageView4.setOnClickListener{
            startActivity(Intent(this,MainUser::class.java))
        }
    }
}