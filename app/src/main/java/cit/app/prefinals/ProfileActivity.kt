package cit.app.prefinals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import android.widget.Toast

class ProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileImageView = findViewById<ImageView>(R.id.profile_image)
        val fullNameEditText = findViewById<EditText>(R.id.fullname_edit_text)
        val usernameEditText = findViewById<EditText>(R.id.username_edit_text)
        val phoneEditText = findViewById<EditText>(R.id.phone_edit_text)
        val passwordEditText = findViewById<EditText>(R.id.password_edit_text)
        val saveButton = findViewById<Button>(R.id.save_button)
        val logoutButton = findViewById<Button>(R.id.logout_button)

        Glide.with(this)
            .load(R.drawable.ic_placeholder)
            .transform(CircleCrop())
            .into(profileImageView)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedFullName = sharedPreferences.getString("fullname", "John Doe") ?: "John Doe"
        val savedUsername = sharedPreferences.getString("username", "email@example.com") ?: "email@example.com"
        val savedPhone = sharedPreferences.getString("phone", "1234567890") ?: "1234567890"
        val savedPassword = sharedPreferences.getString("password", "password123") ?: "password123"

        fullNameEditText.setText(savedFullName)
        usernameEditText.setText(savedUsername)
        phoneEditText.setText(savedPhone)
        passwordEditText.setText(savedPassword)

        saveButton.setOnClickListener {
            val newFullName = fullNameEditText.text.toString().trim()
            val newUsername = usernameEditText.text.toString().trim()
            val newPhone = phoneEditText.text.toString().trim()
            val newPassword = passwordEditText.text.toString().trim()

            if (newFullName.isNotEmpty() && newUsername.isNotEmpty() && newPhone.isNotEmpty() && newPassword.isNotEmpty()) {
                val editor = sharedPreferences.edit()
                editor.putString("fullname", newFullName)
                editor.putString("username", newUsername)
                editor.putString("phone", newPhone)
                editor.putString("password", newPassword)
                editor.apply() // Save updated credentials

                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        logoutButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}