package com.dixitkumar.justreadit.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.navigation.ReaderScreens

@Composable
fun LoginScreen(navController: NavController ,viewModel: LoginScreenViewModel = hiltViewModel()){
    LoginScreenUi(navController,viewModel = viewModel)
}

@Composable
fun LoginScreenUi(
    navController: NavController,
    viewModel: LoginScreenViewModel
){
    val username = rememberSaveable{ mutableStateOf("")}
    val password = remember {
        mutableStateOf("")
    }
    val isFocused = remember {
        mutableStateOf(false)
    }
    val showPassword = remember {
        mutableStateOf(false)
    }


        Surface(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            color = Color.White) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                Spacer(modifier = Modifier.height(10.dp))

                Image(painter = painterResource(id = R.drawable.splash_image),
                    contentDescription = "Splash Image",
                    modifier = Modifier
                        .width(200.dp)
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                    )

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Welcome Back!",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Log in to your existent account of JustReadIt",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray.copy(alpha = 0.8f))

                Spacer(modifier = Modifier.height(30.dp))
                InputField(
                    icon = Icons.Default.PermIdentity,
                    elevation = 20.dp,
                    textState =username,
                    keyboardType = KeyboardType.Email,
                    color = colorResource(id = R.color.blue),
                    label = "Username")
                Spacer(modifier = Modifier.height(20.dp))

                InputField(
                    icon = Icons.Default.LockOpen,
                    elevation = 20.dp,
                    textState =password,
                    keyboardType = KeyboardType.Password,
                    color =  colorResource(id = R.color.blue),
                    label = "Password",
                    showPassword = showPassword,
                    isPasswordFiled = true
                )

                Spacer(modifier = Modifier.height(20.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End){
                    Text(text = "Forgot Password?", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(20.dp))
                }

                Spacer(modifier = Modifier.height(30.dp))
                LoginButton(label = "LOG IN", cornerRadius = 40.dp,
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    fontSize = 17,
                    color = colorResource(id = R.color.blue),
                    elevation = 20.dp, onClick = {
                        if(username.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()){
                            viewModel.SignInWithEmailAndPassword(username.value.trim().toString(),password.value.trim().toString()){
                                navController.popBackStack()
                                navController.navigate(route = ReaderScreens.HomeScreen.name)
                            }
                        }
                    })

                Spacer(modifier = Modifier.height(30.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Or Connect Using",
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(30.dp))
                MoreLoginOptions()

                Spacer(modifier = Modifier.height(30.dp))
                loginOrSignInRow(msg = "Don't hava an account? ", label ="Sign Up" ){
                    navController.navigate(route = ReaderScreens.SignupScreen.name)
                }

            }
        }
}

@Composable
fun loginOrSignInRow(
    msg : String,
    label : String,
    onClick: () -> Unit ={}
){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(text = msg)
        Text(text = label,
            color = colorResource(id = R.color.blue),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                onClick()
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
          label : String,
          textState : MutableState<String>,
          elevation : Dp,
          icon : ImageVector,
          keyboardType: KeyboardType,
          showPassword : MutableState<Boolean> = remember {
              mutableStateOf(true)
          },
          color : Color,
          isPasswordFiled : Boolean = false
){

    //Adjusting Card According to the display width
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    //Taking a reference of keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    //Checking Whether the text field is in focus or not
    val isFocused :MutableState<Boolean> = remember {
        mutableStateOf(false)
    }


    Card(modifier = Modifier
        .width((screenWidth / 100) * 95)
        .height(60.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    , elevation = CardDefaults.cardElevation(elevation),border = if(isFocused.value)BorderStroke(width = 2.dp, color = color)
        else BorderStroke(width = 2.dp,color =Color.White)
    ){

        Row (modifier = Modifier.background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.width(10.dp))
           Icon(imageVector = icon, contentDescription ="Username icon",
                tint = if(isFocused.value) color else Color.LightGray,
                modifier = Modifier.padding(4.dp))

            TextField(
                value = textState.value,
                onValueChange = { newText ->
                    textState.value = newText
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused.value = it.hasFocus
                    },
                textStyle = TextStyle(
                    color = if(isFocused.value) color else Color.LightGray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                ), keyboardActions = KeyboardActions {
                     isFocused.value = false
                     keyboardController?.hide()
                },
                visualTransformation =
                if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if(isPasswordFiled){

                    val (icon, iconColor) = if (showPassword.value) {
                        Pair(
                            Icons.Filled.Visibility,
                            colorResource(id = R.color.blue)
                        )
                    } else {
                        Pair(Icons.Filled.VisibilityOff, colorResource(id = R.color.grey))
                    }

                    IconButton(onClick = { showPassword.value = !showPassword.value }) {
                        Icon(
                            icon,
                            contentDescription = "Visibility",
                            tint = iconColor
                        )
                    }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    autoCorrect = true
                ),
                placeholder = { Text("$label",color = if(isFocused.value) color else Color.LightGray)},
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = if(isFocused.value) color else Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = if(isFocused.value) color else Color.LightGray,
                ))
        }
    }
}

@Composable
fun LoginButton(
    modifier: Modifier,
    label: String,
    cornerRadius : Dp,
    color: Color,
    fontSize : Int = 15,
    fontWeight: FontWeight = FontWeight.Bold,
    elevation: Dp,
    enabled : Boolean = true,
    onClick : () -> Unit = {}

){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        ){

        Button(onClick = { onClick() },
            modifier = modifier,
            enabled = enabled,
            elevation = ButtonDefaults.buttonElevation(elevation),
            shape = RoundedCornerShape(cornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text(text = "$label",
                fontSize = fontSize.sp,
                fontWeight =fontWeight,
                color = Color.White
            )
        }
    }
}


@Composable
fun MoreLoginOptions(){
Row(modifier = Modifier.fillMaxWidth()
, horizontalArrangement = Arrangement.Center){

        LoginButton(modifier = Modifier
            .padding(start = 20.dp, end = 10.dp)
            .height(50.dp)
            .width(150.dp),
            label = "Facebook",
            onClick = { /*TODO*/ },
            cornerRadius = 20.dp,
            color = colorResource(id = R.color.dark_blue),
            elevation = 0.dp)
        LoginButton(modifier = Modifier
            .padding(start = 20.dp, end = 10.dp)
            .height(50.dp)
            .width(150.dp),
            label = "Google",
            onClick = { /*TODO*/ },
            cornerRadius = 20.dp,
            color = colorResource(id = R.color.red),
            elevation = 0.dp)
}
}

