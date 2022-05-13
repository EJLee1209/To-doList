package com.example.to_dolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ToDoLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_login)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val idInput = findViewById<EditText>(R.id.id_input)
        val pwInput = findViewById<EditText>(R.id.pw_input)

        findViewById<TextView>(R.id.insta_join).setOnClickListener {
            startActivity(Intent(this, ToDoJoinActivity::class.java))
        }

        findViewById<TextView>(R.id.login).setOnClickListener {
            val user = HashMap<String, Any>()
            user["username"] = idInput.text.toString()
            user["password"] = pwInput.text.toString()
            retrofitService.instaLogin(user).enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        val user: User = response.body()!!
                        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("token", user.token)
                        editor.putString("user_id", user.id.toString())
                        editor.commit()
                        startActivity(Intent(this@ToDoLoginActivity, ToDoActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("instaa", "fail")
                }
            })

        }



    }
}