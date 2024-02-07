package com.example.examentecnico.login.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//clase que consume al cliente
class LoginService @Inject constructor(private val loginClient: LoginClient) {

    suspend fun doLogin(user: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = loginClient.doLogin()
            response.body()?.success ?: false
        }
    }
}