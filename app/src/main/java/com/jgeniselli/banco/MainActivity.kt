package com.jgeniselli.banco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jgeniselli.banco.compose.ui.theme.BancoImobiliarioTheme
import com.jgeniselli.banco.topbar.TopBarAction
import com.jgeniselli.banco.topbar.TopBarActionButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var actions by remember { mutableStateOf<List<TopBarAction>>(emptyList()) }
            BancoImobiliarioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBar(actions = actions)
                        }
                    ) { _ ->
                        MainNavHost(
                            navController = navController,
                            onChangeActions = { actions = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(actions: List<TopBarAction>) {
    TopAppBar(
        title = { Text("Banker App") },
        actions = {
            actions.forEach { TopBarActionButton(action = it) }
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
    TopBar(listOf())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BancoImobiliarioTheme {
        Greeting("Android")
    }
}