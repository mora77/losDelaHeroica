package com.example.losdelaheroica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.losdelaheroica.addSong.ui.SongScreen
import com.example.losdelaheroica.addSong.ui.SongViewModel
import com.example.losdelaheroica.core.model.Routes.*
import com.example.losdelaheroica.login.ui.LoginScreen
import com.example.losdelaheroica.login.ui.LoginViewModel
import com.example.losdelaheroica.ui.theme.LosDeLaHeroicaTheme
import com.example.losdelaheroica.ui.theme.backGroundColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val songViewModel: SongViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LosDeLaHeroicaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backGroundColor
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = LoginScreen.idRoute) {
                        composable(LoginScreen.idRoute) {
                            LoginScreen(
                                loginViewModel = loginViewModel,
                                navController = navController
                            )
                        }

                        composable(SongScreen.idRoute) {
                            SongScreen(
                                songViewModel = songViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}