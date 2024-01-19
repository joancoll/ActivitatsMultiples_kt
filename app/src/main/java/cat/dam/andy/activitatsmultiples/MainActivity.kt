package cat.dam.andy.activitatsmultiples

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cat.dam.andy.activitatsmultiples.ui.theme.ActivitatsMultiplesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class) //per OulinedTextField
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivitatsMultiplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    var name by rememberSaveable { mutableStateOf("") }
                    var showError by rememberSaveable { mutableStateOf(false) }
                    val dialogState = remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                showError = false
                            },
                            label = { Text(getString(R.string.enter_your_name)) },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            isError = showError
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            if (name.isNotEmpty()) {
                                val intent = Intent(context, SecondActivity::class.java)
                                // També si val context = LocalContext.current es pot fer així:
                                //val intent = Intent(context, SecondActivity::class.java)
                                intent.putExtra("name", name)
                                startActivity(intent)
                            } else {
                                showError = true
                                dialogState.value = true
                            }
                        }) {
                            Text(getString(R.string.next))
                        }
                        if (dialogState.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    dialogState.value = false
                                },
                                title = {
                                    Text(getString(R.string.error))
                                },
                                text = {
                                    Text(getString(R.string.err_empty_name))
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            dialogState.value = false
                                        }
                                    ) {
                                        Text(getString(R.string.ok))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}