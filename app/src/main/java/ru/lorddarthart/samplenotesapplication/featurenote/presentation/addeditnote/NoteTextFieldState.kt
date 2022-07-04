package ru.lorddarthart.samplenotesapplication.featurenote.presentation.addeditnote

import androidx.annotation.StringRes

data class NoteTextFieldState(
    val text: String = "",
    @StringRes val hint: Int = 0,
    val isHintVisible: Boolean = true
)
