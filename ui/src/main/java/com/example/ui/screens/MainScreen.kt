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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utlis.Operation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixCalculatorScreen(onOperationSelected: (Operation) -> Unit) {
    Scaffold(
        modifier = Modifier.padding(16.dp),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = {
                    Text(
                        text = "Матричный калькулятор",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OperationButton(text = "+") {
                    onOperationSelected(Operation.Add)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OperationButton(text = "*") {
                    onOperationSelected(Operation.Multiply)
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OperationButton(text = "-") {
                    onOperationSelected(Operation.Subtract)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OperationButton(text = "T") {
                    onOperationSelected(Operation.Transpose)
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                OperationButton(text = "R") {
                    onOperationSelected(Operation.Rank)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OperationButton(text = "D") {
                    onOperationSelected(Operation.Determinant)
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
        Text(text = text, fontSize = 100.sp)
    }
}
