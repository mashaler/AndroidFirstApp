package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

lateinit var welcomeText: String;
lateinit var tvWelcome: TextView


public class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        welcomeText ="Welcome "+ getIntent().getStringExtra("Username").toString() + "!";
        tvWelcome = this.findViewById(R.id.tvWelcome)
        tvWelcome.setText(welcomeText)
    }

}