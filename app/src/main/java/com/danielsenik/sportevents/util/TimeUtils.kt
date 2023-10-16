package com.danielsenik.sportevents.util

class TimeUtils {
    companion object {
        fun formatTimeLeft(timeLeft: Long): String {
            val seconds = timeLeft / 1000 % 60
            val minutes = timeLeft / (1000 * 60) % 60
            val hours = timeLeft / (1000 * 60 * 60) % 24
            val days = timeLeft / (1000 * 60 * 60 * 24)

            if (days > 0) {
                return String.format("%01d days %02d:%02d:%02d", days, hours, minutes, seconds)
            } else {
                return String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
        }
    }
}