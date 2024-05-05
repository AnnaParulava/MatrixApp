package com.kinopoisk.matrixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kinopoisk.matrixapp.ui.theme.MatrixAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatrixAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
//                        name = "${add(2,2)}",
                        name = stringFromJNI(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    external fun stringFromJNI(): String
    external fun add(x: Int, y: Int): Int
//    external fun sub(x: Int, y: Int): Int
//    external fun multiply(x: Int, y: Int): Int
//    external fun divide(x: Int, y: Int): Int

    companion object {
        // Used to load the 'myfirstcppapp' library on application startup.
        init {
            System.loadLibrary("matrixapp")
            System.loadLibrary("matrix")
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MatrixAppTheme {
        Greeting("Android")
    }
}