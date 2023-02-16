package com.jgeniselli.banco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jgeniselli.banco.compose.ui.theme.BancoImobiliarioTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BancoImobiliarioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBar(
                                isHistoryEnabled = true,
                                onHistory = { navController.navigate(Routes.HISTORY) }
                            )
                        }
                    ) {
                        MainNavHost(navController)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(onHistory: () -> Unit, isHistoryEnabled: Boolean) {
    TopAppBar(
        title = { Text("Banker App") },
        actions = {
            if (isHistoryEnabled) {
                IconButton(onClick = onHistory) {
                    Icon(imageVector = Icons.Default.History, contentDescription = "History")
                }
            }
        }
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar({}, true)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BancoImobiliarioTheme {
        Greeting("Android")
    }
}