package com.example.nammanala


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.animation.core.tween

import kotlinx.coroutines.delay

import androidx.navigation.compose.*
import androidx.navigation.NavController

// ✅ ICONS
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip

import com.google.android.gms.location.LocationServices
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.animation.core.animateFloatAsState


import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person

import androidx.compose.ui.draw.shadow

import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

import android.widget.Toast

import androidx.compose.foundation.clickable
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.foundation.BorderStroke


import android.content.Context

import com.google.firebase.firestore.DocumentSnapshot

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet

import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.model.GradientColor
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.animation.core.*
import androidx.compose.foundation.indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.border

import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import com.example.nammanala.ui.theme.NammaNalaTheme

// 🌿 Theme Colors
val ForestGreen = Color(0xFF2E7D32)
val ForestGreenGlow = Color(0xFF4CAF50)

val WaterBlue = Color(0xFF42A5F5)
val WaterBlueGlow = Color(0xFF00C6FF)

val BackgroundColor = Color(0xFF0B1412)   // deeper


val TextPrimary = Color(0xFFFFFFFF)      // white
val TextSecondary = Color(0xFFE0E0E0)    // light grey

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            FirebaseApp.initializeApp(this)

        setContent {
            NammaNalaTheme {
                NammaNalaApp()
            }
        }
    }
}

// 🔹 Navigation Routes
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Dashboard : Screen("dashboard")
    object Report : Screen("report")
    object Water : Screen("water")
    object Maintenance : Screen("maintenance")
    object Schedule : Screen("schedule")
    object Profile : Screen("profile")
    object Map : Screen("map")
}

@Composable
fun NammaNalaApp() {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Dashboard.route,
        Screen.Water.route,
        Screen.Report.route,
        Screen.Maintenance.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }

            composable(Screen.Welcome.route) {
                WelcomeScreen(navController)
            }

            composable(Screen.Dashboard.route) {
                DashboardScreen(navController)
            }

            composable("alerts") {
                AlertsScreen(navController)
            }

            composable(Screen.Report.route) {
                ReportIssueScreen(navController)
            }

            composable(Screen.Water.route) {
                WaterStatusScreen(navController)
            }

            composable(Screen.Maintenance.route) {
                MaintenanceScreen(navController)
            }

            composable(Screen.Schedule.route) {
                ScheduleMaintenanceScreen(navController)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }

            composable("login") {
                LoginScreen(navController)
            }

            composable("signup") {
                SignupScreen(navController)
            }

            composable("reports") {
                ReportsScreen(navController)
            }
            composable(Screen.Map.route) {
                CanalMapScreen(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF121A14),
        tonalElevation = 10.dp,
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {

        // 🏠 Home
        NavigationBarItem(
            selected = currentRoute == Screen.Dashboard.route,
            onClick = { navController.navigate(Screen.Dashboard.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        // 💧 Water
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Water.route)
            },
            icon = { Icon(Icons.Default.WaterDrop, contentDescription = "Water") },
            label = { Text("Water") }
        )

        // ➕ Report (highlighted)
        NavigationBarItem(
            selected = currentRoute == Screen.Report.route,
            onClick = { navController.navigate(Screen.Report.route) },
            icon = {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = "Report",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(32.dp)
                )
            },
            label = { Text("Report") }
        )

        // 🛠 Maintain
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Maintenance.route)
            },
            icon = { Icon(Icons.Default.Build, contentDescription = "Maintain") },
            label = { Text("Maintain") }
        )

        // 👤 Profile
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Profile.route)
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}

@Composable
fun SplashScreen(navController: NavController) {

    val infinite = rememberInfiniteTransition(label = "")

    // 🌊 wave movement
    val waveShift by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // 🔥 breathing logo
    val scale by infinite.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val alpha by infinite.animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // ⏳ navigation
    LaunchedEffect(true) {
        delay(2200)
        navController.navigate(Screen.Welcome.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌑 base background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF020A07),
                            Color(0xFF06140F),
                            Color(0xFF000000)
                        )
                    )
                )
        )

        // 🌊 WATER WAVE CANVAS
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val width = size.width
            val height = size.height

            val path1 = Path()
            val path2 = Path()

            val waveHeight = 40f
            val waveLength = width / 1.5f

            // wave 1
            path1.moveTo(0f, height * 0.7f)

            for (x in 0..width.toInt() step 10) {
                val y = (height * 0.7f +
                        waveHeight * kotlin.math.sin(
                    (2 * Math.PI * x / waveLength + waveShift)
                )).toFloat()

                path1.lineTo(x.toFloat(), y)
            }

            path1.lineTo(width, height)
            path1.lineTo(0f, height)
            path1.close()

            // wave 2 (slower, subtle)
            path2.moveTo(0f, height * 0.75f)

            for (x in 0..width.toInt() step 10) {
                val y = (height * 0.75f +
                        (waveHeight / 1.5f) * kotlin.math.sin(
                    (2 * Math.PI * x / waveLength + waveShift / 2)
                )).toFloat()

                path2.lineTo(x.toFloat(), y)
            }

            path2.lineTo(width, height)
            path2.lineTo(0f, height)
            path2.close()

            drawPath(
                path1,
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF00E676).copy(alpha = 0.25f),
                        Color.Transparent
                    )
                )
            )

            drawPath(
                path2,
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF00C6FF).copy(alpha = 0.18f),
                        Color.Transparent
                    )
                )
            )
        }

        // ✨ subtle glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF00E676).copy(alpha = 0.12f),
                            Color.Transparent
                        ),
                        radius = 800f
                    )
                )
        )

        // 💧 CENTER LOGO
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Namma-Nala",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF00E676),
                            Color(0xFF00C6FF)
                        )
                    )
                )
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Smart Irrigation System",
                color = Color(0xFFB0BEC5),
                fontSize = 14.sp
            )
        }
    }
}

// 🔥 Welcome Screen (Premium Dark)
@Composable
fun WelcomeScreen(navController: NavController) {

    val alphaAnim by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200),
        label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {

        AnimatedBackground() // ✅ NEW

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .alpha(alphaAnim),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Spacer(modifier = Modifier.height(80.dp))

                Text("Welcome to", color = Color.Gray, fontSize = 16.sp)

                Text(
                    "Namma-Nala",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF00E676),
                                Color(0xFF00C6FF)
                            )
                        )
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Smart canal monitoring system\nfor efficient irrigation 🌾",
                    color = Color(0xFFB0BEC5),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FeatureChip("Live Tracking", Modifier.weight(1f))
                        FeatureChip("AI Insights", Modifier.weight(1f))
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FeatureChip("Smart Alerts", Modifier.weight(1f))
                        FeatureChip("Maintenance", Modifier.weight(1f))
                    }
                }
            }

            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp)
                            .shadow(12.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E676)
                        )
                    ) {
                        Text("Login", fontSize = 16.sp, color = Color.Black)
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("signup") },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(
                            1.dp,
                            Color.White.copy(alpha = 0.2f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text("Sign Up", fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun AnimatedBackground() {

    val infinite = rememberInfiniteTransition(label = "")

    val shift by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF05120E),
                        Color(0xFF081C15),
                        Color(0xFF020A07)
                    ),
                    start = Offset(shift, 0f),
                    end = Offset(shift + 800f, 1500f)
                )
            )
    )

    // glow layer (same as your existing one, just reused cleanly)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00E676).copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    radius = 800f
                )
            )
    )
}

