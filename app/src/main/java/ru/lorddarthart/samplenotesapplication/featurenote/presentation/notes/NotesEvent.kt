package ru.lorddarthart.samplenotesapplication.featurenote.presentation.notes

import ru.lorddarthart.samplenotesapplication.featurenote.domain.model.Note
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
