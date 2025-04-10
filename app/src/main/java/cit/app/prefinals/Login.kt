package cit.app.prefinals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*

class Login : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val toRegisterButton = findViewById<Button>(R.id.toRegister)

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val passedUsername = intent.getStringExtra("username")
        if (!passedUsername.isNullOrEmpty()) {
            usernameEditText.setText(passedUsername)
        }

        loginButton.setOnClickListener {
            val inputUsername = usernameEditText.text.toString().trim()
            val inputPassword = passwordEditText.text.toString().trim()

            val savedUsername = sharedPref.getString("username", null)
            val savedPassword = sharedPref.getString("password", null)

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show()
            } else if (inputUsername == savedUsername && inputPassword == savedPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                val homeIntent = Intent(this, HomeScreen::class.java)
                startActivity(homeIntent)
                finish()
            } else {
                Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_SHORT).show()
            }
        }

        toRegisterButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}