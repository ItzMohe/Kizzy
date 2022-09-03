package com.my.kizzy.ui.screen.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.my.kizzy.ui.common.BackButton
import com.my.kizzy.ui.common.PreferencesHint
import com.my.kizzy.ui.common.Routes

@SuppressLint("BatteryLife")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(navController: NavHostController) {
    val context = LocalContext.current
    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    var showBatteryHint by remember { mutableStateOf(!pm.isIgnoringBatteryOptimizations(context.packageName)) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
        ) {
            SmallTopAppBar(
                title = {},
                navigationIcon = { BackButton { navController.popBackStack() } },
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                modifier = Modifier.padding(start = 24.dp, top = 48.dp),
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge
            )
            LazyColumn(modifier = Modifier.padding(top = 48.dp)) {
                item {
                    AnimatedVisibility(visible = showBatteryHint) {
                        PreferencesHint(
                            title = "Battery Optimisation",
                            icon = Icons.Default.EnergySavingsLeaf,
                            description = "Turn off battery optimisation for better stability of rpc"
                        ) {
                            context.startActivity(Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                data = Uri.parse("package:${context.packageName}")
                            })
                            showBatteryHint =
                                !pm.isIgnoringBatteryOptimizations(context.packageName)
                        }
                    }
                }
                item {
                    SettingItem(
                        title = "Account",
                        description = "Account info",
                        icon = Icons.Outlined.Person
                    ) {
                        navController.navigate(Routes.PROFILE) {
                            launchSingleTop = true

                        }
                    }
                }
                item {
                    SettingItem(
                        title = "Display",
                        description = "Theme,Dynamic Colors,Languages",
                        icon = Icons.Outlined.Palette
                    ) {
                        navController.navigate(Routes.STYLE_AND_APPEAREANCE) {
                            launchSingleTop = true
                        }
                    }
                }
                item {
                    SettingItem(
                        title = "About",
                        description = "Version,releases",
                        icon = Icons.Outlined.Info
                    ) {
                        navController.navigate(Routes.ABOUT) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SettingItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(25.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 0.dp)
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
