package com.example.losdelaheroica.core.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.losdelaheroica.ui.theme.darkGunmetal
import com.example.losdelaheroica.ui.theme.fluorescentBlue
import com.example.losdelaheroica.ui.theme.fluorescentBlueDisable
import com.example.losdelaheroica.ui.theme.textGray

@Composable
fun CommonButton(modifier: Modifier, onClick:() -> Unit, buttonEnabled: Boolean, text: String){
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(35.dp),
        enabled = buttonEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = fluorescentBlue,
            contentColor = darkGunmetal,
            disabledBackgroundColor = fluorescentBlueDisable,
            disabledContentColor = textGray
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 60.dp)
        )
    }
}