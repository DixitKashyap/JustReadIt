package com.dixitkumar.justreadit.screens.signup

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Voicemail
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.login.InputField
import com.dixitkumar.justreadit.screens.login.LoginButton
import com.dixitkumar.justreadit.screens.login.LoginScreen
import com.dixitkumar.justreadit.screens.login.LoginScreenViewModel
import com.dixitkumar.justreadit.screens.login.loginOrSignInRow

@Composable
fun SignUpScreen(navController: NavController,
                 viewModel: LoginScreenViewModel = hiltViewModel()){

   SignUpScreenUi(navController,viewModel = viewModel)
}

@Composable
fun SignUpScreenUi(navController: NavController,viewModel: LoginScreenViewModel) {
    val username = remember { mutableStateOf("") }
    val username_state = remember { mutableStateOf("Already Exist") }
    val email = remember { mutableStateOf("") }
    val email_state = remember { mutableStateOf("Account Already Exist") }
    val phone = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val showPassword_one = remember { mutableStateOf(false) }
    val showPassword_two = remember { mutableStateOf(false) }
    val password_equals = remember { mutableStateOf(true) }


    val buttonClicked = remember {
        mutableStateOf(false)
    }

    val signInState = remember{ mutableStateOf("SIGN IN") }

    Surface(
        modifier = Modifier
            .fillMaxSize(), color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.Gray,
                    contentDescription = "Back Button"
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Let's Get Started!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Create an account if you don't have one",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(40.dp))
            InputField(
                icon = Icons.Default.PermIdentity,
                elevation = 20.dp,
                textState = username,
                keyboardType = KeyboardType.Email,
                color = colorResource(id = R.color.blue),
                label = "Username",
                onClick = {
                    if(!viewModel.alreadyThereName.value) {
                        viewModel.getUserName(username.value.trim())
                    }else{
                        viewModel.alreadyThereName.value = false
                    }
                }
            )
            if (viewModel.alreadyThereName.value) {
                Text(
                    text = username_state.value,
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }


            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                icon = Icons.Default.Email,
                elevation = 20.dp,
                textState = email,
                keyboardType = KeyboardType.Email,
                color = colorResource(id = R.color.blue),
                label = "Email",
                onClick = {
                    if(!viewModel.alreadyThereEmail.value) {
                        viewModel.getUserName(email = email.value.trim())
                    }else{
                        viewModel.alreadyThereEmail.value = false
                    }
                }
            )
            if (viewModel.alreadyThereEmail.value) {
                Text(
                    text = email_state.value,
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
                Spacer(modifier = Modifier.height(20.dp))
                InputField(
                    icon = Icons.Default.PhoneAndroid,
                    elevation = 20.dp,
                    textState = phone,
                    keyboardType = KeyboardType.Email,
                    color = colorResource(id = R.color.blue),
                    label = "Phone"
                )
                Spacer(modifier = Modifier.height(20.dp))
                InputField(
                    icon = Icons.Default.LockOpen,
                    elevation = 20.dp,
                    textState = password,
                    keyboardType = KeyboardType.Email,
                    color = colorResource(id = R.color.blue),
                    label = "Password",
                    isPasswordFiled = true, showPassword = showPassword_one
                )

                Spacer(modifier = Modifier.height(20.dp))
                InputField(
                    icon = Icons.Default.LockOpen,
                    elevation = 20.dp,
                    textState = confirmPassword,
                    keyboardType = KeyboardType.Email,
                    color = colorResource(id = R.color.blue),
                    label = "Confirm Password",
                    isPasswordFiled = true,
                    showPassword = showPassword_two,
                    onClick = {
                        if(password.value.trim().contentEquals(confirmPassword.value.trim())==false){
                            password_equals.value = false
                        }else{
                            password_equals.value = true
                        }
                    }
                )
            if (!password_equals.value) {
                Text(
                    text = "Password Not Matching",
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }


                Spacer(modifier = Modifier.height(40.dp))
                LoginButton(label = signInState, cornerRadius = 40.dp,
                    modifier = Modifier
                        .width(220.dp)
                        .height(60.dp),
                    fontSize = 18,
                    enabled =if(!viewModel.alreadyThereEmail.value && !viewModel.alreadyThereName.value && password_equals.value) true else false,
                    color = colorResource(id = R.color.blue),
                    elevation = 20.dp,
                    onClick = {
                        if (email.value.trim().toString().isNotEmpty()
                            && username.value.trim().toString().isNotEmpty()
                            && phone.value.trim().toString().isNotEmpty()
                            && password.value.trim().toString().isNotEmpty()
                            && confirmPassword.value.trim().toString().isNotEmpty()
                        ) {
                            if (password.value.trim().toString()
                                    .contentEquals(confirmPassword.value.trim().toString())
                            ) {
                                viewModel.getUserName(username.value.trim())
                                viewModel.CreateUserWithEmailAndPassword(
                                    email = email.value.trim().toString(),
                                    username = username.value.trim().toString(),
                                    phone = phone.value.trim().toString(),
                                    password = password.value.trim().toString(),
                                    confirmPassword = confirmPassword.value.trim().toString()
                                ) {

                                    navController.popBackStack()
                                    navController.navigate(route = ReaderScreens.MainScreen.name)
                                }
                            } else {
                                Log.d("TAG", "password not matched")
                            }
                        }
                    })

                Spacer(modifier = Modifier.height(40.dp))
                loginOrSignInRow(msg = "Already have an account? ", label = "Login here") {
                    navController.navigate(route = ReaderScreens.HomeScreen.name)
                }
            }
        }
    }


