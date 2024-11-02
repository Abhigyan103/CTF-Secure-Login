package com.abhigyan.securelogin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.SecureLoginTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecureLoginTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = { TopAppBar(title = { Text(text = "Secure Login") }, colors =  TopAppBarDefaults.topAppBarColors().copy(containerColor = MaterialTheme.colorScheme.primaryContainer))}) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier){
    val pass = "admin123"
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var loginSuccessful by remember { mutableStateOf<Boolean?>(null) }
    val buttonText by remember { derivedStateOf {
        if(loading) "Logging in.." else "Login"
    } }
    val message by remember {
        derivedStateOf { if(loginSuccessful==null) "" else if(loginSuccessful==false) "*Invalid username or password" else "Logged in successfully" }
    }
    val errorColor = MaterialTheme.colorScheme.error
    val messageColor by remember {
        derivedStateOf { if(loginSuccessful==true) Color.Green else errorColor }
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(passwordVisible) {
        if (passwordVisible) {
            delay(2000)
            passwordVisible = false
        }
    }
    Box(modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Box(modifier = Modifier.height(128.dp))
            Text(text = "Enter login details:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Box(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Username") })
            Box(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                visualTransformation = if(!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    val iconButton = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible=!passwordVisible }) {
                        Icon(iconButton, "Show password")
                    }
                }
            )
            Box(modifier = Modifier.height(6.dp))
            Text(text = message, color = messageColor)
            Box(modifier = Modifier.height(6.dp))
            Button(onClick = {
                loading = true
                coroutineScope.launch {
                    delay(2000)
                    if(username == "admin" && password == pass){
                        Log.i("debug", f("zftrhf")+f("y|6O[9;")+"_"+f("U65")+f("_E5;Y;<:Zh")+e("bFqqXk[4eUV4UR>>")+"}")
                        loginSuccessful=true
                    }else {
                        loginSuccessful=false
                        Log.i("flag zeroday", "email != admin || password != admin123")
                    }
                    loading = false
                }

            }, enabled = !loading) {
                Row(
                    Modifier.height(32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (loading) CircularProgressIndicator(
                        modifier = Modifier
                            .height(22.dp)
                            .width(22.dp)
                            .padding(end = 8.dp), color = MaterialTheme.colorScheme.onPrimaryContainer,
                        strokeWidth = 1.dp
                    )
                    Text(text = buttonText, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}

@OptIn(ExperimentalEncodingApi::class)
fun e(key: String): String{
    var key2= key.map { it-1}.fold("") { acc, c -> acc + c }
    key2 = Base64.decode(key2.toByteArray()).decodeToString()
    return key2
}
fun f(key:String): String{
    var s=""
    key.forEachIndexed{i,it -> s+=it-i}
    return s
}