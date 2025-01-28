package com.yafidaffaaa.uas_pam.ui.view.monitoring

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yafidaffaaa.uas_pam.ui.customwidget.TopAppBar
import com.yafidaffaaa.uas_pam.ui.navigation.DestinasiUpdate
import com.yafidaffaaa.uas_pam.ui.viewmodel.PenyediaViewModel
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.UpdateViewModelMonitoring
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenMonitoring(
    idMonitoring: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateViewModelMonitoring = viewModel(factory = PenyediaViewModel.Factory, key = idMonitoring )
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
                showRefresh = false,
                onRefresh = {}
            )
        }
    ) { padding ->
        EntryMonitoringBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onMonitoringValueChange = viewModel::updateInsertMonitoringState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMonitoring()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            },
            petugasList = viewModel.petugasList.value,
            HewanList = viewModel.kandangList.value
        )
    }
}