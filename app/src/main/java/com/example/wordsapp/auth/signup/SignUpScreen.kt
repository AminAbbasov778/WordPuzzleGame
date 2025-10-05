package com.example.wordsapp.auth.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.ui.theme.Inknut40
import com.example.wordsapp.ui.theme.Inter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    navController: NavHostController,
    onIntent: (SignUpIntent) -> Unit,
    state: SignUpState,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.stickman_walking))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold {
            Image(
                painter = painterResource(R.drawable.bg),
                contentScale = ContentScale.Crop,
                contentDescription = "bg",
                modifier = Modifier.fillMaxSize()
            )

            if (state.isLoading) {

                Box(modifier = Modifier.fillMaxSize()) {
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.Center)
                    )
                }

            } else {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())
                    .systemBarsPadding()
                    .fillMaxSize()) {


                    Spacer(modifier = Modifier.height(39.dp))
                    Text(
                        text = "Hanged",
                        fontSize = 40.sp,
                        style = Inknut40,
                        color = Color(0xFFC4C4C4),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(116.dp))
                    Text(
                        text = "Sign up",
                        fontSize = 24.sp,
                        style = Inter,
                        color = Color(0xFFC4C4C4),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(30.dp))


                    Box(contentAlignment = Alignment.CenterStart) {


                        3
                        BasicTextField(
                            value = state.username,
                            onValueChange = { onIntent(SignUpIntent.EnterUsername(it)) },
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    color = Color(0xFF222830),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 32.dp, vertical = 9.dp),
                            singleLine = true,
                            textStyle = Inter.copy(color = Color(0xFFCFCFCF), fontSize = 15.sp),
                            cursorBrush = SolidColor(Color(0xFFCFCFCF)),
                            decorationBox = { innerTextField ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    innerTextField()

                                }


                            }
                        )

                        if (state.username.isBlank()) {
                            Text(
                                text = "Username",
                                color = Color(0xFFCFCFCF),
                                fontSize = 16.sp, modifier = Modifier.padding(start = 52.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))



                    Box(contentAlignment = Alignment.CenterStart) {


                        BasicTextField(
                            value = state.email,
                            onValueChange = { onIntent(SignUpIntent.EnterEmail(it)) },
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    color = Color(0xFF222830),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 32.dp, vertical = 9.dp),
                            singleLine = true,
                            textStyle = Inter.copy(color = Color(0xFFCFCFCF), fontSize = 15.sp),
                            cursorBrush = SolidColor(Color(0xFFCFCFCF)),
                            decorationBox = { innerTextField ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    innerTextField()

                                }


                            }
                        )

                        if (state.email.isBlank()) {
                            Text(
                                text = "Email",
                                color = Color(0xFFCFCFCF),
                                fontSize = 16.sp, modifier = Modifier.padding(start = 52.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))


                    Box(contentAlignment = Alignment.CenterStart) {


                        BasicTextField(
                            value = state.password,
                            onValueChange = { onIntent(SignUpIntent.EnterPassword(it)) },
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    color = Color(0xFF222830),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 9.dp)
                                .padding(start = 32.dp, end = 11.dp),
                            singleLine = true,
                            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            textStyle = Inter.copy(color = Color(0xFFCFCFCF), fontSize = 15.sp),
                            cursorBrush = SolidColor(Color(0xFFCFCFCF)),
                            decorationBox = { innerTextField ->

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        innerTextField()
                                    }

                                    Icon(
                                        painter = painterResource(if (state.isPasswordVisible) R.drawable.ic_visible else R.drawable.ic_invisible),
                                        contentDescription = "visibilty",
                                        tint = Color.Unspecified,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .clickable {
                                                onIntent(SignUpIntent.ChangePasswordVisibility)
                                            })

                                }

                            }
                        )
                        if (state.password.isBlank()) {
                            Text(
                                text = "Password",
                                color = Color(0xFFCFCFCF),
                                fontSize = 16.sp, modifier = Modifier.padding(start = 52.dp)
                            )
                        }
                    }

                    state.errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.padding(horizontal = 22.dp)
                        )
                    }









                    Spacer(modifier = Modifier.height(30.dp))

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 22.dp)
                            .fillMaxWidth()
                            .heightIn(48.dp)
                            .background(
                                color = if (state.email.isNotEmpty() && state.password.isNotEmpty() && state.username.isNotEmpty()) Color(
                                    0xFFC50000
                                ) else Color(0xFFC50000).copy(0.5f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                if (state.email.isNotEmpty() && state.password.isNotEmpty() && state.username.isNotEmpty()) {
                                    onIntent(SignUpIntent.SignUpClicked)
                                }

                            }) {
                        Text(
                            text = "Sign up",
                            style = Inter.copy(color = Color.White, fontSize = 15.sp),
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }
                    Spacer(modifier = Modifier.height(11.dp))

                    Row(
                        modifier = Modifier
                            .padding(22.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f)
                                .background(color = Color(0xFFC5C4C5))
                        )
                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "OR",
                            style = Inter.copy(color = Color(0xFFC4C4C4), fontSize = 14.sp),
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(15.dp))

                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f)
                                .background(color = Color(0xFFC5C4C5))
                        )


                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            fontSize = 14.sp,
                            style = Inter,
                            color = Color(0xFFAAAAAA)
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Sign up",
                            fontSize = 14.sp,
                            style = Inter,
                            color = Color(0xFFC50000), modifier = Modifier.clickable {
                                navController.navigate(Routes.SignInScreen)
                            }
                        )
                    }


                    Spacer(modifier = Modifier.height(22.dp))

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 22.dp)
                            .fillMaxWidth()
                            .heightIn(48.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFFC5C4C5),
                                shape = RoundedCornerShape(10.dp)
                            ).clickable{
                                onIntent(SignUpIntent.ContinueAsGuestClicked)
                            }
                    ) {
                        Text(
                            text = "Continue as guest",
                            style = Inter.copy(color = Color(0xFFC5C4C5), fontSize = 15.sp),
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }

                    if (state.isSignedUp) {
                        navController.popBackStack()
                    }

                }

            }


        }


    }


}


