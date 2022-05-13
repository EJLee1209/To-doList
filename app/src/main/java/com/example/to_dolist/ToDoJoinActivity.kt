package com.example.to_dolist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ToDoJoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_join)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val joinId = findViewById<EditText>(R.id.join_id_input)
        val joinPw1= findViewById<EditText>(R.id.join_pw_input1)
        val joinPw2= findViewById<EditText>(R.id.join_pw_input2)

        findViewById<TextView>(R.id.join).setOnClickListener {
            if(joinPw1.text.toString() != joinPw2.text.toString()){
                Toast.makeText(this, "비밀번호가 같지 않습니다", Toast.LENGTH_SHORT).show()
            }
            else {
                val user = HashMap<String, Any>()
                user["username"] = joinId.text.toString()
                user["password1"] = joinPw1.text.toString()
                user["password2"] = joinPw2.text.toString()

                retrofitService.instaJoin(user).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val user = response.body()!!
                            val sharedPreferences =
                                getSharedPreferences("user_info", Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("token", user.token)
                            editor.putString("user_id", user.id.toString())
                            editor.commit()
                            Toast.makeText(this@ToDoJoinActivity, "아이디 생성 완료", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@ToDoJoinActivity, "아이디 생성 실패", Toast.LENGTH_SHORT)
                            .show()

                    }
                })
            }
        }
    }
}