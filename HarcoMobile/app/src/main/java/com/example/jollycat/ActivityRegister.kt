package com.example.jollycat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jollycat.database.DatabaseHelper
import com.example.jollycat.databinding.ActivityRegisterBinding

class ActivityRegister : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DatabaseHelper
    private val global = Global

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Populate Global.listUser from the database
        Global.listUser = dbHelper.getUser()

        val usernameET = binding.registerUsername
        val passwordET = binding.registerPassword
        val phoneET = binding.registerPhoneET

        binding.registerBtn.setOnClickListener {
            if (usernameET.text.isEmpty() || passwordET.text.isEmpty() || phoneET.text.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (uniqueUsername(usernameET.text.toString())) {
                Toast.makeText(this, "Username must be unique", Toast.LENGTH_SHORT).show()
            } else if (usernameET.length() < 8) {
                Toast.makeText(this, "Username must be more than 8 characters", Toast.LENGTH_SHORT).show()
            } else if (passwordET.length() < 5) {
                Toast.makeText(this, "Password must be more than 5 characters", Toast.LENGTH_SHORT).show()
            } else if (phoneET.length() < 8 || phoneET.length() > 20) {
                Toast.makeText(this, "Phone Number must be between 8 - 20 characters", Toast.LENGTH_SHORT).show()
            } else if (phoneET.text[0] != '0' && phoneET.text[0] != '+') {
                Toast.makeText(this, "Phone number must start with either '0' or '+'", Toast.LENGTH_SHORT).show()
            } else {
                val newUser = User(
                    userID = System.currentTimeMillis().toInt(), // Assign a unique ID based on current time
                    userName = usernameET.text.toString(),
                    phoneNumber = phoneET.text.toString(),
                    password = passwordET.text.toString()
                )
                dbHelper.insertUser(newUser)
                global.listUser.add(newUser)

                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.goToLoginBtn.setOnClickListener {
            finish()
        }
    }

    private fun uniqueUsername(username: String): Boolean {
        for (user in global.listUser) {
            if (user.userName == username) {
                return true
            }
        }
        return false
    }
}
