package ru.lorddarthart.samplenotesapplication.featurenote.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNoteById: GetNoteUseCase,
    val insertNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase
)
