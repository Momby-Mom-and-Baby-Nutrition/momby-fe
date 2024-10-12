package com.example.momby.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.CustomButton
import com.example.momby.ui.theme.DarkPink

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user = viewModel.user.collectAsState()
    val email = viewModel.email.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))
        Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier.size(150.dp),
                model = R.drawable.ic_profile_color,
                contentDescription = "Photo Profile"
            )
            ElevatedCard(modifier = Modifier
                .padding(bottom = 6.dp)
                .size(42.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .clickable {
                    //ganti fotonya
                }
            ) {
                Box(modifier = Modifier.size(42.dp), contentAlignment = Alignment.Center) {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = R.drawable.ic_change_photo,
                        contentDescription = "Photo Icon"
                    )
                }
            }
        }

        //Nama
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user.value?.name.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = DarkPink,
            textAlign = TextAlign.Center
        )

        //Email
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = email.value,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkPink,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Card
        ElevatedCard(
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                ) {

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable {
                            navController.navigate("profile_edit")
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(12.dp),
                        model = R.drawable.ic_edit_profile,
                        contentDescription = "Profile Edit"
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Edit Profil",
                            style = MaterialTheme.typography.bodyLarge,
                            color = DarkPink,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Nama, berat badan hingga aktivitas fisik",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkPink
                        )
                    }
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(12.dp)
                        .clickable {
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        model = R.drawable.ic_contact_help,
                        contentDescription = "Profile Edit"
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Bantuan",
                            style = MaterialTheme.typography.bodyLarge,
                            color = DarkPink,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Anda akan dialihkan pada kontak layanan",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkPink
                        )
                    }
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right", )

                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        CustomButton(modifier = Modifier, text = "Keluar", onClick = {
            viewModel.logout()
            navController.navigate("on_boarding")
        })
    }
}