package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MatrixSizeScreen(onMatrixSizeSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MatrixSizeButton(text = "2x2") { onMatrixSizeSelected(2) }
        MatrixSizeButton(text = "3x3") { onMatrixSizeSelected(3) }
        MatrixSizeButton(text = "4x4") { onMatrixSizeSelected(4) }
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

@Preview
@Composable
fun MatrixSizeScreenPreview() {
    MatrixSizeScreen(onMatrixSizeSelected = {})
}

