package cit.app.prefinals

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ViewScheduleActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)

        val scheduleId = intent.getIntExtra("scheduleId", -1)

        if (scheduleId == -1) {
            return
        }

        val schedule = getScheduleById(scheduleId)

        val courseNameTextView = findViewById<TextView>(R.id.courseNameTextView)
        val courseCodeTextView = findViewById<TextView>(R.id.courseCodeTextView)
        val dayTextView = findViewById<TextView>(R.id.dayTextView)
        val timeRangeTextView = findViewById<TextView>(R.id.timeRangeTextView)

        if (schedule != null) {
            courseNameTextView.text = schedule.courseName
            courseCodeTextView.text = schedule.courseCode
            dayTextView.text = schedule.day
            timeRangeTextView.text = schedule.timeRange
        } else {
            courseNameTextView.text = "Schedule not found"
            courseCodeTextView.text = ""
            dayTextView.text = ""
            timeRangeTextView.text = ""
        }
    }

    private fun getScheduleById(id: Int): ClassSchedule? {
        val sharedPreferences = getSharedPreferences("schedules", MODE_PRIVATE)
        val gson = Gson()

        val currentSchedulesJson = sharedPreferences.getString("scheduleList", "[]")
        val currentSchedules: MutableList<ClassSchedule> = try {
            val type = object : TypeToken<MutableList<ClassSchedule>>() {}.type
            gson.fromJson(currentSchedulesJson, type) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }

        return currentSchedules.find { it.id == id }
    }
}