package com.example.losdelaheroica.core.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.losdelaheroica.ui.theme.jacartaColor
import com.example.losdelaheroica.ui.theme.textGray
import com.example.losdelaheroica.ui.theme.textTitleWhite

@Composable
fun CommonTextField(value: String, onTextChanged: (String) -> Unit, placeHolder: String){
    TextField(
        value = value,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = placeHolder, color = textGray) },
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(17.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(
            textColor = textTitleWhite,
            backgroundColor = jacartaColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}