@Composable
fun FeatureChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()

    // scale animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(120),
        label = ""
    )

    // glow animation
    val glow by animateFloatAsState(
        targetValue = if (isPressed) 1f else 0.4f,
        animationSpec = tween(200),
        label = ""
    )

    val glowColor = Color(0xFF00E676)

    Box(
        modifier = modifier
            .height(44.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)

            // ✨ OUTER GLOW
            .shadow(
                elevation = 14.dp,
                shape = RoundedCornerShape(50),
                ambientColor = glowColor.copy(alpha = 0.3f * glow),
                spotColor = glowColor.copy(alpha = 0.3f * glow)
            )

            // ✨ BORDER GLOW (main effect)
            .border(
                width = 1.5.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        glowColor.copy(alpha = 0.8f * glow),
                        Color.White.copy(alpha = 0.2f),
                        glowColor.copy(alpha = 0.8f * glow)
                    )
                ),
                shape = RoundedCornerShape(50)
            )

            // base background
            .background(
                Color.White.copy(alpha = 0.06f),
                RoundedCornerShape(50)
            )

            // ripple (fixed version)
            .indication(
                interactionSource = interaction,
                indication = LocalIndication.current
            )
            .clickable(
                interactionSource = interaction,
                indication = null
            ) {
                onClick()
            },

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFFB0BEC5),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}



