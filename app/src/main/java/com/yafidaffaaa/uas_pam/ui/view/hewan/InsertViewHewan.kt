package com.yafidaffaaa.uas_pam.ui.view.hewan

import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiInsert
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.InsertUiEvent
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.InsertUiState
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.InsertViewModelHewan
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yafidaffaaa.uas_pam.ui.customwidget.hewan.TipePakanRadioButton
import com.yafidaffaaa.uas_pam.ui.customwidget.hewan.ZonaWilayahDropdown
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertHwnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelHewan = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsert.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                showRefresh = false,
                onRefresh = {}
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertHwnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertHwn()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        item {
            FormInput(
                insertUiEvent = insertUiState.insertUiEvent,
                onValueChange = onSiswaValueChange,
                modifier = Modifier.fillMaxWidth()
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
fun FormInput(
    insertUiEvent: InsertUiEvent = InsertUiEvent(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = insertUiEvent.nama_hewan,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_hewan = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        TipePakanRadioButton(
            selectedValue = insertUiEvent.tipe_pakan,
            onValueChange = { selectedValue ->
                onValueChange(insertUiEvent.copy(tipe_pakan = selectedValue))
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertUiEvent.populasi,
            onValueChange = { onValueChange(insertUiEvent.copy(populasi = it)) },
            label = { Text("Populasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        ZonaWilayahDropdown(
            selectedValue = insertUiEvent.zona_wilayah,
            onValueChange = { selectedValue ->
                onValueChange(insertUiEvent.copy(zona_wilayah = selectedValue))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}