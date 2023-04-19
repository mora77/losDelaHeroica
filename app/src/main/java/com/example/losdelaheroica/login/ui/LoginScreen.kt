package com.example.losdelaheroica.login.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.losdelaheroica.R
import com.example.losdelaheroica.ui.theme.*

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(Modifier.align(Alignment.End))
        Body(
            modifier = Modifier,
            loginViewModel = loginViewModel
        )
        Footer(Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun Footer(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "¿No tienes una cuenta? ",
            fontSize = 14.sp,
            color = textGray
        )
        Text(
            text = " Crear cuenta",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = fluorescentBlue
        )
    }
}

@Composable
fun Body(modifier: Modifier, loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val pass: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnabled by loginViewModel.isLoginEnabled.observeAsState(initial = false)
    Column(modifier = modifier) {
        MusicalGroupImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(35.dp))
        TitleAndInstructions()
        Spacer(modifier = Modifier.size(35.dp))
        EmailTextField(email) {
            loginViewModel.onLoginChange(email = it, password = pass)
        }
        Spacer(modifier = Modifier.size(20.dp))
        PasswordTextField(pass) {
            loginViewModel.onLoginChange(email = email, password = it)
        }
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(isLoginEnabled, Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(15.dp))
        ForgotPassText(Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun ForgotPassText(modifier: Modifier) {
    Text(
        text = "¿Olvidaste tu contraseña?",
        fontSize = 12.sp,
        color = fluorescentBlue,
        modifier = modifier
    )
}

@Composable
fun LoginButton(loginEnabled: Boolean, modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { },
        shape = RoundedCornerShape(35.dp),
        enabled = loginEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = fluorescentBlue,
            contentColor = darkGunmetal,
            disabledBackgroundColor = fluorescentBlueDisable,
            disabledContentColor = textGray
        )
    ) {
        Text(
            text = "Ingresar",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 60.dp)
        )
    }
}

@Composable
fun TitleAndInstructions() {
    Column(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Login", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = textTitleWhite
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Por favor ingresa tus datos para continuar",
            fontSize = 14.sp,
            color = textGray
        )
    }
}

@Composable
fun PasswordTextField(pass: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = pass,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        maxLines = 1,
        shape = RoundedCornerShape(17.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = textTitleWhite,
            backgroundColor = jacartaColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        trailingIcon = {
            val iconField = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = iconField, contentDescription = "Show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun EmailTextField(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Correo electrónico") },
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(17.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = textTitleWhite,
            backgroundColor = jacartaColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        )
    )
}

@Composable
fun MusicalGroupImage(modifier: Modifier) {
    Image(
        modifier = modifier.size(width = 280.dp, height = 255.dp),
        painter = painterResource(id = R.drawable.losdelaheroicalogo),
        contentDescription = "Group Logo"
    )
}

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        tint = fluorescentBlue,
        contentDescription = "Close app",
        modifier = modifier.clickable { activity.finish() })
}