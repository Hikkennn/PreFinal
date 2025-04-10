package cit.app.prefinals

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.gson.Gson

class HomeScreen : Activity() {
    private val scheduleList = mutableListOf<ClassSchedule>()
    private lateinit var adapter: ArrayAdapter<ClassSchedule> // Class-level adapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val listView = findViewById<ListView>(R.id.class_schedule_list)
        val addButton = findViewById<Button>(R.id.add_button)
        val profileButton = findViewById<Button>(R.id.profile_button) // Reference for Profile button

        // Initialize and set the adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, scheduleList)
        listView.adapter = adapter

        // Open AddScheduleActivity on button click
        addButton.setOnClickListener {
            startActivity(Intent(this, AddScheduleActivity::class.java))
        }

        // Open ProfileActivity on button click
        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Handle list item clicks to view or edit schedule
        listView.setOnItemClickListener { _, _, position, _ ->
            val schedule = scheduleList[position]
            val intent = Intent(this, ViewScheduleActivity::class.java)
            intent.putExtra("scheduleId", schedule.id)
            startActivity(intent)
        }

        // Load saved schedules from SharedPreferences
        loadScheduleFromPreferences()
    }

    private fun loadScheduleFromPreferences() {
        val sharedPreferences = getSharedPreferences("schedules", MODE_PRIVATE)
        val scheduleJson = sharedPreferences.getString("scheduleList", "[]")
        val gson = Gson()
        val scheduleArray = gson.fromJson(scheduleJson, Array<ClassSchedule>::class.java).toList()
        scheduleList.addAll(scheduleArray)
        adapter.notifyDataSetChanged() // Notify adapter for UI update
    }

    private fun saveScheduleToPreferences() {
        val sharedPreferences = getSharedPreferences("schedules", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val scheduleJson = gson.toJson(scheduleList)
        editor.putString("scheduleList", scheduleJson)
        editor.apply()
    }

    fun addNewSchedule(schedule: ClassSchedule) {
        scheduleList.add(schedule)
        adapter.notifyDataSetChanged() // Update UI when data changes
        saveScheduleToPreferences()
    }
}