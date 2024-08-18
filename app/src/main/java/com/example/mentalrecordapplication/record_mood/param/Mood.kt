package com.example.mentalrecordapplication.record_mood.param

enum class Mood(private val mood: String) {
    HAPPY("Happy"),
    ANGER("Anger"),
    SAD("Sad"),
    FUN("Fun");

    fun getMood(): String {
        return mood
    }
}