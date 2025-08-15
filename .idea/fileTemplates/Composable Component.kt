#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

#parse("File Header.java")
@Composable
fun ${NAME}(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
private fun Preview${NAME}() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        ${NAME}(
            modifier = Modifier
        )
    }
}
