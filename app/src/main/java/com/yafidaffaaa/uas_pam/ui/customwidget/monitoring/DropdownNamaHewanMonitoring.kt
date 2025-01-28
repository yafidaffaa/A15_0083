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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.yafidaffaaa.uas_pam.model.KandangWithHewan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownNamaHewanMonitoring(
    hewanList: List<KandangWithHewan>,
    selectedHewanId: Int?,
    onHewanSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedHewan = selectedHewanId?.let { id ->
        hewanList.find { it.Id_kandang == id }
    }?.let {
        "${it.Nama_hewan} (ID Kandang: ${it.Id_kandang})"
    } ?: "Pilih Hewan"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedHewan,
            onValueChange = {},
            label = { Text("Pilih Hewan") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            hewanList.forEach { kandang ->
                DropdownMenuItem(
                    text = {
                        Text(
                            buildAnnotatedString {
                                append(kandang.Nama_hewan)
                                append(" (")
                                withStyle(style = SpanStyle(color = Color.Red)) {
                                    append("ID Kandang: ${kandang.Id_kandang}")
                                }
                                append(")")
                            }
                        )
                    },
                    onClick = {
                        kandang.Id_kandang?.let { onHewanSelected(it) }
                        expanded = false
                    }
                )
            }
        }
    }
}