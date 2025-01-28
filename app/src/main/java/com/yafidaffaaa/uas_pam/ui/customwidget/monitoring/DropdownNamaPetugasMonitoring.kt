package com.yafidaffaaa.uas_pam.ui.customwidget.monitoring

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yafidaffaaa.uas_pam.model.Petugas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownNamaPetugasMonitoring(
    petugasList: List<Petugas>,
    selectedPetugasId: Int?,
    onPetugasSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedPetugas = petugasList.find { it.id_petugas == selectedPetugasId }?.nama_petugas ?: "Pilih Petugas"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedPetugas,
            onValueChange = {},
            label = { Text("Pilih Petugas (Dokter Hewan)") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            petugasList.forEach { petugas ->
                DropdownMenuItem(
                    text = { Text(petugas.nama_petugas) },
                    onClick = {
                        petugas.id_petugas?.let { id -> onPetugasSelected(id) }
                        expanded = false
                    }
                )
            }
        }
    }
}