package com.yafidaffaaa.uas_pam.ui.view.hewan

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
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiHome
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.HomeUiState
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.HomeViewModelHewan

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToInsertHewan: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    viewModel: HomeViewModelHewan = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getHewan()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = {
                    navController.popBackStack()
                },
                showRefresh = false,
                onRefresh = {
                    viewModel.getHewan()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInsertHewan,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color.Black,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Hewan", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { query ->
                    searchQuery.value = query
                    if (query.isEmpty()) {
                        viewModel.getHewan()
                    } else {
                        viewModel.searchHewan(query)
                    }
                },
                label = { Text("Search Hewan") },
                modifier = Modifier.fillMaxWidth().padding(top = 0.dp, start = 16.dp, end = 16.dp)
            )

            HomeStatus(
                homeUiState = viewModel.hwnUIState,
                retryAction = { viewModel.getHewan() },
                modifier = Modifier.fillMaxSize().padding(top = 10.dp),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.getHewan()
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
    onDeleteClick: (Hewan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {

    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.hewan.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Hewan" )
                }
            }else {
                HwnLayout(

                    hewan = homeUiState.hewan, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_hewan.toString())
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
fun HwnLayout(
    hewan: List<Hewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Hewan) -> Unit,
    onDeleteClick: (Hewan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(hewan) { hewan ->
            HwnCard(
                hewan = hewan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(hewan) },
                onDeleteClick = {
                    onDeleteClick(hewan)
                }
            )
        }
    }
}

@Composable
fun HwnCard(
    hewan: Hewan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Hewan) -> Unit = {}
) {

    val backgroundColor = when (hewan.tipe_pakan.lowercase()) {
        "karnivora" -> colorResource(id = R.color.karnivora_red)
        "herbivora" -> colorResource(id = R.color.herbivora_green)
        "omnivora" -> colorResource(id = R.color.omnivora_blue)
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hewan.nama_hewan,
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Text(
                text = hewan.tipe_pakan,
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}