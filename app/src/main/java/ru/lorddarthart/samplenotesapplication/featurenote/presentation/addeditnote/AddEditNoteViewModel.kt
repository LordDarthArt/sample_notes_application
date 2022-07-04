package ru.lorddarthart.samplenotesapplication.featurenote.presentation.addeditnote

import android.content.res.loader.ResourcesProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.lorddarthart.samplenotesapplication.R
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.NOTE_COLOR
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.NOTE_ID
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.UNEXPECTED_ID
import ru.lorddarthart.samplenotesapplication.featurenote.domain.model.Note
import ru.lorddarthart.samplenotesapplication.featurenote.domain.usecase.NoteUseCases
import ru.lorddarthart.samplenotesapplication.featurenote.domain.util.InvalidNoteException
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = R.string.title_hint
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = R.string.content_hint
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(savedStateHandle.get<Int>(NOTE_COLOR) ?: Note.notesColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventsFlow = MutableStateFlow<UiEvents>(UiEvents.EmptyEvent)
    val eventsFlow = _eventsFlow.asStateFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>(NOTE_ID)?.let { noteId ->
            if (noteId != UNEXPECTED_ID) {
                viewModelScope.launch {
                    noteUseCases.getNoteById(noteId)?.also {
                        currentNoteId = it.id
                        _noteTitle.value = _noteTitle.value.copy(
                            text = it.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = it.content,
                            isHintVisible = false
                        )
                        _noteColor.value = it.color
                    }
                }
                currentNoteId = noteId
            } else {
                viewModelScope.launch {
                    noteUseCases.getNotes().collect {
                        currentNoteId = it.count()
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                     text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNote(
                            Note(
                                id = currentNoteId,
                                title = _noteTitle.value.text,
                                content = _noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = _noteColor.value
                            )
                        )
                        _eventsFlow.emit(UiEvents.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventsFlow.emit(
                            UiEvents.ShowSnackbar(
                                message = e.message ?: R.string.unexpected_error
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvents {
        data class ShowSnackbar(val message: Any) : UiEvents()
        object SaveNote : UiEvents()
        object EmptyEvent : UiEvents()
    }
}