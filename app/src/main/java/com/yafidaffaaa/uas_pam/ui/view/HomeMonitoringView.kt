package com.yafidaffaaa.uas_pam.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.HomeViewModelHewan

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeMonitoringView (
    modifier: Modifier = Modifier,
    manageKandang: () -> Unit = { },
    manageHewan: () -> Unit = { },
    manageMonitoring: () -> Unit = { },
    managePetugas: () -> Unit = { },
    viewModel: HomeViewModelHewan = viewModel(factory = PenyediaViewModel.Factory),

    ) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = DestinasiMonitoring.titleRes,
                canNavigateBack = false,
                navigateUp = {},
                showRefresh = false,
                onRefresh = {}
            )
        },
        content = { paddingValues ->
            BodyHomeView(
                manageKandang = manageKandang,
                manageHewan = manageHewan,
                manageMonitoring = manageMonitoring,
                managePetugas = managePetugas,
                paddingValues = paddingValues
            )
        }
    )
}

@Composable
fun BodyHomeView (
    manageKandang: () -> Unit,
    manageHewan: () -> Unit,
    manageMonitoring: () -> Unit,
    managePetugas: () -> Unit,
    paddingValues: PaddingValues,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        item {
            HomeCard(
                title = "Management Kandang",
                icon = Icons.Filled.Info,
                onClick = manageKandang,
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFF4B6F82)
                    )
                )
            )
        }
        item {
            HomeCard(
                title = "Management Hewan",
                icon = Icons.Filled.Favorite,
                onClick = manageHewan,
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFF4B6F82)
                    )
                )
            )
        }
        item {
            HomeCard(
                title = "Management Monitoring",
                icon = Icons.Filled.CheckCircle,
                onClick = manageMonitoring,
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFF4B6F82)
                    )
                )
            )
        }
        item {
            HomeCard(
                title = "Management Petugas",
                icon = Icons.Filled.Person,
                onClick = managePetugas,
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFF4B6F82)
                    )
                )
            )
        }
    }
}

@Composable
fun HomeCard (
    title: String,
    icon: ImageVector,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale = animateFloatAsState(
        targetValue = if (clicked) 0.95f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                clicked = !clicked
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = gradient)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(45.dp)
                )

                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}