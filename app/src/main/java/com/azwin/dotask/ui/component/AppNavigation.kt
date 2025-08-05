package com.azwin.dotask.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Enum untuk merepresentasikan pilihan switch
enum class SwitchOption {
    NOTES, TIMER
}

@Composable
fun navbarSwitch() {
    // State untuk menyimpan pilihan yang sedang aktif
    var selectedOption by remember { mutableStateOf(SwitchOption.NOTES) }

    val containerColor = Color(0xFFCCCCCC) // Warna abu-abu terang untuk container
    val activeItemColor = Color(0xFFFFFFFF)  // Warna putih untuk item aktif
    val inactiveItemColor = Color(0xFFFFFFFF).copy(alpha = 0f) // Warna abu-abu terang untuk item tidak aktif)  // Warna putih untuk item aktif
    val inactiveTextColor = Color(0xFF666666) // Warna abu-abu gelap untuk teks tidak aktif
    val activeTextColor = Color(0xFF000000) // Warna hitam untuk teks aktif

    var visible by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        contentAlignment = Alignment.BottomCenter,

        ) {
        Row(
            modifier = Modifier.height(50.dp)
                .width(300.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(containerColor)
                .padding(5.dp),

            ) {
            // Item Notes
            val notesBackgroundColor by animateColorAsState(
                targetValue = if (selectedOption == SwitchOption.NOTES) activeItemColor else inactiveItemColor,
                animationSpec = tween(durationMillis = 300), label = "notesBackgroundColor"
            )
            val notesTextColor by animateColorAsState(
                targetValue = if (selectedOption == SwitchOption.NOTES) activeTextColor else inactiveTextColor,
                animationSpec = tween(durationMillis = 300), label = "notesTextColor"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f) // Membagi ruang secara merata
                    .clip(RoundedCornerShape(25.dp)) // Sedikit lebih kecil dari container agar ada padding
                    .background(notesBackgroundColor)
                    .clickable { selectedOption = SwitchOption.NOTES }

                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Notes",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = notesTextColor,

                    )
            }

            // Item Timer
            val timerBackgroundColor by animateColorAsState(
                targetValue = if (selectedOption == SwitchOption.TIMER) activeItemColor else inactiveItemColor,
                animationSpec = tween(durationMillis = 300), label = "timerBackgroundColor"
            )
            val timerTextColor by animateColorAsState(
                targetValue = if (selectedOption == SwitchOption.TIMER) activeTextColor else inactiveTextColor,
                animationSpec = tween(durationMillis = 300), label = "timerTextColor"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(25.dp))
                    .background(timerBackgroundColor)
                    .clickable { selectedOption = SwitchOption.TIMER }
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Timer",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = timerTextColor
                )
            }
        }
    }
}