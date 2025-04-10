package cit.app.prefinals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import java.util.regex.Pattern

class Register : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullName = findViewById<EditText>(R.id.fullName)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val toLogin = findViewById<Button>(R.id.toLogin)

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        registerButton.setOnClickListener {
            val name = fullName.text.toString().trim()
            val username = email.text.toString().trim()
            val phoneNum = phone.text.toString().trim()
            val pass = password.text.toString()
            val confirmPass = confirmPassword.text.toString()

            val existingUsername = sharedPref.getString("username", "")

            if (name.isEmpty() || username.isEmpty() || phoneNum.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (existingUsername == username) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
            } else if (pass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(username)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(pass)) {
                Toast.makeText(this, "Password must be at least 8 characters, with at least one uppercase letter, one number, and one special character.", Toast.LENGTH_LONG).show()
            } else {
                val editor = sharedPref.edit()
                editor.putString("fullname", name)
                editor.putString("username", username)
                editor.putString("phone", phoneNum)
                editor.putString("password", pass)
                editor.apply()

                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Login::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            }
        }

        toLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    // Email validation
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        return Pattern.matches(emailPattern, email)
    }

    // Password validation
    private fun isValidPassword(password: String): Boolean {
        // Password must have at least 8 characters, one uppercase letter, and one digit
        val passwordPattern = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        return Pattern.matches(passwordPattern, password)
    }
}