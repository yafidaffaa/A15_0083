package com.yafidaffaaa.uas_pam.ui.view.kandang

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yafidaffaaa.uas_pam.model.Hewan
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.customwidget.kandang.DropdownNamaHewan
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.InsertUiEventKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.InsertUiStateKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.InsertViewModelKandang
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertKandangScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelKandang = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val hewanList by viewModel.hewanList.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = "Tambah Kandang",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                showRefresh = false,
                onRefresh = {}
            )
        }
    ) { innerPadding ->
        EntryKandangBody(
            insertUiState = viewModel.uiState,
            onKandangValueChange = viewModel::updateInsertKandangState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKandang()
                    navigateBack()
                }
            },
            hewanList = hewanList,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryKandangBody(
    insertUiState: InsertUiStateKandang,
    onKandangValueChange: (InsertUiEventKandang) -> Unit,
    onSaveClick: () -> Unit,
    hewanList: List<Hewan>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        item {
            FormInputKandang(
                insertUiEvent = insertUiState.insertUiEvent,
                onValueChange = onKandangValueChange,
                modifier = Modifier.fillMaxWidth(),
                hewanList = hewanList,
            )
        }
        item {
            Button(
                onClick = onSaveClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Simpan")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputKandang(
    insertUiEvent: InsertUiEventKandang = InsertUiEventKandang(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEventKandang) -> Unit = {},
    enabled: Boolean = true,
    hewanList: List<Hewan>
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DropdownNamaHewan(
            hewanList = hewanList,
            selectedHewanId = insertUiEvent.id_hewan,
            onHewanSelected = { selectedId ->
                onValueChange(insertUiEvent.copy(id_hewan = selectedId))
            },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = insertUiEvent.kapasitas,
            onValueChange = { onValueChange(insertUiEvent.copy(kapasitas = it)) },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            value = insertUiEvent.lokasi,
            onValueChange = { onValueChange(insertUiEvent.copy(lokasi = it)) },
            label = { Text("Lokasi Kandang") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}