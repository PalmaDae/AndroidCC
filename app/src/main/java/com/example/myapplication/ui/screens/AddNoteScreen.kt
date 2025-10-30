import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.model.Note
import com.example.myapplication.viewmodel.SharedViewModel

@Composable
fun AddNoteScreen(
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf<String?>(null) }
    val errorPasswordLength = stringResource(R.string.error_empty_title)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = null
            },
            label = { Text(text = stringResource(R.string.note_title)) },
            isError = titleError != null,
            modifier = Modifier.fillMaxWidth()
        )

        if (titleError != null) {
            Text(
                text = titleError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text(text = stringResource(R.string.note_content)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (title.isBlank()) {
                    titleError = errorPasswordLength
                } else {
                    val newNote = Note(title, content)
                    viewModel.notes.value = viewModel.notes.value + newNote
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_button))
        }
    }
}