@Composable
fun LoginScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 🔥 animation
    val alpha by animateFloatAsState(1f, tween(800), label = "")
    val scale by animateFloatAsState(1f, tween(800), label = "")

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌊 animated background (reuse your function)
        AnimatedBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha),
            verticalArrangement = Arrangement.Center
        ) {

            // 🔥 TITLE
            Text(
                text = "Welcome Back 👋",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Login to continue your irrigation insights",
                color = Color(0xFFB0BEC5),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(28.dp))

            // 💎 GLASS CARD
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White.copy(0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(20.dp, RoundedCornerShape(24.dp))
            ) {

                Column(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(0.08f),
                                    Color.White.copy(0.02f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {

                    // 📧 EMAIL FIELD
                    PremiumTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email"
                    )

                    Spacer(Modifier.height(14.dp))

                    // 🔒 PASSWORD FIELD
                    PremiumTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        isPassword = true
                    )

                    Spacer(Modifier.height(20.dp))

                    // 🔥 LOGIN BUTTON
                    Button(
                        onClick = {

                            if (email.isEmpty() || password.isEmpty()) {
                                toast(context, "Fill all fields")
                                return@Button
                            }

                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->

                                    if (task.isSuccessful) {
                                        toast(context, "Login Successful ✅")

                                        navController.navigate("dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        toast(context, "Invalid credentials ❌")
                                    }
                                }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .shadow(12.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E676)
                        )
                    ) {
                        Text("Login", color = Color.Black, fontSize = 16.sp)
                    }

                    Spacer(Modifier.height(10.dp))

                    TextButton(
                        onClick = { navController.navigate("signup") },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Don't have an account? Sign Up",
                            color = Color(0xFFB0BEC5)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {

    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsPressedAsState()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        interactionSource = interaction,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else VisualTransformation.None,

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF00E676),
            unfocusedBorderColor = Color.White.copy(0.2f),
            cursorColor = Color(0xFF00E676),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Color(0xFF00E676),
            unfocusedLabelColor = Color.Gray
        ),

        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (focused) 12.dp else 4.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color(0xFF00E676).copy(0.2f),
                spotColor = Color(0xFF00E676).copy(0.2f)
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Select Gender") }
    var age by remember { mutableStateOf("") }
    var land by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    val genders = listOf("Male", "Female", "Other")

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌊 background
        AnimatedBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(Modifier.height(30.dp))

            // 🔥 HEADER
            Text(
                "Create Account 🚀",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                "Register to start smart irrigation",
                color = Color(0xFFB0BEC5)
            )

            Spacer(Modifier.height(20.dp))

            // 💎 PROFILE SECTION
            Box(contentAlignment = Alignment.Center) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(0.08f))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text("Upload Profile", color = Color.Gray)
                }
            }

            Spacer(Modifier.height(24.dp))

            // 💎 GLASS FORM CARD
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White.copy(0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(20.dp, RoundedCornerShape(24.dp))
            ) {

                Column(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(0.08f),
                                    Color.White.copy(0.02f)
                                )
                            )
                        )
                        .padding(18.dp)
                ) {

                    // 🔹 PERSONAL
                    SectionTitle("Personal Info")

                    PremiumTextField(name, { name = it }, "Full Name")

                    Spacer(Modifier.height(12.dp))

                    // Gender dropdown
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Gender") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00E676),
                                unfocusedBorderColor = Color.White.copy(0.2f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color(0xFF00E676)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)  // 🔥 THIS IS THE FIX
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            genders.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        gender = it
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    PremiumTextField(age, { age = it }, "Age")

                    Spacer(Modifier.height(18.dp))

                    // 🔹 FARM
                    SectionTitle("Farm Details")

                    PremiumTextField(land, { land = it }, "Land (acres)")

                    Spacer(Modifier.height(12.dp))

                    PremiumTextField(location, { location = it }, "State")

                    Spacer(Modifier.height(12.dp))

                    PremiumTextField(address, { address = it }, "Address")

                    Spacer(Modifier.height(18.dp))

                    // 🔹 ACCOUNT
                    SectionTitle("Account")

                    PremiumTextField(phone, { phone = it }, "Phone")

                    Spacer(Modifier.height(12.dp))

                    PremiumTextField(email, { email = it }, "Email")

                    Spacer(Modifier.height(12.dp))

                    PremiumTextField(password, { password = it }, "Password", true)

                    Spacer(Modifier.height(22.dp))

                    // 🚀 BUTTON
                    Button(
                        onClick = {

                            when {
                                name.isEmpty() -> toast(context, "Enter name")
                                gender == "Select Gender" -> toast(context, "Select gender")
                                age.isEmpty() -> toast(context, "Enter age")
                                land.isEmpty() -> toast(context, "Enter land")
                                location.isEmpty() -> toast(context, "Enter location")
                                address.isEmpty() -> toast(context, "Enter address")
                                phone.length != 10 -> toast(context, "Invalid phone")
                                password.length < 6 -> toast(context, "Weak password")

                                else -> {

                                    auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->

                                            if (task.isSuccessful) {

                                                val userId = auth.currentUser!!.uid

                                                val userData = hashMapOf(
                                                    "name" to name,
                                                    "gender" to gender,
                                                    "age" to age,
                                                    "land" to land,
                                                    "location" to location,
                                                    "address" to address,
                                                    "phone" to phone,
                                                    "email" to email
                                                )

                                                db.collection("users")
                                                    .document(userId)
                                                    .set(userData)

                                                toast(context, "Registered ✅")

                                                navController.navigate("login") {
                                                    popUpTo("signup") { inclusive = true }
                                                }
                                            } else {
                                                toast(context, "Error")
                                            }
                                        }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .shadow(12.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E676)
                        )
                    ) {
                        Text("Create Account", color = Color.Black)
                    }

                    Spacer(Modifier.height(10.dp))

                    TextButton(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Already have an account? Login", color = Color.Gray)
                    }
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        color = Color(0xFF00E676),
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
}

fun toast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

// 🔥 Dashboard (Premium Layout)
val DarkBg = Color(0xFF081C15)
val PremiumCard = Brush.verticalGradient(
    listOf(
        Color.White.copy(alpha = 0.06f),
        Color.White.copy(alpha = 0.02f)
    )
)
val BorderGlass = Color.White.copy(alpha = 0.08f)


@Composable
fun DashboardScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val uid = auth.currentUser?.uid

    var name by remember { mutableStateOf("User") }
    var location by remember { mutableStateOf("--") }
    var land by remember { mutableStateOf("--") }

    var water by remember { mutableIntStateOf(0) }
    var flow by remember { mutableStateOf("Smooth") }
    var time by remember { mutableStateOf("--") }

    // 🔥 USER
    LaunchedEffect(Unit) {
        uid?.let {
            db.collection("users").document(it).get()
                .addOnSuccessListener { doc ->
                    name = doc.getString("name") ?: "User"
                    location = doc.getString("location") ?: "--"
                    land = doc.getString("land") ?: "--"
                }
        }
    }

    // 🔥 WATER REALTIME
    LaunchedEffect(Unit) {
        uid?.let {
            db.collection("water_status")
                .whereEqualTo("userId", it)
                .addSnapshotListener { snap, _ ->
                    val latest = snap?.documents?.maxByOrNull {
                        it.getLong("timestamp") ?: 0L
                    }

                    latest?.let {
                        water = (it.get("percentage") as? Number)?.toInt() ?: 0
                        flow = it.getString("flow") ?: "Smooth"

                        val t = it.getLong("timestamp") ?: 0L
                        time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            .format(Date(t))
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF06140F),
                        Color(0xFF0B1F1A),
                        Color(0xFF04110D)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 100.dp) // 🔥 FIX CUT ISSUE
    ) {

        Header(name, location, navController)

        Spacer(Modifier.height(16.dp))

        HeroCardExact(water, time, flow)

        Spacer(Modifier.height(16.dp))

        StatsExact(land, flow, time, water)

        Spacer(Modifier.height(16.dp))

        AlertsExact(water, flow)

        Spacer(Modifier.height(16.dp))

        AIInsightsExact(water)

        Spacer(Modifier.height(16.dp))

        QuickActionsExact(navController)

        Spacer(Modifier.height(16.dp))

        AboutExact()

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun Header(name: String, location: String, navController: NavController) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val greeting = when {
                hour < 12 -> "Good Morning"
                hour < 18 -> "Good Afternoon"
                else -> "Good Evening"
            }
            Text(greeting, color = Color.Gray)
            Text(name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(location, color = Color.LightGray, fontSize = 12.sp)
        }

        // 🔔 CLICKABLE BELL
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f))
                .clickable {
                    navController.navigate("alerts") // 🔥 navigation
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AlertsScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid

    var alerts by remember { mutableStateOf(listOf<AlertData>()) }

    LaunchedEffect(Unit) {

        userId?.let { uid ->

            db.collection("water_status")
                .whereEqualTo("userId", uid)
                .addSnapshotListener { snapshot, _ ->

                    val newAlerts = mutableListOf<AlertData>()

                    snapshot?.documents?.forEach { doc ->

                        val percent = (doc.get("percentage") as? Number)?.toInt() ?: 0
                        val flow = doc.getString("flow") ?: "Unknown"
                        val timestamp = doc.getLong("timestamp") ?: 0L

                        val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            .format(Date(timestamp))

                        // 🔥 ALERT LOGIC
                        if (percent <= 15) {
                            newAlerts.add(
                                AlertData(
                                    "Water level critically low ($percent%)",
                                    time,
                                    Color.Red
                                )
                            )
                        }

                        if (flow == "Blocked") {
                            newAlerts.add(
                                AlertData(
                                    "Canal flow blocked",
                                    time,
                                    Color.Yellow
                                )
                            )
                        }

                        if (newAlerts.isEmpty()) {
                            newAlerts.add(
                                AlertData(
                                    "System running normally",
                                    time,
                                    Color(0xFF00E676)
                                )
                            )
                        }
                    }

                    alerts = newAlerts.reversed() // latest first
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF081C15))
            .padding(16.dp)
    ) {

        // 🔙 HEADER
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Notifications",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        // 🔔 ALERT LIST
        if (alerts.isEmpty()) {

            Text(
                "No alerts available",
                color = Color.Gray
            )

        } else {

            LazyColumn {
                items(alerts) { alert ->
                    AlertItem(alert)
                }
            }
        }
    }
}

data class AlertData(
    val text: String,
    val time: String,
    val color: Color
)

@Composable
fun AlertItem(alert: AlertData) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(alert.color, CircleShape)
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(alert.text, color = Color.White)
                Text(alert.time, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}


@Composable
fun AIInsightsExact(percent: Int) {

    val days = percent / 12

    val recommendation = when {
        percent > 70 -> "Optimal usage"
        percent > 40 -> "Use carefully"
        else -> "Save water"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        InsightCard(
            title = "Water lasts",
            value = "$days Days",
            modifier = Modifier.weight(1f)
        )

        InsightCard(
            title = "Recommendation",
            value = recommendation,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun InsightCard(title: String, value: String, modifier: Modifier) {

    Card(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(20.dp)), // ✨ depth
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.White.copy(0.08f))
    ) {
        Box(
            modifier = Modifier
                .background(PremiumCard)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            )  {

                Text(
                    text = title,
                    color = Color(0xFF90A4AE),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = value,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}


@Composable
fun AlertRow(text: String, tag: String, color: Color) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )

        Spacer(Modifier.width(8.dp))

        Text(text, color = Color.White)
    }

        Box(
            modifier = Modifier
                .background(color.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(tag, color = color, fontSize = 12.sp)
        }
    }


@Composable
fun HeroCardExact(percent: Int, time: String, flow: String) {

    // ✅ STATUS LOGIC (single source of truth)
    val status = when {
        percent <= 15 || flow == "Blocked" -> "Critical"
        percent <= 40 -> "Warning"
        else -> "Healthy"
    }

    val statusColor = when (status) {
        "Critical" -> Color(0xFFFF5252)
        "Warning" -> Color(0xFFFFC107)
        else -> WaterBlue
    }

    val message = when (status) {
        "Critical" -> "Immediate attention required"
        "Warning" -> "Monitor system closely"
        else -> "Everything looks good"
    }

    val animatedProgress by animateFloatAsState(
        targetValue = percent / 100f,
        label = ""
    )

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF0F2027),
                            Color(0xFF203A43),
                            Color(0xFF2C5364)
                        )
                    )
                )
                .padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // 💧 CIRCLE
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(140.dp)
                ) {

                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        color = statusColor,
                        strokeWidth = 12.dp,
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(20.dp, CircleShape),
                        trackColor = Color.White.copy(0.1f),
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "$percent%",
                            color = statusColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Water",
                            color = Color(0xFFB0BEC5),
                            fontSize = 12.sp
                        )
                    }
                }

                // 📊 RIGHT SIDE
                Column {

                    Text(
                        status, // ✅ dynamic
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Water Level is $status", // ✅ dynamic
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        message,
                        color = Color(0xFFB0BEC5)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Last updated: $time",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
@Composable
fun StatsExact(
    land: String,
    flow: String,
    time: String,
    waterPercent: Int // 🔥 ADD THIS
) {

    val usage = 100 - waterPercent // 🔥 LOGIC FIX

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Stat("Land", "$land Acres", Modifier.weight(1f))
            Stat("Flow", flow, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Stat("Last Sync", time, Modifier.weight(1f))
            Stat("Usage", "$usage%", Modifier.weight(1f)) // ✅ FIXED
        }
    }
}

@Composable
fun Stat(title: String, value: String, modifier: Modifier) {

    Card(
        modifier = modifier
            .height(110.dp)
            .shadow(12.dp, RoundedCornerShape(20.dp)), // ✨ glow depth
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.White.copy(0.08f))
    ) {

        Box(
            modifier = Modifier
                .background(PremiumCard) // 🔥 glass effect
                .padding(14.dp)
        ) {
            Column(Modifier.padding(14.dp)) {
                Text(
                    title,
                    color = Color(0xFF90A4AE),
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    value,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun AlertsExact(percent: Int, flow: String) {

    Card(
        modifier = Modifier
            .shadow(12.dp, RoundedCornerShape(20.dp)), // ✨ depth
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.White.copy(0.08f))
    ) {
        Box(
            modifier = Modifier
                .background(PremiumCard)
                .padding(16.dp)
        ) {

            Column(Modifier.padding(16.dp)) {

                Text("Smart Alerts", color = Color.White)

                Spacer(Modifier.height(10.dp))

                if (flow == "Blocked") {
                    AlertRow("Canal blockage detected", "Critical", Color.Red)
                } else if (percent <= 15) {
                    AlertRow("Water critically low ($percent%)", "Critical", Color.Red)
                } else if (percent <= 40) {
                    AlertRow("Water level dropping", "Warning", Color.Yellow)
                } else {
                    Text("No issues detected", color = Color(0xFF00E676))
                }
            }
        }
    }
}

@Composable
fun QuickActionsExact(navController: NavController) {

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Action("Report", Color(0xFF00C853), Modifier.weight(1f)) {
                navController.navigate("report")
            }
            Action("Water", Color(0xFF2979FF), Modifier.weight(1f)) {
                navController.navigate("water")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Action("Maintain", Color(0xFFFF9100), Modifier.weight(1f)) {
                navController.navigate("maintenance")
            }
            Action("Map", Color(0xFF00BCD4), Modifier.weight(1f)) {
                navController.navigate("map")
            }
        }
    }
}

@Composable
fun Action(text: String, color: Color, modifier: Modifier, onClick: () -> Unit) {

    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val scale by animateFloatAsState(
        if (pressed) 0.95f else 1f, label = ""
    )

    Card(
        modifier = modifier
            .height(110.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable(
                interactionSource = interaction,
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.18f)
        ),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            val icon = when (text) {
                "Report" -> Icons.Default.Report
                "Water" -> Icons.Default.WaterDrop
                "Maintain" -> Icons.Default.Build
                "Map" -> Icons.Default.Map
                else -> Icons.Default.Info
            }

            Icon(
                icon,
                contentDescription = text,
                tint = color,
                modifier = Modifier
                    .size(28.dp)
                    .shadow(8.dp, CircleShape)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CanalMapScreen(navController: NavController) {

    val context = LocalContext.current

    // 🔥 Example canal points
    val canalPoints = listOf(
        com.google.android.gms.maps.model.LatLng(12.4210, 76.5720),
        com.google.android.gms.maps.model.LatLng(12.4150, 76.5800),
        com.google.android.gms.maps.model.LatLng(12.4100, 76.5900)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF081C15))
    ) {

        // 🔙 HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Canal Map",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 🗺 MAP VIEW
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {

                val mapView = com.google.android.gms.maps.MapView(context)
                mapView.onCreate(Bundle())
                mapView.onResume()

                mapView.getMapAsync { googleMap ->

                    // 📍 move camera
                    googleMap.moveCamera(
                        com.google.android.gms.maps.CameraUpdateFactory
                            .newLatLngZoom(canalPoints.first(), 14f)
                    )

                    // 🔵 draw canal line
                    googleMap.addPolyline(
                        com.google.android.gms.maps.model.PolylineOptions()
                            .addAll(canalPoints)
                            .color(android.graphics.Color.CYAN)
                            .width(10f)
                    )

                    // 📍 markers (optional but nice)
                    canalPoints.forEachIndexed { index, point ->
                        googleMap.addMarker(
                            com.google.android.gms.maps.model.MarkerOptions()
                                .position(point)
                                .title("Point ${index + 1}")
                        )
                    }
                }

                mapView
            }
        )
    }
}

@Composable
fun AboutExact() {

    Card(
        modifier = Modifier
            .shadow(12.dp, RoundedCornerShape(20.dp)), // ✨ depth
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.White.copy(0.08f))
    ) {
        Box(
            modifier = Modifier
                .background(PremiumCard)
                .padding(16.dp)
        ) {

            Column(Modifier.padding(16.dp)) {

                Text("About Namma-Nala", color = Color.White, fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(8.dp))

                Text(
                    "Namma-Nala is a smart irrigation monitoring system helping farmers track water levels in real-time, detect issues, and optimize water usage using intelligent insights.",
                    color = Color.LightGray
                )

                Spacer(Modifier.height(8.dp))

                Text("✔ Real-time tracking", color = Color.LightGray)
                Text("✔ AI insights", color = Color.LightGray)
                Text("✔ Issue reporting", color = Color.LightGray)
                Text("✔ Maintenance planning", color = Color.LightGray)
            }
        }
    }
}

fun calculateDistanceKm(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Double {

    val results = FloatArray(1)

    android.location.Location.distanceBetween(
        lat1, lon1, lat2, lon2, results
    )

    return results[0].toDouble() / 1000
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportIssueScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid



    var currentLat by remember { mutableStateOf(0.0) }
    var currentLng by remember { mutableStateOf(0.0) }

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    var locationText by remember { mutableStateOf("Fetching location...") }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        currentLat = loc.latitude
                        currentLng = loc.longitude

                        locationText = "Lat: ${loc.latitude}, Lng: ${loc.longitude}"
                    }
                }
            } catch (e: SecurityException) {
                locationText = "Permission error"
            }
        } else locationText = "Location permission required"
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri = it }

    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedIssue by remember { mutableStateOf("Select Issue Type") }

    val issues = listOf(
        "Leakage", "Blockage", "Silt", "Canal Damage",
        "Overflow", "Water Theft", "Gate Issue"
    )

    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(BackgroundColor, Color(0xFF0C1C15))
                    )
                )
                .padding(16.dp)
        ) {

            // 🔥 HEADER
            Column {
                Text(
                    "Report Issue",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Help us maintain canals efficiently",
                    color = Color(0xFFB0BEC5),
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            // 💎 GLASS CARD
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White.copy(0.08f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(25.dp, RoundedCornerShape(28.dp))
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(0.07f),
                                    Color.White.copy(0.02f)
                                )
                            )
                        )
                        .padding(18.dp)
                ) {

                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

                        // 🔽 DROPDOWN
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {

                            OutlinedTextField(
                                value = selectedIssue,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Issue Type") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF4CAF50),
                                    unfocusedBorderColor = Color.White.copy(0.2f),
                                    cursorColor = Color(0xFF4CAF50),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                issues.forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            selectedIssue = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // 📝 DESCRIPTION
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4CAF50),
                                unfocusedBorderColor = Color.White.copy(0.2f),
                                cursorColor = Color(0xFF4CAF50),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        )

                        // 📷 UPLOAD BUTTON
                        Button(
                            onClick = { imageLauncher.launch("image/*") },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .shadow(10.dp, RoundedCornerShape(16.dp))
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Image, null, tint = Color.White)
                                Spacer(Modifier.width(8.dp))
                                Text("Upload Image", color = Color.White)
                            }
                        }

                        // 🖼 IMAGE PREVIEW
                        imageUri?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .shadow(16.dp, RoundedCornerShape(18.dp))
                            )
                        }

                        // 📍 LOCATION CHIP
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(0.06f),
                            border = BorderStroke(1.dp, Color.White.copy(0.1f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.LocationOn, null, tint = Color(0xFF81C784))
                                Spacer(Modifier.width(6.dp))
                                Text(locationText, color = Color(0xFF81C784))
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        // 🚀 SUBMIT BUTTON
                        GradientButton(
                            text = "Submit Report",
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                                if (selectedIssue == "Select Issue Type" || description.isEmpty()) {
                                    toast(context, "Please fill all fields")
                                    return@GradientButton
                                }

                                if (userId == null) {
                                    toast(context, "User not logged in ❌")
                                    return@GradientButton
                                }

                                val time = SimpleDateFormat(
                                    "dd MMM yyyy, hh:mm a",
                                    Locale.getDefault()
                                ).format(Date())




                                val reportData = hashMapOf(
                                    "userId" to userId,
                                    "issue" to selectedIssue,
                                    "description" to description,
                                    "latitude" to currentLat,
                                    "longitude" to currentLng,
                                    "timestamp" to System.currentTimeMillis(),
                                    "imageUri" to (imageUri?.toString() ?: "")
                                )

                                db.collection("reports")
                                    .add(reportData)
                                    .addOnSuccessListener {
                                        toast(context, "Report Submitted ✅")
                                        selectedIssue = "Select Issue Type"
                                        description = ""
                                        imageUri = null
                                    }
                                    .addOnFailureListener {
                                        toast(context, "Failed ❌")
                                    }
                            }
                        )
                    }
                }
            }
        }

        // 🕘 HISTORY BUTTON
        TextButton(
            onClick = { navController.navigate("reports") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.History, null, tint = Color(0xFF81C784))
                Spacer(Modifier.width(4.dp))
                Text("History", color = Color(0xFF81C784))
            }
        }
    }
}

