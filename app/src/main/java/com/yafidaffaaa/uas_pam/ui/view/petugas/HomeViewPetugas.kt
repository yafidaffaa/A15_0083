package com.yafidaffaaa.uas_pam.ui.view.petugas

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yafidaffaaa.uas_pam.R
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiHomePetugas
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.HomeUiState
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.HomeViewModelPetugas

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPetugas(
    navigateToInsertPetugas: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    viewModel: HomeViewModelPetugas = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getPetugas()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHomePetugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = {
                    navController.popBackStack()
                },
                showRefresh = false,
                onRefresh = {
                    viewModel.getPetugas()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInsertPetugas,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color.Black,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Officer", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { query ->
                    searchQuery.value = query
                    if (query.isEmpty()) {
                        viewModel.getPetugas()
                    } else {
                        viewModel.searchPetugas(query)
                    }
                },
                label = { Text("Search Petugas") },
                modifier = Modifier.fillMaxWidth().padding(top = 0.dp, start = 16.dp, end = 16.dp)
            )

            HomeStatus(
                homeUiState = viewModel.ptgUIState,
                retryAction = { viewModel.getPetugas() },
                modifier = Modifier.padding(top = 10.dp),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.getPetugas()
                }
            )
        }
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier.padding(top = 8.dp),
    onDeleteClick: (Petugas) -> Unit = {},
    onDetailClick: (String) -> Unit
) {

    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.petugas.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Petugas" )
                }
            }else {
                PtgLayout(
                    petugas = homeUiState.petugas, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_petugas.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction,errorMessage = homeUiState.message, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */

@Composable
fun OnError(retryAction: () -> Unit, errorMessage: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = errorMessage, modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PtgLayout(
    petugas: List<Petugas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Petugas) -> Unit,
    onDeleteClick: (Petugas) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(petugas) { petugas ->
            PtgCard(
                petugas = petugas,
                modifier = Modifier
                    .fillMaxWidth(),
                onDeleteClick = {
                    onDeleteClick(petugas)
                },
                onDetailClick = {
                    onDetailClick(petugas)
                }
            )
        }
    }
}

@Composable
fun PtgCard(
    petugas: Petugas,
    modifier: Modifier = Modifier,
    onDeleteClick: (Petugas) -> Unit = {},
    onDetailClick: (Petugas) -> Unit
) {
    val backgroundColor = when (petugas.jabatan) {
        "Keeper" -> colorResource(id = R.color.karnivora_red)
        "Dokter Hewan" -> colorResource(id = R.color.herbivora_green)
        "Kurator" -> colorResource(id = R.color.omnivora_blue)
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
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
                    text = petugas.nama_petugas,
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleLarge,
                )

                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Detail Petugas",
                    modifier = Modifier
                        .clickable { onDetailClick(petugas) }
                        .size(24.dp),
                    tint = Color.White
                )
            }
            Text(
                text = petugas.jabatan,
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}