package com.yafidaffaaa.uas_pam.ui.view.petugas

import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.InsertUiEvent
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.InsertUiState
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.InsertViewModelPetugas
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yafidaffaaa.uas_pam.ui.customwidget.petugas.JabatanRadioButton
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiInsertPetugas
import kotlinx.coroutines.launch



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
            value = insertUiEvent.nama_petugas,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_petugas = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        JabatanRadioButton(
            selectedValue = insertUiEvent.jabatan,
            onValueChange = { selectedValue ->
                onValueChange(insertUiEvent.copy(jabatan = selectedValue))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
