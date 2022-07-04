package ru.lorddarthart.samplenotesapplication.featurenote.presentation.notes

import ru.lorddarthart.samplenotesapplication.featurenote.domain.model.Note
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.NoteOrder
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)