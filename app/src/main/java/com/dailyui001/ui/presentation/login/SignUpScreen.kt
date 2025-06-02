package com.dailyui001.ui.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.app.theme.utils.sdp
import com.app.theme.utils.ssp
import com.dailyui001.R
import com.dailyui001.base.BaseScreen
import com.dailyui001.base.BaseViewModel
import com.dailyui001.ui.navigation.NavigationAction
import com.dailyui001.ui.presentation.component.BaseButton
import com.dailyui001.ui.presentation.component.BaseText
import com.dailyui001.ui.presentation.component.BaseTextField
import com.dailyui001.ui.presentation.component.CircularViewWithIcon
import com.dailyui001.ui.presentation.component.Heading
import com.dailyui001.ui.presentation.component.TextWithTextAlign
import com.dailyui001.ui.presentation.component.isLandScape
import com.dailyui001.ui.theme.DailyUI001Color
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SignUpScreen(
    baseUIEvents: SharedFlow<BaseViewModel.BaseViewModelEvents> = MutableSharedFlow(),
    state: SignUpScreenState = SignUpScreenState(),
    navigation: (NavigationAction) -> Unit = {},
    actionEvent: (SignUpViewModel.ActionEvent) -> Unit = {}
) {

    BaseScreen(
        baseUIEvents = baseUIEvents,
        navigation = navigation,
        bgColor = DailyUI001Color.bgColor
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isLandScape()) {
                Spacer(modifier = Modifier.height(80.sdp))

                Heading(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Sign Up",
                )

                Spacer(modifier = Modifier.height(18.sdp))

                BaseText(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Please login or sign up to continue using\nour app",
                    color = DailyUI001Color.subTitleTextColor,
                )

                Spacer(modifier = Modifier.height(20.sdp))

                BaseText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Enter via Social Networks",
                    color = DailyUI001Color.subTitleTextColor,
                )

                Spacer(modifier = Modifier.height(20.sdp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularViewWithIcon(
                        modifier = Modifier,
                        icon = R.drawable.ic_apple,
                        iconColor = DailyUI001Color.iconColor
                    )

                    Spacer(modifier = Modifier.width(8.sdp))

                    CircularViewWithIcon(
                        modifier = Modifier,
                        icon = R.drawable.ic_google,
                        iconColor = DailyUI001Color.iconColor
                    )
                }

                Spacer(modifier = Modifier.height(20.sdp))

                TextWithTextAlign(
                    text = "or login with\n" +
                            "email",
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.SemiBold,
                    color = DailyUI001Color.textColor,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(80.sdp))

                BaseTextField(
                    value = state.email,
                    onValueChange = {
                        actionEvent.invoke(
                            SignUpViewModel.ActionEvent.OnEmailChange(
                                it
                            )
                        )
                    },
                    label = { Text("Enter Email") },
                    visualTransformation = VisualTransformation.None
                )

                Spacer(modifier = Modifier.height(24.sdp))

                BaseTextField(
                    value = state.password,
                    onValueChange = {
                        actionEvent.invoke(
                            SignUpViewModel.ActionEvent.OnPasswordChange(
                                it
                            )
                        )
                    },
                    label = { Text("Enter Password") },
                    visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { actionEvent.invoke(SignUpViewModel.ActionEvent.TogglePasswordVisibility) }) {
                            Icon(
                                painter = painterResource(
                                    if (state.passwordVisible) R.drawable.ic_visibility
                                    else R.drawable.ic_visibility_off
                                ),
                                tint = DailyUI001Color.textColor,
                                contentDescription = if (state.passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.sdp))

                BaseButton(
                    text = state.buttonText,
                    onClick = { actionEvent.invoke(SignUpViewModel.ActionEvent.OnCreateAccountClick) },
                    modifier = Modifier.fillMaxWidth(),
                    enable = true,
                    backgroundColor = DailyUI001Color.buttonBgColor,
                    textColor = DailyUI001Color.buttonTextColor,
                    cornerRadius = 30
                )

                Spacer(modifier = Modifier.height(36.sdp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BaseText(
                        modifier = Modifier,
                        text = "Already have an account? ",
                        color = DailyUI001Color.subTitleTextColor,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 14.ssp
                    )


                    BaseText(
                        modifier = Modifier,
                        text = "Login",
                        color = DailyUI001Color.buttonBgColor,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 14.ssp,
                        textDecoration = TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 36.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BaseText(
                        modifier = Modifier,
                        text = "I accept App’s ",
                        color = DailyUI001Color.termTextColor,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 12.ssp
                    )


                    BaseText(
                        modifier = Modifier,
                        text = "Terms of Use",
                        color = DailyUI001Color.termTextColor,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 12.ssp,
                        textDecoration = TextDecoration.Underline
                    )

                    BaseText(
                        modifier = Modifier,
                        text = " and ",
                        color = DailyUI001Color.termTextColor,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 12.ssp
                    )

                    BaseText(
                        modifier = Modifier,
                        text = "Privacy Policy.",
                        color = DailyUI001Color.termTextColor,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 12.ssp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            else {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.sdp)
                ){
                    Heading(
                        modifier = Modifier.align(Alignment.Start),
                        text = "Sign Up",
                    )

                    Spacer(modifier = Modifier.height(12.sdp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BaseText(
                            modifier = Modifier.align(alignment = Alignment.CenterVertically),
                            text = "Please login or sign up to continue using\nour app",
                            color = DailyUI001Color.subTitleTextColor,
                        )

                        BaseTextField(
                            modifier = Modifier.width(335.sdp),
                            value = state.email,
                            onValueChange = { actionEvent.invoke(SignUpViewModel.ActionEvent.OnEmailChange(it)) },
                            label = { Text("Enter Email") },
                            visualTransformation = VisualTransformation.None
                        )
                    }

                    Spacer(modifier = Modifier.height(18.sdp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 92.sdp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BaseText(
                            modifier = Modifier.align(alignment = Alignment.CenterVertically),
                            text = "Enter via Social Networks",
                            color = DailyUI001Color.subTitleTextColor,
                        )

                        BaseTextField(
                            modifier = Modifier.width(335.sdp),
                            value = state.password,
                            onValueChange = { actionEvent.invoke(SignUpViewModel.ActionEvent.OnPasswordChange(it)) },
                            label = { Text("Enter Password") },
                            visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { actionEvent.invoke(SignUpViewModel.ActionEvent.TogglePasswordVisibility) }) {
                                    Icon(
                                        painter = painterResource(
                                            if (state.passwordVisible) R.drawable.ic_visibility
                                            else R.drawable.ic_visibility_off
                                        ),
                                        contentDescription = if (state.passwordVisible) "Hide password" else "Show password"
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.sdp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 135.sdp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularViewWithIcon(
                                modifier = Modifier,
                                icon = R.drawable.ic_apple,
                                iconColor = DailyUI001Color.iconColor
                            )

                            Spacer(modifier = Modifier.width(8.sdp))

                            CircularViewWithIcon(
                                modifier = Modifier,
                                icon = R.drawable.ic_google,
                                iconColor = DailyUI001Color.iconColor
                            )
                        }

                        BaseButton(
                            text = state.buttonText,
                            onClick = { actionEvent.invoke(SignUpViewModel.ActionEvent.OnCreateAccountClick) },
                            modifier = Modifier.width(335.sdp),
                            enable = true,
                            backgroundColor = DailyUI001Color.buttonBgColor,
                            textColor = DailyUI001Color.buttonTextColor,
                            cornerRadius = 30
                        )
                    }

                    Spacer(modifier = Modifier.height(26.sdp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 141.sdp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        TextWithTextAlign(
                            text = "or login with\n" +
                                    "email",
                            fontSize = 16.ssp,
                            fontWeight = FontWeight.SemiBold,
                            color = DailyUI001Color.loginText,
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )

                        Row(
                            modifier = Modifier.width(335.sdp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            BaseText(
                                modifier = Modifier,
                                text = "Already have an account? ",
                                color = DailyUI001Color.subTitleTextColor,
                                fontSize = 14.ssp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 14.ssp
                            )


                            BaseText(
                                modifier = Modifier,
                                text = "Login",
                                color = DailyUI001Color.buttonBgColor,
                                fontSize = 14.ssp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 14.ssp,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End)
                            .padding(bottom = 36.sdp),
                    ) {
                        Row(
                            modifier = Modifier.width(353.sdp).align(Alignment.BottomEnd),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            BaseText(
                                modifier = Modifier,
                                text = "I accept App’s ",
                                color = DailyUI001Color.termTextColor,
                                fontSize = 12.ssp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 12.ssp
                            )


                            BaseText(
                                modifier = Modifier,
                                text = "Terms of Use",
                                color = DailyUI001Color.termTextColor,
                                fontSize = 12.ssp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 12.ssp,
                                textDecoration = TextDecoration.Underline
                            )

                            BaseText(
                                modifier = Modifier,
                                text = " and ",
                                color = DailyUI001Color.termTextColor,
                                fontSize = 12.ssp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 12.ssp
                            )

                            BaseText(
                                modifier = Modifier,
                                text = "Privacy Policy.",
                                color = DailyUI001Color.termTextColor,
                                fontSize = 12.ssp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 12.ssp,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }
            }
        }

    }

}


@Preview
@Composable
private fun SignUpPreview() {
    SignUpScreen()
}