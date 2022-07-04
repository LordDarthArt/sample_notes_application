package ru.lorddarthart.samplenotesapplication.featurenote.presentation.util

import ru.lorddarthart.samplenotesapplication.featurenote.Constants.ADD_EDIT_NOTE_SCREEN
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.NOTES_SCREEN

sealed class Screen(val route: String) {
    object NotesScreen : Screen(NOTES_SCREEN)
    object AddEditNotesScreen : Screen(ADD_EDIT_NOTE_SCREEN)
}