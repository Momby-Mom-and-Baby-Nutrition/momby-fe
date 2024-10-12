package com.example.momby.presentation.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.CustomButton
import com.example.momby.component.CustomOutlinedButton
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.DisablePink
import com.example.momby.ui.theme.LightPink
import com.example.momby.ui.theme.poppinsFontFamily

@Composable
fun OnBoardingScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier
                .padding(
                    top = 48.dp,
                    start = 48.dp,
                    end = 48.dp,
                    bottom = 48.dp
                )
                .align(Alignment.CenterHorizontally),
            model = R.drawable.vector_onboarding,
            contentDescription = "Vector Ibu Hamil",
            alignment = Alignment.Center
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 56.dp, topEnd = 56.dp))
                .background(DisablePink.copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 42.dp, start = 42.dp, end = 42.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.width(150.dp),
                    model = R.drawable.logo,
                    contentDescription = "Logo Momby"
                )
                Text(
                    text = "Memenuhi Nutrisi Ibu Hamil Untuk Generasi Masa Depan",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 23.sp,
                    color = DarkPink,
                    textAlign = TextAlign.Justify
                )
                Text(
                    modifier = Modifier.alpha(0.4f),
                    text = "Aplikasi kami menyediakan rekomendasi makanan bagi ibu hamil, memastikan gizi ibu hamil tercukupi dan mencegah stunting pada anak-anaknya.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkPink,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(24.dp))


                //BUTTON REGISTER
                CustomButton(modifier = Modifier.height(60.dp), text = "Register") {
                    navController.navigate("register")
                }


                //BUTTON LOGIN
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedButton(modifier = Modifier, text = "Login") {
                    navController.navigate("login")
                }
            }
        }

    }
}

