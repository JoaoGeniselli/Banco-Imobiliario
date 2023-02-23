package com.jgeniselli.banco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jgeniselli.banco.ui.theme.BancoImobiliarioTheme
import com.jgeniselli.banco.topbar.component.TopBarAction
import com.jgeniselli.banco.topbar.component.TopBarActionButton

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
                        MainNavGraph(
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
        title = { Text(stringResource(id = R.string.app_name)) },
        actions = {
            actions.forEach { TopBarActionButton(action = it) }
        }
    )
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar(listOf())
}