@Composable
fun ReportsScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    var reports by remember { mutableStateOf(listOf<DocumentSnapshot>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userId?.let { uid ->
            db.collection("reports")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener {
                    reports = it.documents.reversed()
                    isLoading = false
                }
                .addOnFailureListener {
                    isLoading = false
                }
        }
    }

    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            BackgroundColor,
                            Color(0xFF0C1C15)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            // 🔥 HEADER
            Column {
                Text(
                    "My Reports",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Track and review reported issues",
                    color = Color(0xFFB0BEC5),
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF4CAF50)
                        )
                    }
                }

                reports.isEmpty() -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Report,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "No reports yet",
                                color = TextSecondary
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        items(reports) { doc ->

                            val issue = doc.getString("issue") ?: ""
                            val desc = doc.getString("description") ?: ""
                            val lat = doc.getDouble("latitude") ?: 0.0
                            val lng = doc.getDouble("longitude") ?: 0.0

                            // Example village reference (you can later fetch from Firebase too)
                            val villageLat = 12.9716
                            val villageLng = 77.5946

                            val distance = calculateDistanceKm(lat, lng, villageLat, villageLng)
                            val timestamp = doc.getLong("timestamp") ?: 0L
                            val time = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
                                .format(Date(timestamp))
                            val imageUri = doc.getString("imageUri") ?: ""

                            // 💎 PREMIUM CARD
                            Card(
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                border = BorderStroke(1.dp, Color.White.copy(0.08f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(18.dp, RoundedCornerShape(24.dp))
                            ) {

                                Box(
                                    modifier = Modifier
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Color.White.copy(0.07f),
                                                    Color.White.copy(0.02f)
                                                )
                                            )
                                        )
                                        .padding(16.dp)
                                ) {

                                    Column {

                                        // 🔥 TITLE + TIME
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                issue,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )

                                            Text(
                                                time,
                                                fontSize = 11.sp,
                                                color = TextSecondary
                                            )
                                        }

                                        Spacer(Modifier.height(6.dp))

                                        Text(
                                            desc,
                                            color = TextSecondary,
                                            fontSize = 14.sp
                                        )

                                        Spacer(Modifier.height(8.dp))

                                        // 📍 LOCATION CHIP
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.LocationOn,
                                                contentDescription = null,
                                                tint = Color(0xFF81C784),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(Modifier.width(4.dp))
                                            Text(
                                                "📍 ${"%.2f".format(distance)} km from nearest village",
                                                color = Color(0xFF81C784),
                                                fontSize = 12.sp
                                            )
                                        }

                                        // 📸 IMAGE
                                        if (imageUri.isNotEmpty()) {
                                            Spacer(Modifier.height(12.dp))

                                            Image(
                                                painter = rememberAsyncImagePainter(Uri.parse(imageUri)),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(170.dp)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .shadow(12.dp, RoundedCornerShape(16.dp))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 🔙 NAV BUTTON
        TextButton(
            onClick = { navController.navigate(Screen.Dashboard.route) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF81C784))
                Spacer(Modifier.width(4.dp))
                Text("Home", color = Color(0xFF81C784))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterStatusScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
    if (userId == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("User not logged in", color = Color.White)
        }
        return
    }
    val context = LocalContext.current

    // ---------------- STATE ----------------
    var releasedToday by remember { mutableStateOf("Select") }
    val releaseOptions = listOf("Yes", "No")
    var releaseExpanded by remember { mutableStateOf(false) }

    var flow by remember { mutableStateOf("Select") }
    var hours by remember { mutableStateOf("") }
    var acresToday by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val flowOptions = listOf("Smooth", "Slow", "Blocked")

    var waterPercent by remember { mutableIntStateOf(100) }
    var history by remember { mutableStateOf(listOf<DocumentSnapshot>()) }

    // 💧 Realistic constants
    val totalCapacity = 500000.0
    val litersPerAcrePerHour = 4000.0

    // ---------------- FETCH ----------------
    LaunchedEffect(true) {
        db.collection("water_status")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->

                val docs = result.documents.sortedByDescending { doc ->
                    doc.getLong("timestamp") ?: 0L
                }

                history = docs

                if (docs.isNotEmpty()) {
                    val value = docs.first().get("percentage")

                    waterPercent = when (value) {
                        is Long -> value.toInt()
                        is Double -> value.toInt()
                        else -> waterPercent
                    }
                }
            }
    }

    Box(Modifier.fillMaxSize()) {

        WaterPremiumBackground() // 🔥 ADD THIS

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                "Water Intelligence Dashboard",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            // ---------------- INPUT ----------------
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(20.dp, RoundedCornerShape(24.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color.White.copy(alpha = 0.02f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        // 🚰 Water Released?
                        ExposedDropdownMenuBox(
                            expanded = releaseExpanded,
                            onExpandedChange = { releaseExpanded = !releaseExpanded }
                        ) {
                            OutlinedTextField(
                                value = releasedToday,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Water Released Today?") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        releaseExpanded
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            )

                            ExposedDropdownMenu(
                                expanded = releaseExpanded,
                                onDismissRequest = { releaseExpanded = false }
                            ) {
                                releaseOptions.forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            releasedToday = it
                                            releaseExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        // Flow
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {

                            OutlinedTextField(
                                value = flow,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Flow Condition") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                flowOptions.forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            flow = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = hours,
                            onValueChange = { if (it.all { c -> c.isDigit() }) hours = it },
                            label = { Text("Motor Hours") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = acresToday,
                            onValueChange = { if (it.all { c -> c.isDigit() }) acresToday = it },
                            label = { Text("Acres Irrigated") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        GradientButtonn(
                            text = "Analyze Water",
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                                if (releasedToday == "Select") {
                                    Toast.makeText(
                                        context,
                                        "Select water release",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@GradientButtonn
                                }

                                if (flow == "Select" || hours.isEmpty() || acresToday.isEmpty()) {
                                    Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT)
                                        .show()
                                    return@GradientButtonn
                                }

                                val hrs = hours.toDoubleOrNull() ?: 0.0
                                val acres = acresToday.toDoubleOrNull() ?: 0.0

                                val efficiency = when (flow) {
                                    "Smooth" -> 0.9
                                    "Slow" -> 0.7
                                    "Blocked" -> 0.4
                                    else -> 0.75
                                }

// 💧 base water
                                val basePercent =
                                    if (releasedToday == "Yes") 100.0 else waterPercent.toDouble()

                                val usage = acres * hrs * litersPerAcrePerHour * efficiency

                                val currentWater = (basePercent / 100.0) * totalCapacity
                                val newWater = (currentWater - usage).coerceAtLeast(0.0)

// ✅ final %
                                val calculatedPercent = ((newWater / totalCapacity) * 100).toInt()
                                val percent = calculatedPercent.coerceIn(5, 100)

// 🔥 update UI
                                waterPercent = percent

// 🔥 save to Firebase
                                db.collection("water_status").add(
                                    mapOf(
                                        "userId" to userId,
                                        "percentage" to percent,
                                        "usage" to usage,
                                        "timestamp" to System.currentTimeMillis(),
                                        "releasedToday" to releasedToday,
                                        "flow" to flow
                                    )
                                )

                                waterPercent = percent

                            },

                            )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------------- STATUS ----------------
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(20.dp, RoundedCornerShape(24.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color.White.copy(alpha = 0.02f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Current Water Level",
                            color = TextSecondary
                        )

                        Spacer(Modifier.height(20.dp))

                        val animatedProgress by animateFloatAsState(
                            targetValue = waterPercent / 100f,
                            label = ""
                        )

                        // 🌊 HERO CIRCULAR INDICATOR
                        Box(contentAlignment = Alignment.Center) {

                            val animatedProgress by animateFloatAsState(
                                targetValue = waterPercent / 100f,
                                label = ""
                            )

                            CircularProgressIndicator(
                                progress = { animatedProgress },
                                strokeWidth = 10.dp,
                                color = WaterBlueGlow,
                                modifier = Modifier.size(140.dp)
                            )

                            Text(
                                "$waterPercent%",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------------- AI INSIGHTS ----------------
            val insights = generateAdvancedInsights(
                waterPercent,
                flow,
                hours.toIntOrNull() ?: 0,
                acresToday.toIntOrNull() ?: 0,
                history
            )

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(20.dp, RoundedCornerShape(24.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color.White.copy(alpha = 0.02f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("🤖 Smart AI Insights", fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(8.dp))

                        insights.forEach {
                            Text("• $it")
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "📡 Water Activity Feed",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Column {

                Spacer(Modifier.height(10.dp))

                history.take(10).forEach { doc ->

                    val percent = (doc.get("percentage") as? Number)?.toInt() ?: 0
                    val flow = doc.getString("flow") ?: "Unknown"
                    val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                        .format(Date(doc.getLong("timestamp") ?: 0L))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.05f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Text(
                            "Water: $percent% • Flow: $flow • $time",
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }


                Spacer(Modifier.height(16.dp))


                // ---------------- GRAPH ----------------
                if (history.isNotEmpty()) {

                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(20.dp, RoundedCornerShape(24.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.White.copy(alpha = 0.08f),
                                            Color.White.copy(alpha = 0.02f)
                                        )
                                    )
                                )
                                .padding(16.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {

                                Text("📊 Water Trend", fontWeight = FontWeight.Bold)

                                Spacer(Modifier.height(8.dp))

                                AndroidView(
                                    factory = { ctx ->

                                        val chart = BarChart(ctx)

                                        val sdf =
                                            java.text.SimpleDateFormat(
                                                "EEE",
                                                java.util.Locale.getDefault()
                                            )

                                        // 🧠 Step 1: Sort history (latest first)
                                        val sortedHistory = history.sortedByDescending {
                                            it.getLong("timestamp") ?: 0L
                                        }

                                        // 🧠 Step 2: Initialize day map
                                        val dayMap = mutableMapOf(
                                            "Mon" to 0f,
                                            "Tue" to 0f,
                                            "Wed" to 0f,
                                            "Thu" to 0f,
                                            "Fri" to 0f,
                                            "Sat" to 0f,
                                            "Sun" to 0f
                                        )

                                        // 🧠 Step 3: Fill ONLY latest value per day
                                        for (doc in sortedHistory) {

                                            val timestamp = doc.getLong("timestamp") ?: continue
                                            val day = sdf.format(java.util.Date(timestamp))

                                            // skip if already filled (prevents overwrite)
                                            if (dayMap[day] != 0f) continue

                                            val value =
                                                (doc.get("percentage") as? Number)?.toFloat() ?: 0f

                                            dayMap[day] = value
                                        }

                                        // 🧠 Step 4: Fixed order
                                        val orderedDays =
                                            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

                                        val entries = orderedDays.mapIndexed { index, day ->
                                            BarEntry(index.toFloat(), dayMap[day] ?: 0f)
                                        }

                                        // 🎨 Gradient colors
                                        val gradientColors = listOf(
                                            GradientColor(
                                                android.graphics.Color.parseColor("#00C6FF"),
                                                android.graphics.Color.parseColor("#0072FF")
                                            ),
                                            GradientColor(
                                                android.graphics.Color.parseColor("#43E97B"),
                                                android.graphics.Color.parseColor("#38F9D7")
                                            ),
                                            GradientColor(
                                                android.graphics.Color.parseColor("#F7971E"),
                                                android.graphics.Color.parseColor("#FFD200")
                                            )
                                        )

                                        val dataSet = BarDataSet(entries, "").apply {
                                            setGradientColors(gradientColors)
                                            valueTextColor = android.graphics.Color.WHITE
                                            valueTextSize = 12f
                                            highLightAlpha = 0
                                        }

                                        val barData = BarData(dataSet)
                                        barData.barWidth = 0.5f

                                        chart.data = barData

                                        // 🌙 Clean look
                                        chart.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                                        chart.description.isEnabled = false
                                        chart.legend.isEnabled = false

                                        // 📉 Animation
                                        chart.animateY(1200, Easing.EaseInOutCubic)

                                        // 📏 X Axis (days)
                                        chart.xAxis.apply {
                                            position = XAxis.XAxisPosition.BOTTOM
                                            setDrawGridLines(false)
                                            textColor = android.graphics.Color.LTGRAY
                                            textSize = 12f
                                            valueFormatter = IndexAxisValueFormatter(orderedDays)
                                            granularity = 1f
                                            labelCount = orderedDays.size
                                        }

                                        // 📏 Y Axis
                                        chart.axisLeft.apply {
                                            axisMinimum = 0f
                                            axisMaximum = 100f
                                            textColor = android.graphics.Color.LTGRAY
                                            gridColor =
                                                android.graphics.Color.parseColor("#33FFFFFF")
                                            gridLineWidth = 0.5f
                                        }

                                        chart.axisRight.isEnabled = false

                                        chart.setFitBars(true)
                                        chart.setDrawValueAboveBar(true)

                                        chart.invalidate()
                                        chart
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(240.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(80.dp))
            }

        }
    }

@Composable
fun WaterPremiumBackground() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF021B2B)) // 🔥 solid deep blue
    )

    // ✨ keep glow (premium feel)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00C6FF).copy(alpha = 0.12f),
                        Color.Transparent
                    ),
                    radius = 900f
                )
            )
    )
}

fun generateAdvancedInsights(
    percent: Int,
    flow: String,
    hours: Int,
    acres: Int,
    history: List<DocumentSnapshot>
): List<String> {

    val insights = mutableListOf<String>()

    // 🌊 Water Level Intelligence
    when {
        percent > 75 -> insights.add("✅ Water level is healthy and sufficient for irrigation")
        percent > 40 -> insights.add("⚠ Moderate water level — plan usage carefully")
        else -> insights.add("🚨 Critical water level — restrict irrigation immediately")
    }

    // 🌾 Usage Intelligence
    if (hours > 6) {
        insights.add("⚡ High motor usage detected — consider reducing run time")
    }

    if (acres > 8) {
        insights.add("🌾 Large irrigation area — optimize distribution to avoid wastage")
    }

    // 🚧 Flow Analysis
    if (flow == "Blocked") {
        insights.add("🛠 Canal blockage detected — maintenance required urgently")
    } else if (flow == "Slow") {
        insights.add("🐢 Reduced flow — possible silt or minor obstruction")
    }

    // 📉 Trend Prediction
    if (history.size >= 3) {

        val last = history.take(3).map {
            it.getLong("percentage")?.toInt() ?: 0
        }

        val trend = last.zipWithNext { a, b -> b - a }.average()

        if (trend < -5) {
            insights.add("📉 Rapid water depletion trend detected")
        } else {
            insights.add("📊 Stable water usage trend")
        }
    }

    // 🔮 Future Prediction
    val estimatedDays = percent / 12

    if (estimatedDays <= 2) {
        insights.add("⏳ Water may run out within $estimatedDays days")
    } else {
        insights.add("🗓 Estimated water availability: $estimatedDays days")
    }

    // 💡 Smart Recommendation
    if (percent < 40 && hours > 4) {
        insights.add("💡 Recommendation: Reduce irrigation hours to conserve water")
    }

    return insights
}

@Composable
fun MaintenanceScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    var maintenanceList by remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userId?.let { uid ->
            db.collection("maintenance")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener {
                    maintenanceList = it.documents.reversed()
                    isLoading = false
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    listOf(BackgroundColor, Color(0xFF0C1C15))
                )
            )
            .padding(16.dp)
    ) {

        // 🔥 HEADER
        Column {
            Text("Maintenance", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Monitor and manage canal upkeep", color = TextSecondary, fontSize = 13.sp)
        }

        Spacer(Modifier.height(20.dp))

        // 💎 HERO CARD
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color.White.copy(0.08f)),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(25.dp, RoundedCornerShape(28.dp))
        ) {

            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.White.copy(0.08f),
                                Color.White.copy(0.02f)
                            )
                        )
                    )
                    .padding(18.dp)
            ) {

                Column {

                    Text("Next Scheduled Maintenance", color = TextSecondary)

                    Spacer(Modifier.height(8.dp))

                    if (maintenanceList.isNotEmpty()) {

                        val next = maintenanceList.first()

                        Text(
                            next.getString("date") ?: "--",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF81C784)
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            "Issue: ${next.getString("issue")}",
                            color = Color.White
                        )

                    } else {
                        Text("No upcoming maintenance", color = Color.White)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Recent Issues", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(Modifier.height(12.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFF4CAF50))
        } else if (maintenanceList.isEmpty()) {
            Text("No maintenance scheduled", color = TextSecondary)
        } else {

            maintenanceList.forEach { doc ->

                MaintenanceItem(
                    docId = doc.id,
                    issue = doc.getString("issue") ?: "",
                    priority = doc.getString("priority") ?: "",
                    date = doc.getString("date") ?: "",
                    status = doc.getString("status") ?: "Scheduled",
                    type = doc.getString("type") ?: "Issue-Based"
                )

                Spacer(Modifier.height(12.dp))
            }
        }

        Spacer(Modifier.height(20.dp))

        GradientButton(
            text = "Schedule Maintenance",
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.Schedule.route) }
        )

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun MaintenanceItem(
    docId: String,
    issue: String,
    priority: String,
    date: String,
    status: String,
    type: String
) {
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    val priorityColor = when (priority) {
        "High" -> Color(0xFFE57373)
        "Medium" -> Color(0xFFFFB74D)
        else -> Color(0xFF81C784)
    }

    val statusColor = if (status == "Completed")
        Color(0xFF81C784)
    else
        Color(0xFF64B5F6)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.White.copy(0.08f)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(18.dp, RoundedCornerShape(24.dp))
    ) {

        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(0.07f),
                            Color.White.copy(0.02f)
                        )
                    )
                )
                .padding(14.dp)
        ) {

            Column {

                // 🔥 TITLE + TYPE
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(issue, color = Color.White, fontWeight = FontWeight.SemiBold)

                    Text(type, color = TextSecondary, fontSize = 11.sp)
                }

                Spacer(Modifier.height(8.dp))

                // 📊 BADGES
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Badge(priority, priorityColor)
                    Badge(status, statusColor)
                }
                Spacer(Modifier.height(8.dp))

                Text("📅 $date", color = TextSecondary, fontSize = 12.sp)

                // ✅ BUTTON
                if (status != "Completed") {

                    Spacer(Modifier.height(10.dp))

                    Button(
                        onClick = {
                            db.collection("maintenance")
                                .document(docId)
                                .update("status", "Completed")
                                .addOnSuccessListener {
                                    toast(context, "Marked Completed ✅")
                                }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Mark Completed")
                    }
                }
            }
        }
    }
}

@Composable
fun Badge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, color = color, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleMaintenanceScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    var maintenanceType by remember { mutableStateOf("Issue-Based") }
    var typeExpanded by remember { mutableStateOf(false) }

    val maintenanceTypes = listOf(
        "Issue-Based",
        "Regular Maintenance",
        "6-Month Inspection",
        "Emergency Maintenance"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedIssue by remember { mutableStateOf("Select Issue") }
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var priority by remember { mutableStateOf("Auto") }

    val issues = listOf("Leakage", "Blockage", "Silt")
    val context = LocalContext.current

    val userId = auth.currentUser?.uid

    // 🔥 AUTO FETCH LAST REPORTED ISSUE
    LaunchedEffect(Unit) {
        userId?.let { uid ->
            db.collection("reports")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) {
                        val latest = result.documents.last()
                        val issue = latest.getString("issue") ?: ""

                        if (issue.isNotEmpty()) {
                            selectedIssue = issue
                            priority = when (issue) {
                                "Leakage" -> "High"
                                "Blockage" -> "Medium"
                                "Silt" -> "Low"
                                else -> "Low"
                            }
                        }
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {

        Text(
            text = "Schedule Maintenance",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(20.dp, RoundedCornerShape(24.dp))
        ) {

            // ✨ GLASS EFFECT WRAPPER
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.White.copy(alpha = 0.08f),
                                Color.White.copy(alpha = 0.02f)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {

                Column {

                    // 🔥 SECTION
                    Text("Maintenance Details", color = Color(0xFF4CAF50), fontSize = 13.sp)

                    Spacer(Modifier.height(10.dp))

                    // 🔽 TYPE DROPDOWN
                    ExposedDropdownMenuBox(
                        expanded = typeExpanded,
                        onExpandedChange = { typeExpanded = !typeExpanded }
                    ) {

                        OutlinedTextField(
                            value = maintenanceType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Maintenance Type") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(typeExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        )

                        ExposedDropdownMenu(
                            expanded = typeExpanded,
                            onDismissRequest = { typeExpanded = false }
                        ) {
                            maintenanceTypes.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        maintenanceType = it
                                        typeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    if (maintenanceType == "Issue-Based") {

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {

                            OutlinedTextField(
                                value = selectedIssue,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Select Issue") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                issues.forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            selectedIssue = it
                                            priority = when (it) {
                                                "Leakage" -> "High"
                                                "Blockage" -> "Medium"
                                                else -> "Low"
                                            }
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    var generalTask by remember { mutableStateOf("") }

                    if (maintenanceType != "Issue-Based") {
                        OutlinedTextField(
                            value = generalTask,
                            onValueChange = { generalTask = it },
                            label = { Text("Maintenance Task") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 📅 DATE FIELD (UPGRADED UI)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .clickable { showDatePicker = true }
                            .padding(2.dp)
                    ) {
                        OutlinedTextField(
                            value = selectedDate,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            label = { Text("Select Date") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔥 PRIORITY BADGE
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text("Priority", color = TextSecondary)

                        Spacer(Modifier.width(8.dp))

                        val priorityColor = when (priority) {
                            "High" -> Color(0xFFFF5252)
                            "Medium" -> Color(0xFFFFA000)
                            else -> Color(0xFF4CAF50)
                        }

                        Box(
                            modifier = Modifier
                                .background(priorityColor.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                                .border(1.dp, priorityColor, RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(priority, color = priorityColor, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 🚀 BUTTON (PREMIUM)
                    GradientButtonn(
                        text = "Confirm Schedule",
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                14.dp,
                                RoundedCornerShape(16.dp),
                                ambientColor = Color(0xFF4CAF50).copy(0.3f),
                                spotColor = Color(0xFF4CAF50).copy(0.3f)
                            ),
                        onClick = {

                            if (
                                selectedDate.isEmpty() ||
                                (maintenanceType == "Issue-Based" && selectedIssue == "Select Issue") ||
                                (maintenanceType != "Issue-Based" && generalTask.isEmpty())
                            ) {
                                Toast.makeText(context, "Fill all details", Toast.LENGTH_SHORT).show()
                                return@GradientButtonn
                            }

                            val userId = auth.currentUser?.uid ?: return@GradientButtonn

                            val time = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                                .format(Date())

                            val finalIssue = if (maintenanceType == "Issue-Based") {
                                selectedIssue
                            } else {
                                generalTask
                            }

                            val data = hashMapOf(
                                "userId" to userId,
                                "issue" to finalIssue,
                                "date" to selectedDate,
                                "priority" to priority,
                                "status" to "Scheduled",
                                "type" to maintenanceType,
                                "timestamp" to time
                            )

                            db.collection("maintenance")
                                .whereEqualTo("userId", userId)
                                .whereEqualTo("date", selectedDate)
                                .get()
                                .addOnSuccessListener { result ->

                                    if (!result.isEmpty) {
                                        Toast.makeText(context, "Maintenance already scheduled ❌", Toast.LENGTH_LONG).show()
                                    } else {

                                        db.collection("maintenance")
                                            .add(data)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Scheduled ✅", Toast.LENGTH_SHORT).show()

                                                // 🔥 reset form (UI polish)
                                                selectedIssue = "Select Issue"
                                                selectedDate = ""
                                                priority = "Auto"

                                                navController.popBackStack()
                                            }
                                    }
                                }
                        }
                    )
                }
            }
        }
    }

    // 📅 DATE PICKER
    if (showDatePicker) {

        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        selectedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            .format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

fun saveImageUri(context: Context, uri: String) {
    val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    prefs.edit().putString("image_uri", uri).apply()
}

fun loadImageUri(context: Context): String? {
    val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    return prefs.getString("image_uri", null)
}

@Composable
fun ProfileScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid
    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf(loadImageUri(context)?.let { Uri.parse(it) })
    }

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var land by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            saveImageUri(context, it.toString())
        }
    }

    // 🔥 FIRESTORE LOAD
    LaunchedEffect(Unit) {
        userId?.let {
            db.collection("users").document(it)
                .get()
                .addOnSuccessListener { doc ->
                    name = doc.getString("name") ?: ""
                    age = doc.getString("age") ?: ""
                    gender = doc.getString("gender") ?: ""
                    land = doc.getString("land") ?: ""
                    location = doc.getString("location") ?: ""
                    isLoaded = true
                }
        }
    }

    if (!isLoaded) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // 🔥 PROFILE COMPLETENESS
    val filledFields = listOf(name, age, gender, land, location).count { it.isNotEmpty() }
    val completion = (filledFields / 5f * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(20.dp))

        // 👤 IMAGE
        Box(contentAlignment = Alignment.BottomEnd) {

            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(ForestGreen.copy(0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, null, tint = ForestGreen, modifier = Modifier.size(50.dp))
                }
            }

            IconButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .background(ForestGreen, CircleShape)
                    .size(36.dp)
            ) {
                Icon(Icons.Default.CameraAlt, null, tint = Color.White)
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Namma-Nala User", color = TextSecondary)

        Spacer(Modifier.height(16.dp))

        // 🔥 PROFILE COMPLETION BAR
        PremiumCard {
            Column {
                Text("Profile Completion", color = TextSecondary)

                Spacer(Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = completion / 100f,
                    color = Color(0xFF00E676),
                    trackColor = Color.White.copy(0.1f)
                )

                Spacer(Modifier.height(4.dp))

                Text("$completion% Complete", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🌾 FARM SUMMARY (NEW FEATURE)
        PremiumCard {
            Column {
                Text("Farm Summary", color = Color(0xFF4CAF50))

                Spacer(Modifier.height(6.dp))

                Text("Land: $land Acres", color = Color.White)
                Text("Location: $location", color = TextSecondary)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🧠 AI SUMMARY (NEW 🔥)
        PremiumCard {

            val landValue = land.toIntOrNull() ?: 0

            val insight = when {

                landValue == 0 ->
                    "Add your land details to unlock smart irrigation insights"

                landValue > 10 ->
                    "Large-scale farming detected 🌾 — optimize canal distribution and reduce water loss"

                landValue in 6..10 ->
                    "Moderate farm size — balanced irrigation strategy recommended"

                landValue in 1..5 ->
                    "Small farm — efficient water usage achievable with minimal loss"

                else ->
                    "Farm profile looks healthy"
            }

            val insightColor = when {
                landValue == 0 -> Color.Gray
                landValue > 10 -> Color(0xFFFFC107) // warning tone
                else -> Color(0xFF00E676) // healthy green
            }

            Column {

                Text(
                    "AI Insight",
                    color = Color(0xFF00E676),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    insight,
                    color = Color.White,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "Status: ${if (landValue > 10) "Needs Optimization" else "Optimal"}",
                    color = insightColor,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        ProfileEditableItem("👤 Age", age, isEditing) { age = it }
        ProfileEditableItem("⚧ Gender", gender, isEditing) { gender = it }
        ProfileEditableItem("🌾 Land Owned", land, isEditing) { land = it }
        ProfileItem("📍 Location", location)

        Spacer(Modifier.height(20.dp))

        GradientButton(
            text = if (isEditing) "Save Profile" else "Edit Profile",
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                if (isEditing) {

                    val updatedData = hashMapOf(
                        "name" to name,
                        "age" to age,
                        "gender" to gender,
                        "land" to land,
                        "location" to location
                    )

                    userId?.let {
                        db.collection("users").document(it)
                            .update(updatedData as Map<String, Any>)
                            .addOnSuccessListener {
                                toast(context, "Profile Updated ✅")
                                isEditing = false
                            }
                            .addOnFailureListener {
                                toast(context, "Update Failed ❌")
                            }
                    }

                } else {
                    isEditing = true
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                auth.signOut()
                navController.navigate("login") {
                    popUpTo("dashboard") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Logout", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun PremiumCard(content: @Composable ColumnScope.() -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.White.copy(0.08f)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(0.06f),
                            Color.White.copy(0.02f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun ProfileItem(title: String, value: String, editable: Boolean = false) {

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp, RoundedCornerShape(24.dp))
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = TextSecondary)
            Text(value, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ProfileEditableItem(
    title: String,
    value: String,
    isEditing: Boolean,
    onChange: (String) -> Unit
) {

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp, RoundedCornerShape(24.dp))
    ) {

        if (isEditing) {
            Column(Modifier.padding(16.dp)) {
                Text(title, color = TextSecondary)
                OutlinedTextField(
                    value = value,
                    onValueChange = onChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, color = TextSecondary)
                Text(value, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(ForestGreenGlow, ForestGreen)
                    ),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun GradientButtonn(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        label = ""
    )

    Box(
        modifier = modifier
            .height(55.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)

            // 🔥 glow shadow (blue)
            .shadow(
                elevation = 14.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFF1976D2).copy(alpha = 0.4f),
                spotColor = Color(0xFF1976D2).copy(alpha = 0.4f)
            )

            .clip(RoundedCornerShape(16.dp))

            // 🌊 DARK BLUE GRADIENT
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF0D47A1), // deep water blue
                        Color(0xFF1976D2)  // premium highlight
                    )
                )
            )

            .clickable(
                interactionSource = interaction,
                indication = null
            ) {
                onClick()
            },

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

