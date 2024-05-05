package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MatrixCalculatorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Матричный калькулятор",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Кнопки для операций
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Ряд с кнопками сложения и вычитания
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                OperationButton(text = "+") {
                    // Обработка нажатия кнопки сложения
                    // Ваш код обработчика
                }
                Spacer(modifier = Modifier.height(16.dp))
                OperationButton(text = "*") {
                    // Обработка нажатия кнопки вычитания
                    // Ваш код обработчика
                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Кнопка умножения
                OperationButton(text = "-") {
                    // Обработка нажатия кнопки умножения
                    // Ваш код обработчика
                }
            }
        }
    }
}

@Composable
fun OperationButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

@Preview
@Composable
fun PreviewMatrixCalculatorScreen() {
    MatrixCalculatorScreen()
}
