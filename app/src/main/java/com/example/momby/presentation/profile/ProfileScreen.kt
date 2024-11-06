package com.example.momby.presentation.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.CustomButton
import com.example.momby.ui.theme.DarkPink
import com.yalantis.ucrop.UCrop
import java.io.File


@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user = viewModel.user.collectAsState()
    val email = viewModel.email.collectAsState()
    val context = LocalContext.current

    val cropLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let { viewModel.uploadImage(it) }
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let {
                    val imageUri = viewModel.saveBitmapToFile(context, it)
                    imageUri?.let { uri -> startCrop(context, uri, cropLauncher = cropLauncher) }
                }
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri: Uri? = data?.data
                uri?.let { startCrop(context, it, cropLauncher = cropLauncher) }
            }
        }


    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intent)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))
        Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                model = if (user.value?.profilePictureUrl != "") user.value?.profilePictureUrl else R.drawable.ic_profile_color,
                contentDescription = "Photo Profile"
            )
            ElevatedCard(modifier = Modifier
                .padding(bottom = 6.dp)
                .size(42.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .clickable {
                    val options = arrayOf("Ambil Foto", "Pilih dari Galeri")
                    AlertDialog
                        .Builder(context)
                        .setTitle("Pilih Sumber Gambar")
                        .setItems(options) { _, which ->
                            when (which) {
                                0 -> {
                                    when {
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.CAMERA
                                        ) == PackageManager.PERMISSION_GRANTED -> {

                                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                            cameraLauncher.launch(intent)
                                        }

                                        else -> {
                                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                        }
                                    }
                                }

                                1 -> {
                                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                                        type = "image/*"
                                    }
                                    galleryLauncher.launch(intent)
                                }
                            }
                        }
                        .show()
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
            Column(
                modifier = Modifier
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
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow Right"
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(12.dp)
                        .clickable {
                            val whatsappUrl = "https://wa.me/6281237867350"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
                            context.startActivity(intent)
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
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow Right",
                    )

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

private fun startCrop(
    context: Context,
    sourceUri: Uri,
    cropLauncher: ActivityResultLauncher<Intent>
) {
    val destinationUri = Uri.fromFile(File(context.cacheDir, "cropped_image.jpg"))
    UCrop.of(sourceUri, destinationUri)
        .withAspectRatio(1f, 1f)
        .withMaxResultSize(2500, 2500)
        .getIntent(context)
        .let {
            cropLauncher.launch(it)
        }
}



