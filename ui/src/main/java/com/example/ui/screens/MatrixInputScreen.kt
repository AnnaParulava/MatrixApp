package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Pink80
import com.example.ui.viewmodel.MatrixViewModel
import kotlinx.coroutines.flow.collectLatest
import mappers.matrixToString
import java.text.DecimalFormatSymbols
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixInputScreen(nav: () -> Unit = {}, matrixViewModel: MatrixViewModel) {
    // Mutable state for holding matrices
    val matrixParams = matrixViewModel.getMatrixParams()
    val rows = matrixParams?.rows ?: 2
    val cols = matrixParams?.cols ?: 2
    val matrix1 = remember { mutableStateOf(Array(rows) { DoubleArray(rows) { 0.0 } }) }
    val matrix2 = remember { mutableStateOf(Array(rows) { DoubleArray(rows) { 0.0 } }) }
    val resultMatrix = remember { mutableStateOf(Array(rows) { DoubleArray(rows) { 0.0 } }) }
    val numberResult = remember { mutableStateOf(String()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = {
                    Text(
                        text = matrixViewModel.operationTitle(),
                        fontSize = 24.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            Modifier
                                .size(25.dp)
                                .fillMaxWidth(),
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Матрица 1",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold, fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            MatrixInput(matrix = matrix1.value, onValueChange = { row, col, newValue ->
                matrix1.value[row][col] = newValue
            })

            Spacer(modifier = Modifier.height(30.dp))

            if (matrixViewModel.checkToShowSecondMatrix()) {
                Text(
                    text = "Матрица 2",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
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
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                //     horizontalArrangement = Arrangement.SpaceBetween
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
                        "Вычислить",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold, fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    onClick = {
                        matrixViewModel.clearResult()
                    },
                    modifier = Modifier.weight(0.6f) // кнопка будет занимать всё доступное пространство после первой кнопки
                ) {
                    Text(
                        text = "Очистить",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Результат",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold, fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            if (matrixViewModel.chechToShowResultMatrix()) {
                ReadOnlyMatrix(matrix = resultMatrix.value)
            } else {
                Text(
                    text = numberResult.value,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold, fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),


                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
        }

        LaunchedEffect(matrixViewModel.screenState) {
            matrixViewModel.screenState.collectLatest { state ->
                state.result?.let { result ->
                    resultMatrix.value = result.data
                    numberResult.value =
                        matrixToString(result.data).replace('[', ' ').replace(']', ' ')
                }
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

