package cit.app.prefinals

data class ClassSchedule(
    val id: Int,
    val courseName: String,
    val courseCode: String,
    val day: String,
    val timeRange: String
)