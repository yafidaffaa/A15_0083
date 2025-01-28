package com.yafidaffaaa.uas_pam.ui.view.kandang

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yafidaffaaa.uas_pam.R
import com.yafidaffaaa.uas_pam.model.Kandang
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiHomeKandang
import com.yafidaffaaa.uas_pam.ui.view.hewan.OnError
import com.yafidaffaaa.uas_pam.ui.view.hewan.OnLoading
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.HomeUiStateKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.HomeViewModelKandang
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenKandang(
    navigateToInsertKandang: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    viewModel: HomeViewModelKandang = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchQuery = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchQuery.value) {
        coroutineScope.launch {
            delay(500)
            if (searchQuery.value.isNotEmpty()) {
                viewModel.searchKandang(searchQuery.value)
            } else {
                viewModel.getKandang()
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHomeKandang.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = {
                    navController.popBackStack()
                },
                showRefresh = false,
                onRefresh = {
                    viewModel.getKandang()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInsertKandang,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color.Black,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kandang", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { query ->
                    searchQuery.value = query
                },
                label = { Text("Search Kandang") },
                modifier = Modifier.fillMaxWidth().padding(top = 0.dp, start = 16.dp, end = 16.dp)
            )

            HomeStatusKandang(
                homeUiState = viewModel.kndUIState,
                retryAction = { viewModel.getKandang() },
                modifier = Modifier.padding(top = 10.dp),
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun HomeStatusKandang(
    homeUiState: HomeUiStateKandang,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier.padding(top = 8.dp),
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiStateKandang.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStateKandang.Success -> {
            if (homeUiState.kandang.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kandang")
                }
            } else {
                KandangLayout(
                    kandang = homeUiState.kandang,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_kandang.toString())
                    }
                )
            }
        }
        is HomeUiStateKandang.Error -> OnError(retryAction, errorMessage = homeUiState.message, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KandangLayout(
    kandang: List<Kandang>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kandang) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kandang) { kandang ->
            KandangCard(
                kandang = kandang,
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun KandangCard(
    kandang: Kandang,
    modifier: Modifier = Modifier,
    onDetailClick: (Kandang) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Kandang ID: ${kandang.id_kandang}",
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Kapasitas: ${kandang.kapasitas}",
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Lokasi: ${kandang.lokasi}",
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Detail Kandang",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onDetailClick(kandang) },
                tint = Color.White
            )
        }
    }
}