package com.example.wallpapercomposeapp.helpers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//state hoisting example
@Composable
fun CounterApp() {
    val counter = rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Counter(value = counter.intValue) { newValue ->
            counter.intValue = newValue // Update the counter value
        }
        Spacer(modifier = Modifier.height(16.dp))
        ResetButton(onClick = { counter.intValue = 0 }) // Reset the counter
    }
}

@Composable
fun Counter(value: Int, onUpdate: (Int) -> Unit) {
    Text(text = "Counter: $value")
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onUpdate(value + 1) }) {
            Text(text = "Increment")
        }
    }
}

@Composable
fun ResetButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Reset")
    }
}