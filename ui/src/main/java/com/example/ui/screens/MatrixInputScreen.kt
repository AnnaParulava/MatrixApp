package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MatrixInputScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            MatrixInput(matrixSize = 4)
        }
        item {
            MatrixInput(matrixSize = 4)
        }
        item {
            ReadOnlyMatrix(matrixSize = 4)
        }
    }
}

@Composable
fun MatrixInput(matrixSize: Int) {
    val matrix by remember { mutableStateOf(Array(matrixSize) { Array(matrixSize) { "" } }) }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until matrixSize) {
            Row {
                for (j in 0 until matrixSize) {
                    MatrixInput(
                        value = matrix[i][j],
                        onValueChange = { newValue ->
                            matrix[i][j] = newValue
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MatrixInput(
    value: String,
    onValueChange: (String) -> Unit,
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
        maxLines = 1,
        label = {
            Text(
                text = "0",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun ReadOnlyMatrix(matrixSize: Int) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until matrixSize) {
            Row {
                for (j in 0 until matrixSize) {
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
                            text = "0",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}