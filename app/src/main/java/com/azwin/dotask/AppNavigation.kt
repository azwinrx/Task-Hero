package com.azwin.dotask

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azwin.dotask.View.QuestView
import com.azwin.dotask.View.TimerView

// Enum untuk merepresentasikan pilihan switch
enum class SwitchOption {
    NOTES, TIMER
}

@Composable
fun navbarSwitch(
    selectedOption: SwitchOption, // Terima state dari luar
    onOptionSelected: (SwitchOption) -> Unit, // Kirim event klik ke luar
    modifier: Modifier = Modifier // Tambahkan modifier sebagai parameter standar
) {
    val containerColor = Color(0xFFCCCCCC)
    val selectedColor = Color.White
    val unselectedColor = Color.Transparent

    Row(
        modifier = modifier // Gunakan modifier dari parameter
            .height(50.dp)
            .width(300.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(containerColor)
            .padding(5.dp),
    ) {
        // Item Quest
        val notesBackgroundColor = if (selectedOption == SwitchOption.NOTES) selectedColor else unselectedColor
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(RoundedCornerShape(25.dp))
                .background(notesBackgroundColor)
                .clickable { onOptionSelected(SwitchOption.NOTES) },
            contentAlignment = Alignment.Center
        ) {
            Text("Quest", fontSize = 18.sp)
        }

        // Item Timer
        val timerBackgroundColor = if (selectedOption == SwitchOption.TIMER) selectedColor else unselectedColor
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(RoundedCornerShape(25.dp))
                .background(timerBackgroundColor)
                .clickable { onOptionSelected(SwitchOption.TIMER) },
            contentAlignment = Alignment.Center
        ) {
            Text("Timer", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainApp() {
    // 1. Buat PagerState untuk mengelola state pager (halaman saat ini, scroll, dll)
    val pagerState = rememberPagerState { SwitchOption.values().size }
    val coroutineScope = rememberCoroutineScope()

    // Ambil opsi yang terpilih berdasarkan halaman pager saat ini
    val selectedOption = SwitchOption.values()[pagerState.currentPage]

    Column(modifier = Modifier.fillMaxSize()) {
        // 2. HorizontalPager sebagai container yang bisa digeser
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Pager mengisi sisa ruang
        ) { page ->
            // Tampilkan halaman yang sesuai berdasarkan indeks
            when (SwitchOption.values()[page]) {
                SwitchOption.NOTES -> QuestView()
                SwitchOption.TIMER -> TimerView()
            }
        }

        // 3. Tempatkan navbarSwitch di bagian bawah

    }
}

