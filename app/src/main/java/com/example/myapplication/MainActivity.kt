package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.lang.Math.abs


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier){
    var targetValue by remember { mutableStateOf((0..100).random()) }
    var sliderValue by remember { mutableStateOf(50f) }
    var totalScore by remember { mutableStateOf(0) }
    var commentOnGuess by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.bull_s_eye_game), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = modifier.height(150.dp))
        Text(
            text = stringResource(R.string.target_value, targetValue),
            fontSize = 20.sp
            )
        Spacer(modifier = Modifier.height(120.dp))
        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
            },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor =  Color(red = 128, green = 0, blue = 128),
                activeTickColor =  Color(red = 128, green = 0, blue = 128),
                activeTrackColor = Color(red = 128, green = 0, blue = 128)
            )
        )
        Spacer(modifier = Modifier.height(90.dp))
        Button(
            onClick = {
                val score = calculateScore(targetValue, sliderValue)
                totalScore += score
                targetValue = (0..100).random()
                sliderValue = 50f
                commentOnGuess = getComment(score)
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(red = 128, green = 0, blue = 128))
        ) {
            Text(stringResource(R.string.hit_me))
        }
        Spacer(modifier = modifier.height(70.dp))
        Text(text = stringResource(R.string.your_score, totalScore))
        Spacer(modifier = modifier.height(60.dp))
        Text(text = commentOnGuess,color = Color.Gray, fontSize = 14.sp)
    }

}

fun calculateScore(target: Int, guess: Float): Int {
    val difference = abs(target - guess)
    return when {
        difference <= 3 -> 5
        difference <= 8 -> 1
        else -> 0
    }
}

fun getComment(score:Int):String{
    return when {
        score == 5 -> "Perfect! You scored 5 points"
        score == 1 -> "Good, You scored 1 point, Try again"
        else -> "Try again to get closer"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
    GameScreen()
    }
}