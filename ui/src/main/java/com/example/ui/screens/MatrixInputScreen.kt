package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview(showSystemUi = true)
@Composable
fun MatrixInputScreen() {
    // Mutable state for holding matrices
    val matrix1 = remember { mutableStateOf(Array(4) { Array(4) { "" } }) }
    val matrix2 = remember { mutableStateOf(Array(4) { Array(4) { "" } }) }
    val resultMatrix = remember { mutableStateOf(Array(4) { Array(4) { "" } }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Matrix inputs

        Text(
            text = "Matrix 1",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 55.dp, bottom = 8.dp).align(Alignment.Start)
        )

        MatrixInput(matrix = matrix1.value, onValueChange = { row, col, newValue ->
            matrix1.value[row][col] = newValue
        })

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Matrix 2",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 55.dp, bottom = 8.dp).align(Alignment.Start)
        )
        MatrixInput(matrix = matrix2.value, onValueChange = { row, col, newValue ->
            matrix2.value[row][col] = newValue
        })

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Calculate */ },
                modifier = Modifier.weight(1f) // кнопка будет занимать всё доступное пространство после матрицы
            ) {
                Text("Calculate")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { /* Clear */ },
                modifier = Modifier.weight(0.5f) // кнопка будет занимать всё доступное пространство после первой кнопки
            ) {
                Text("Clear")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Result",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 55.dp, bottom = 8.dp).align(Alignment.Start)
        )
        // Result matrix
        ReadOnlyMatrix(matrix = resultMatrix.value)
    }
}




@Composable
fun MatrixInput(
    matrix: Array<Array<String>>,
    onValueChange: (row: Int, col: Int, newValue: String) -> Unit
) {
    Column {
        for (i in matrix.indices) {
            Row {
                for (j in matrix[i].indices) {
                    MatrixInputField(
                        value = matrix[i][j],
                        onValueChange = { newValue ->
                            onValueChange(i, j, newValue)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MatrixInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun ReadOnlyMatrix(matrix: Array<Array<String>>) {
    Column {
        for (i in matrix.indices) {
            Row {
                for (j in matrix[i].indices) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(50.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Text(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            text = matrix[i][j],
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
