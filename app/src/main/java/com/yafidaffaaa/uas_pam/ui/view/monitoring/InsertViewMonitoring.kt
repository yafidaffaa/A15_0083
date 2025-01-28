package com.yafidaffaaa.uas_pam.ui.view.monitoring

import com.yafidaffaaa.uas_pam.ui.customwidget.monitoring.DropdownNamaHewanMonitoring
import com.yafidaffaaa.uas_pam.ui.customwidget.monitoring.DropdownNamaPetugasMonitoring

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yafidaffaaa.uas_pam.model.KandangWithHewan
import com.yafidaffaaa.uas_pam.model.Petugas
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.InsertUiEventMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.InsertUiStateMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.InsertViewModelMonitoring
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMonitoringScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelMonitoring = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val petugasList by viewModel.petugasList.collectAsState()
    val kandangList by viewModel.kandangList.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = "Tambah Monitoring",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                showRefresh = false,
                onRefresh = {}
            )
        }
    ) { innerPadding ->
        EntryMonitoringBody(
            insertUiState = viewModel.uiState,
            onMonitoringValueChange = viewModel::updateInsertMonitoringState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMonitoring()
                    navigateBack()
                }
            },
            petugasList = petugasList,
            HewanList = kandangList,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryMonitoringBody(
    insertUiState: InsertUiStateMonitoring,
    onMonitoringValueChange: (InsertUiEventMonitoring) -> Unit,
    onSaveClick: () -> Unit,
    petugasList: List<Petugas>,
    HewanList: List<KandangWithHewan>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        item {
            FormInputMonitoring(
                insertUiEvent = insertUiState.insertUiEvent,
                onValueChange = onMonitoringValueChange,
                modifier = Modifier.fillMaxWidth(),
                petugasList = petugasList,
                kandangList = HewanList,
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputMonitoring(
    insertUiEvent: InsertUiEventMonitoring = InsertUiEventMonitoring(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEventMonitoring) -> Unit = {},
    enabled: Boolean = true,
    petugasList: List<Petugas>,
    kandangList: List<KandangWithHewan>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DropdownNamaPetugasMonitoring(
            petugasList = petugasList,
            selectedPetugasId = insertUiEvent.id_petugas ?: 0,
            onPetugasSelected = { selectedId ->
                onValueChange(insertUiEvent.copy(id_petugas = selectedId))
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownNamaHewanMonitoring(
            hewanList = kandangList,
            selectedHewanId = insertUiEvent.id_kandang ?: 0,
            onHewanSelected = { selectedId ->
                onValueChange(insertUiEvent.copy(id_kandang = selectedId))
            },
            modifier = Modifier.fillMaxWidth()
        )

        DatePickerInput(
            selectedDate = insertUiEvent.tanggal_monitoring,
            onDateSelected = { selectedDate ->
                onValueChange(insertUiEvent.copy(tanggal_monitoring = selectedDate))
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertUiEvent.hewan_sakit.toString(),
            onValueChange = {
                onValueChange(insertUiEvent.copy(hewan_sakit = it.toIntOrNull() ?: 0))
            },
            label = { Text("Jumlah Hewan Sakit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            value = insertUiEvent.hewan_sehat.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0
                onValueChange(insertUiEvent.copy(hewan_sehat = newValue))
            },
            label = { Text("Jumlah Hewan Sehat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerInput(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDatePickerDialog by remember { mutableStateOf(false) }

    if (showDatePickerDialog) {
        val currentDate = LocalDate.now()
        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                onDateSelected(selectedDate)
                showDatePickerDialog = false
            },
            currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth
        )
        datePickerDialog.show()
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text("Tanggal Monitoring") },
        readOnly = true,
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { showDatePickerDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
            }
        }
    )
}