#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

#parse("File Header.java")
@Composable
fun ${NAME}Screen(
    controller: NavHostController
) {
    val actions = remember {
        ${NAME}Actions(
            onBack = { controller.popBackStack() }
        )
    }

    ${NAME}Content(
        actions = actions
    )
}

private data class ${NAME}Actions(
    val onBack: () -> Unit = {},
)

@Composable
private fun ${NAME}Content(
    actions: ${NAME}Actions,
) {

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        ${NAME}Content(
            actions = ${NAME}Actions()
        )
    }
}
