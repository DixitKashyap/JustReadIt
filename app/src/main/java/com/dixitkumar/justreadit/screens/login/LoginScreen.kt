package com.dixitkumar.justreadit.screens.login

import android.graphics.drawable.Icon
import android.text.InputType
import android.widget.ScrollView
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dixitkumar.justreadit.R

@Composable
fun LoginScreen(navController: NavController){
    LoginScreenUi()
}

@Preview(showBackground = true)
@Composable
fun LoginScreenUi(){
    val username = rememberSaveable{ mutableStateOf("")}
    val password = remember {
        mutableStateOf("")
    }
    val isFocused = remember {
        mutableStateOf(false)
    }
        Surface(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            color = Color.White) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                Spacer(modifier = Modifier.height(20.dp))

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

                InputField(
                    icon = Icons.Default.PermIdentity,
                    elevation = 20.dp,
                    textState =username,
                    keyboardType = KeyboardType.Email,
                    color = colorResource(id = R.color.blue),
                    label = "Username")
                InputField(
                    icon = Icons.Default.LockOpen,
                    elevation = 20.dp,
                    textState =password,
                    keyboardType = KeyboardType.Password,
                    color =  colorResource(id = R.color.blue),
                    label = "Password"
                )

                Spacer(modifier = Modifier.height(30.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End){
                    Text(text = "Forgot Password?", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(20.dp))
                }

                Spacer(modifier = Modifier.height(30.dp))
                LoginButton(label = "LOG IN", onClick = { /*TODO*/}, cornerRadius = 40.dp,
                    modifier = Modifier
                        .width(220.dp)
                        .height(60.dp),
                    fontSize = 20,
                    color = colorResource(id = R.color.blue),
                    elevation = 20.dp)

                Spacer(modifier = Modifier.height(50.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Or Connect Using",
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(30.dp))
                MoreLoginOptions()

                Spacer(modifier = Modifier.height(45.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = "Don't have an account?")
                    Text(text = " Sign Up",color = colorResource(id = R.color.blue), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
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
          color : Color
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

    Spacer(modifier = Modifier.height(30.dp))
    Card(modifier = Modifier.width((screenWidth/100)*95),
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
    onClick :() -> Unit,
    cornerRadius : Dp,
    color: Color,
    fontSize : Int = 15,
    fontWeight: FontWeight = FontWeight.Bold,
    elevation: Dp

){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        ){

        Button(onClick = { onClick() },
            modifier = modifier,
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

