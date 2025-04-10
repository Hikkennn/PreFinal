package cit.app.prefinals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cit.app.prefinals.ClassSchedule
import cit.app.prefinals.R

class SubjectAdapter(context: Context, schedules: List<ClassSchedule>) :
    ArrayAdapter<ClassSchedule>(context, 0, schedules) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val schedule = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity_add_schedule, parent, false)

        val courseNameTextView = view.findViewById<TextView>(R.id.courseNameTextView)
        val timeRangeTextView = view.findViewById<TextView>(R.id.timeRangeTextView)

        courseNameTextView.text = schedule?.courseName
        timeRangeTextView.text = schedule?.timeRange

        return view
    }
}