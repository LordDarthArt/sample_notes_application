package ru.lorddarthart.samplenotesapplication.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.lorddarthart.samplenotesapplication.NoteApp
import ru.lorddarthart.samplenotesapplication.featurenote.data.datasource.NoteDatabase
import ru.lorddarthart.samplenotesapplication.featurenote.data.repository.NoteRepositoryImpl
import ru.lorddarthart.samplenotesapplication.featurenote.domain.repository.NoteRepository
import ru.lorddarthart.samplenotesapplication.featurenote.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(
        app: Application
    ): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        NoteDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideNotesRepository(
        db: NoteDatabase
    ): NoteRepository = NoteRepositoryImpl(
        db.noteDao
    )

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepository
    ): NoteUseCases = NoteUseCases(
        getNotes = GetNotesUseCase(repository),
        getNoteById = GetNoteUseCase(repository),
        deleteNote = DeleteNoteUseCase(repository),
        insertNote = AddNoteUseCase(repository)
    )
}