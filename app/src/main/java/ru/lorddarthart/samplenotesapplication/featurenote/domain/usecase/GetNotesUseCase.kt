package ru.lorddarthart.samplenotesapplication.featurenote.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.lorddarthart.samplenotesapplication.featurenote.domain.model.Note
import ru.lorddarthart.samplenotesapplication.featurenote.domain.repository.NoteRepository
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.NoteOrder
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.OrderType

class GetNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> = repository.getNotes().map { notes ->
        when (noteOrder) {
            is NoteOrder.Color -> if (noteOrder.orderType is OrderType.Ascending) notes.sortedBy { it.color } else notes.sortedByDescending { it.color }
            is NoteOrder.Date -> if (noteOrder.orderType is OrderType.Ascending) notes.sortedBy { it.timestamp } else notes.sortedByDescending { it.timestamp }
            is NoteOrder.Title -> if (noteOrder.orderType is OrderType.Ascending) notes.sortedBy { it.title.lowercase() } else notes.sortedByDescending { it.title.lowercase() }
        }
    }
}