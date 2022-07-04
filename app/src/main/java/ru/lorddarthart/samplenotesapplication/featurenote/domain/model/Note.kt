package ru.lorddarthart.samplenotesapplication.featurenote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lorddarthart.samplenotesapplication.ui.theme.*

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val notesColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )
    }
}
