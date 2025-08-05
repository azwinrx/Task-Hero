package com.azwin.dotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.azwin.dotask.ui.component.navbarSwitch
import com.azwin.dotask.ui.component.timerLogic

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                timerLogic()
            }
        }
    }
}


@Composable
fun doTask() {
    Row(
        modifier = Modifier
            .fillMaxSize()

    ){
        navbarSwitch()
    }


}



@Preview(
    showBackground = true,
    showSystemUi = true
    )
@Composable
fun doTaskPreview() {
    doTask()
}