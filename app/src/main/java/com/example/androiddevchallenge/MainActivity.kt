/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val timerInputValue = remember { mutableStateOf(TextFieldValue()) }

    val timerIsFinished = remember { mutableStateOf(false) }

    val timerIsRunning = remember { mutableStateOf(false) }

    val color = if (timerIsFinished.value) Color.Red else Color.Green

    val timerIcon =
        if (timerIsFinished.value) R.drawable.ic_baseline_timer_off_24 else R.drawable.ic_baseline_timer_24

    val timerValue = remember { mutableStateOf(0) }

    Surface(color = color) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = timerIcon),
                    contentDescription = "Timer Icon",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }

            if (timerIsRunning.value) {
                Row {
                    Text("${timerValue.value}s", style = MaterialTheme.typography.h2)
                }
            } else {
                Row {
                    TextField(
                        value = timerInputValue.value,
                        label = { Text("Seconds") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { timerInputValue.value = it },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            if (!timerIsRunning.value && !timerIsFinished.value) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            timerIsFinished.value = false
                            if (!timerInputValue.value.text.isNullOrEmpty()) {
                                val milliSeconds = timerInputValue.value.text.toLong() * 1000
                                object : CountDownTimer(milliSeconds, 1000) {
                                    var counter = 0
                                    override fun onTick(millisUntilFinished: Long) {
                                        timerIsRunning.value = true
                                        counter++
                                        timerValue.value = counter
                                    }

                                    override fun onFinish() {
                                        timerIsRunning.value = false
                                        timerIsFinished.value = true
                                    }
                                }.start()
                            }
                        }
                    ) {
                        Text("Start Timer")
                    }
                }
            }

            if (timerIsFinished.value) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            timerIsFinished.value = false
                            timerInputValue.value = TextFieldValue()
                        }
                    ) {
                        Text("Clear")
                    }
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
