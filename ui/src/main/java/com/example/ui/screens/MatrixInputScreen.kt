package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Pink40
import com.example.ui.theme.Pink80
import com.example.ui.viewmodel.MatrixViewModel
import kotlinx.coroutines.flow.collectLatest
import mappers.matrixToString
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun MatrixInputScreen(matrixViewModel: MatrixViewModel) {
    // Mutable state for holding matrices
    val matrixParams = matrixViewModel.getMatrixParams()
    val rows = matrixParams?.rows ?: 2
    val cols = matrixParams?.cols ?: 2
    // val resultrows = if (matrixViewModel.checkToShowSecondMatrix()) rows else 1
    val matrix1 = remember { mutableStateOf(Array(rows) { DoubleArray(rows) { 0.0 } }) }
    val matrix2 = remember { mutableStateOf(Array(rows) { DoubleArray(rows) { 0.0 } }) }
    val resultMatrix = remember { mutableStateOf(Array(rows) { DoubleArray(rows) { 0.0 } }) }
    val numberResult = remember { mutableStateOf(String()) }


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
            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        MatrixInput(matrix = matrix1.value, onValueChange = { row, col, newValue ->
            matrix1.value[row][col] = newValue
        })

        Spacer(modifier = Modifier.height(30.dp))

        if (matrixViewModel.checkToShowSecondMatrix()) {
            Text(
                text = "Matrix 2",
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            MatrixInput(matrix = matrix2.value, onValueChange = { row, col, newValue ->
                matrix2.value[row][col] = newValue
            })
        }

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { matrixViewModel.operation(matrix1.value, matrix2.value) },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Pink80,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                )
            ) {
                Text(
                    "Calculate",
                    style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = {
                    matrixViewModel.clearResult()
                },
                modifier = Modifier.weight(0.5f) // кнопка будет занимать всё доступное пространство после первой кнопки
            ) {
                Text(
                    text = "Clear",
                    style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Result",
            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        if (matrixViewModel.chechToShowResultMatrix()) {
            ReadOnlyMatrix(matrix = resultMatrix.value)
        } else {
            Text(
                text = numberResult.value,
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }
    }

    LaunchedEffect(matrixViewModel.screenState) {
        matrixViewModel.screenState.collectLatest { state ->
            state.result?.let { result ->
                resultMatrix.value = result.data
                numberResult.value = matrixToString(result.data).replace('[', ' ').replace(']', ' ')
            }
        }
    }
}

@Composable
fun MatrixInput(
    matrix: Array<DoubleArray>,
    onValueChange: (row: Int, col: Int, newValue: Double) -> Unit
) {
    Column {
        for (i in matrix.indices) {
            Row {
                for (j in matrix[i].indices) {
                    MatrixInputField(
                        value = matrix[i][j].toString(),
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
    onValueChange: (Double) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(value)) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            if (it.text.length <= 3) {
                text = it
                onValueChange(
                    checkInput(it.text)
                )
            } else {
                text = it.copy(text = it.text.take(3))
                onValueChange(
                    checkInput(it.text.take(3))
                )
            }
        },
        modifier = Modifier
            .padding(5.dp)
            .size(60.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, textAlign = TextAlign.Center),
        singleLine = true,
        maxLines = 1
    )
}

fun checkInput(value: String): Double {
    return try {
        if (value.isNotEmpty()) value.toDouble() else 0.0
    } catch (e: NumberFormatException) {
        0.0
    }
}

fun formatDouble(value: Double): String {
    val decimalFormat = java.text.DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH))
    return decimalFormat.format(value)
}

@Composable
fun ReadOnlyMatrix(matrix: Array<DoubleArray>) {
    Column {
        for (i in matrix.indices) {
            Row {
                for (j in matrix[i].indices) {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(60.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                            )
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(4.dp),
                            )

                    ) {
                        Text(
                            text = formatDouble(matrix[i][j]),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

