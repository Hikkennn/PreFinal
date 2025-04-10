package cit.app.prefinals

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class AddScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        val saveButton = findViewById<Button>(R.id.save_button)
        val courseNameEditText = findViewById<EditText>(R.id.course_name_edit_text)
        val courseCodeEditText = findViewById<EditText>(R.id.course_code_edit_text)
        val dayEditText = findViewById<EditText>(R.id.day_edit_text)
        val timeRangeEditText = findViewById<EditText>(R.id.time_range_edit_text)

        saveButton.setOnClickListener {
            val courseName = courseNameEditText.text.toString().trim()
            val courseCode = courseCodeEditText.text.toString().trim()
            val day = dayEditText.text.toString().trim()
            val timeRange = timeRangeEditText.text.toString().trim()

            if (courseName.isNotEmpty() && courseCode.isNotEmpty() && day.isNotEmpty() && timeRange.isNotEmpty()) {
                val scheduleId = generateUniqueId()
                val schedule = ClassSchedule(scheduleId, courseName, courseCode, day, timeRange)

                val sharedPreferences = getSharedPreferences("schedules", MODE_PRIVATE)
                val gson = Gson()

                // Safely retrieve and deserialize the schedule list
                val currentSchedulesJson = sharedPreferences.getString("scheduleList", "[]")
                val currentSchedules: MutableList<ClassSchedule> = try {
                    val type = object : TypeToken<MutableList<ClassSchedule>>() {}.type
                    gson.fromJson(currentSchedulesJson, type) ?: mutableListOf()
                } catch (e: Exception) {
                    mutableListOf()
                }

                currentSchedules.add(schedule)

                // Serialize and save the updated list
                val updatedSchedulesJson = gson.toJson(currentSchedules)
                sharedPreferences.edit().putString("scheduleList", updatedSchedulesJson).apply()

                Toast.makeText(this, "Schedule Saved", Toast.LENGTH_SHORT).show()
                finish() // Close activity and return to previous screen
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateUniqueId(): Int {
        return (System.currentTimeMillis() % Int.MAX_VALUE).toInt() // Generates a simple unique ID
    }
}
