package ru.lorddarthart.samplenotesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.NOTE_COLOR
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.NOTE_ID
import ru.lorddarthart.samplenotesapplication.featurenote.Constants.UNEXPECTED_ID
import ru.lorddarthart.samplenotesapplication.featurenote.presentation.addeditnote.components.AddEditNoteScreen
import ru.lorddarthart.samplenotesapplication.featurenote.presentation.notes.components.NotesScreen
import ru.lorddarthart.samplenotesapplication.featurenote.presentation.util.Screen
import ru.lorddarthart.samplenotesapplication.ui.theme.SampleNotesApplicationTheme

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleNotesApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNotesScreen.route + "?${NOTE_ID}={${NOTE_ID}}&&${NOTE_COLOR}={${NOTE_COLOR}}",
                            arguments = listOf(
                                navArgument(name = NOTE_ID) {
                                    type = NavType.IntType
                                    defaultValue = UNEXPECTED_ID
                                },
                                navArgument(name = NOTE_COLOR) {
                                    type = NavType.IntType
                                    defaultValue = UNEXPECTED_ID
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt(NOTE_COLOR) ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleNotesApplicationTheme {
        Greeting("Android")
    }
}