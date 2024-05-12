package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MatrixSizeScreen(nav: () -> Unit = {}, onMatrixParamsSelected: (rows: Int, cols: Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MatrixSizeButton(text = "2x2") {
            onMatrixParamsSelected(2,2)
            nav()
        }
        MatrixSizeButton(text = "3x3") {  onMatrixParamsSelected(3,3) }
        MatrixSizeButton(text = "4x4") { onMatrixParamsSelected(4,4) }
    }
}

@Composable
fun MatrixSizeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(160.dp)
    ) {
        Text(text = text)
    }
}
