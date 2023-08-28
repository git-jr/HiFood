package com.paradoxo.hifood.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    state: String,
    onStateChange: (String) -> Unit = {}
) {
    var internalState by remember { mutableStateOf(state) }
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = internalState,
            onValueChange = {
                internalState = it
                onStateChange(it)
            },
            label = {
                Text(
                    text = label,
                    color = Color.Gray,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black
            ),
            placeholder = {
                Text(
                    text = label,
                    color = Color.LightGray,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(52.dp),
        )
    }
}