package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

private lateinit var etUsername: EditText
private lateinit var etPassword: EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etPassword)
        val etButton = findViewById<Button>(R.id.btnLogin)

        etButton.setOnClickListener{
            login(etUsername, etPassword)
        }
    }

    fun login(Name: EditText, Pass:EditText) {
        val userName: String = Name.getText().toString().trim()
        val password: String = Pass.getText().toString().trim()

        val call: Call<ResponseBody> = RetrofitClient
            .getInstance()
            .api
            .checkUser(User(userName, password))

        if (userName.isEmpty()) {
            Name.setError("Username is required")
            Name.requestFocus()
            return
        } else if (password.isEmpty()) {
            Pass.setError("Password is required")
            Pass.requestFocus()
            return
        }

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                var s = ""
                try {
                    s = response.body()!!.string()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (s == userName) {
                    val intent = Intent(this@LoginActivity,dashboard::class.java)
                    intent.putExtra("Username",s)
                    Toast.makeText(
                        this@LoginActivity,
                        "Successfully login",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "User does not exists!", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

                }


            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
            }


        })
    }

}