package com.kinopoisk.matrixapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MatrixAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.Black.toArgb()
        enableEdgeToEdge()
        setContent {
            MatrixAppTheme {
                SetStatusBarColor(color = MaterialTheme.colorScheme.primary)
                Scaffold(
                    containerColor = Color.White
                ){ innerPadding ->
                   Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp
                        ),
                    ) {
                       main()
                       val navController = rememberNavController()
                       Navigation(navController = navController)
                    }
                }
            }
        }
    }
    @Composable
    fun SetStatusBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color)
        }
    }

    companion object {
        init {
            System.loadLibrary("matrixapp")
            System.loadLibrary("matrix")
        }
    }

    private external fun addMatricesJNI(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray>
    private external fun subtractMatricesJNI(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray>
    private external fun multiplyMatricesJNI(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray>

    fun main() {
        val matrix1 = arrayOf(doubleArrayOf(1.1, 2.0), doubleArrayOf(3.0, 4.0))
        val matrix2 = arrayOf(doubleArrayOf(5.0, 6.0), doubleArrayOf(7.0, 8.0))
        val resultMatrix = addMatricesJNI(matrix1, matrix2)
        extracted("add", resultMatrix)
        extracted("sub", subtractMatricesJNI(matrix1, matrix2))
        extracted("multi", multiplyMatricesJNI(matrix1, matrix2))
    }

    private fun extracted(log: String, resultMatrix: Array<DoubleArray>) {
        var res = ""

        for (row in resultMatrix) {
            for (element in row) {
                res += "$element "
            }
            println()
        }
        Log.d("AAA", "$log $res")
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample(test: String) {
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = test
//                """
//                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.
//
//                    It also contains some basic inner content, such as this text.
//
//                    You have pressed the floating action button $presses times.
//                """.trimIndent(),
            )
        }
    }
}


//@Preview
//@Composable
//fun PreviewSimpleScreen() {
//    ScaffoldExample()
//}
