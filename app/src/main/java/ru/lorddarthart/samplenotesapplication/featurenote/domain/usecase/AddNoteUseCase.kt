package ru.lorddarthart.samplenotesapplication.featurenote.domain.usecase

import ru.lorddarthart.samplenotesapplication.featurenote.domain.model.Note
import ru.lorddarthart.samplenotesapplication.featurenote.domain.repository.NoteRepository
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.InvalidNoteException

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.id == null) { throw InvalidNoteException("Note ID can't be null") }
        if (note.title.isBlank()) { throw InvalidNoteException("Notes with no titles are not supported") }
        if (note.content.isBlank()) { throw InvalidNoteException("Notes with no content are not supported") }
        repository.insertNote(note)
    }
}