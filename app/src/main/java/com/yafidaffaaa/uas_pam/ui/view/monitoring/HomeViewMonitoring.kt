package com.yafidaffaaa.uas_pam.ui.view.monitoring

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yafidaffaaa.uas_pam.R
import com.yafidaffaaa.uas_pam.model.Monitoring
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiHomeMonitoring
import com.yafidaffaaa.uas_pam.ui.view.hewan.OnError
import com.yafidaffaaa.uas_pam.ui.view.hewan.OnLoading
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.HomeUiStateMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.HomeViewModelMonitoring

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMonitoring(
    navigateToInsertMonitoring: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    viewModel: HomeViewModelMonitoring = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getMonitoring()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = DestinasiHomeMonitoring.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = {
                    navController.popBackStack()
                },
                showRefresh = false,
                onRefresh = {
                    viewModel.getMonitoring()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInsertMonitoring,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color.Black,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Monitoring", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            HomeStatusMonitoring(
                homeUiState = viewModel.monitoringUIState,
                retryAction = { viewModel.getMonitoring() },
                modifier = Modifier.padding(top = 10.dp),
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun HomeStatusMonitoring(
    homeUiState: HomeUiStateMonitoring,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier.padding(top = 8.dp),
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiStateMonitoring.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStateMonitoring.Success -> {
            if (homeUiState.monitoring.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Monitoring")
                }
            } else {
                MonitoringLayout(
                    monitoring = homeUiState.monitoring,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_monitoring.toString())
                    }
                )
            }
        }
        is HomeUiStateMonitoring.Error -> OnError(retryAction, errorMessage = homeUiState.message, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun MonitoringLayout(
    monitoring: List<Monitoring>,
    modifier: Modifier = Modifier,
    onDetailClick: (Monitoring) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(monitoring) { item ->
            MonitoringCard(
                monitoring = item,
                modifier = Modifier.fillMaxWidth(),
                onDetailClick = { onDetailClick(item) }
            )
        }
    }
}

@Composable
fun MonitoringCard(
    monitoring: Monitoring,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Monitoring ID: ${monitoring.id_monitoring}",
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onDetailClick) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Detail Monitoring", tint = Color.White)
                }
            }

            Text(
                text = "Tanggal Monitoring: ${monitoring.tanggal_monitoring}",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Status: ${monitoring.status}",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}