package com.yafidaffaaa.uas_pam.ui.customwidget.hewan
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TipePakanRadioButton(
    selectedValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    val listPakan = listOf("Omnivora", "Herbivora", "Karnivora")

    Column {
        Text(text = "Tipe Pakan ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        listPakan.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = { onValueChange(item) }
                )
                Text(text = item, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}