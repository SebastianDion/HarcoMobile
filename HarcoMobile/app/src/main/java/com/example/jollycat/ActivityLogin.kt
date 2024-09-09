package com.example.jollycat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jollycat.database.DatabaseHelper
import com.example.jollycat.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        Global.listUser = dbHelper.getUser()

        val usernameET = binding.loginUsernameET
        val passwordET = binding.loginPasswordET

        binding.loginBtn.setOnClickListener {
            if (usernameET.text.isNotEmpty() && passwordET.text.isNotEmpty()) {
                val users = dbHelper.getUser()
                var userFound = false

                for (user in users) {
                    if (user.userName == usernameET.text.toString() && user.password == passwordET.text.toString()) {
                        user.userID?.let {
                            Global.currentSelectedUser = it
                            userFound = true
                            val intent = Intent(this, ActivityMainPage::class.java)
                            startActivity(intent)
                            finish()
                        }
                        break
                    }
                }

                if (!userFound) {
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please input all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToRegisterBtn.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }
    }
}
