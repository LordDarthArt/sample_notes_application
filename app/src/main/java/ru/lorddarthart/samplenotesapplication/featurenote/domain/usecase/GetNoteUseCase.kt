package ru.lorddarthart.samplenotesapplication.featurenote.domain.usecase

import ru.lorddarthart.samplenotesapplication.featurenote.domain.model.Note
import ru.lorddarthart.samplenotesapplication.featurenote.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? = repository.getNoteById(id)
}