package com.example.raceme

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DemoApp() }
    }
}

@Composable
fun DemoApp() {
    var screen by remember { mutableStateOf("login") }
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            when (screen) {
                "login" -> LoginScreen { screen = "home" }
                "home" -> HomeScreen(
                    goQuotes = { screen = "quotes" },
                    goBadges = { screen = "badges" },
                    goProfile = { screen = "profile" }
                )
                "quotes" -> QuotesScreen(onBack = { screen = "home" })
                "badges" -> BadgesScreen(onBack = { screen = "home" })
                "profile" -> ProfileScreen(onBack = { screen = "home" })
            }
        }
    }
}

// ----------- Screens (no navigation library) -----------

@Composable
fun TopBar(title: String, onBack: (() -> Unit)? = null) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            Text("‹ Back", modifier = Modifier
                .clickable { onBack() }
                .padding(end = 8.dp),
                color = Color(0xFF6A5AE0), fontSize = 16.sp)
        }
        Text(title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CardButton(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = Color.Gray)
        }
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("RaceMe", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text("Demo Mode", color = Color.Gray)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") }, singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) { Text("Log In") }
    }
}

@Composable
fun HomeScreen(goQuotes: () -> Unit, goBadges: () -> Unit, goProfile: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TopBar("Home")
        Text("Welcome back! Choose a section:", color = Color.Gray)
        CardButton("✨ Quotes", "Daily inspiration", onClick = goQuotes)
        CardButton("🏅 Badges", "Your achievements", onClick = goBadges)
        CardButton("👤 Profile", "Edit username, bio & photo", onClick = goProfile)
    }
}

@Composable
fun QuotesScreen(onBack: () -> Unit) {
    val quotes = listOf(
        "Small steps every day lead to big results.",
        "Progress > Perfection.",
        "You don’t have to go fast — you just have to go.",
        "Discipline is choosing what you want most over what you want now.",
        "Your only competition is yesterday’s you."
    )
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        TopBar("Quotes", onBack)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            items(quotes) { q ->
                Card(shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .background(Color(0xFFF8F6FF))
                            .padding(18.dp)
                    ) {
                        Text("“$q”", fontSize = 18.sp, lineHeight = 24.sp, textAlign = TextAlign.Left)
                        Spacer(Modifier.height(8.dp))
                        Text("— RaceMe", color = Color(0xFF6A5AE0), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

data class Badge(val name: String, val swatch: Color)

@Composable
fun BadgesScreen(onBack: () -> Unit) {
    val badges = listOf(
        Badge("First 1,000 Steps", Color(0xFFFFE082)),
        Badge("7-Day Streak", Color(0xFFB2EBF2)),
        Badge("Early Riser", Color(0xFFC8E6C9)),
        Badge("Weekend Warrior", Color(0xFFFFCDD2)),
        Badge("Marathon Mindset", Color(0xFFD1C4E9)),
    )
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        TopBar("Badges", onBack)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            items(badges) { b ->
                Card(shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(b.swatch),
                            contentAlignment = Alignment.Center
                        ) { Text("🏅", fontSize = 26.sp) }
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(b.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                            Text("Looks great on your profile!", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    var username by remember { mutableStateOf("Danielle") }
    var bio by remember { mutableStateOf("Chasing steps, one day at a time 💫") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        photoUri = uri
    }

    // Decode the selected image to a Bitmap (no extra libraries)
    LaunchedEffect(photoUri) {
        if (photoUri != null) {
            val ctx = androidx.compose.ui.platform.LocalContext.current
            bitmap = if (Build.VERSION.SDK_INT >= 28) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(ctx.contentResolver, photoUri!!))
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(ctx.contentResolver, photoUri)
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TopBar("Edit Profile", onBack)

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Profile photo",
                    modifier = Modifier.size(80.dp).clip(CircleShape)
                )
            } else {
                Box(
                    Modifier.size(80.dp).clip(CircleShape).background(Color(0xFFEDE7F6)),
                    contentAlignment = Alignment.Center
                ) { Text("👤", fontSize = 34.sp) }
            }
            Spacer(Modifier.width(12.dp))
            OutlinedButton(onClick = { launcher.launch("image/*") }, shape = RoundedCornerShape(14.dp)) {
                Text("Upload Picture")
            }
        }

        OutlinedTextField(
            value = username, onValueChange = { username = it },
            label = { Text("Username") }, singleLine = true, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = bio, onValueChange = { bio = it },
            label = { Text("Bio") }, modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { /* state is already saved locally for demo */ },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Save") }

        Card(shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Preview", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (bitmap != null) {
                        Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "Preview",
                            modifier = Modifier.size(56.dp).clip(CircleShape))
                    } else {
                        Box(
                            Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFEDE7F6)),
                            contentAlignment = Alignment.Center
                        ) { Text("👤", fontSize = 24.sp) }
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(username, fontWeight = FontWeight.Medium)
                        Text(bio, color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
