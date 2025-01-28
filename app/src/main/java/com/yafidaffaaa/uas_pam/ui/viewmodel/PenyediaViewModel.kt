package com.yafidaffaaa.uas_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yafidaffaaa.uas_pam.MonitoringApplication
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.DetailViewModelHewan
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.HomeViewModelHewan
import com.yafidaffaaa.uas_pam.ui.viewmodel.hewan.InsertViewModelHewan
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.DetailViewModelKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.HomeViewModelKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.InsertViewModelKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.kandang.UpdateViewModelKandang
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.DetailViewModelMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.HomeViewModelMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.InsertViewModelMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.monitoring.UpdateViewModelMonitoring
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.DetailViewModelPetugas
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.HomeViewModelPetugas
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.InsertViewModelPetugas
import com.yafidaffaaa.uas_pam.ui.viewmodel.petugas.UpdateViewModelPetugas

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModelHewan(MonitoringApplication().container.hewanRepository)
        }
        initializer {
            InsertViewModelHewan(MonitoringApplication().container.hewanRepository)
        }
        initializer {
            DetailViewModelHewan(MonitoringApplication().container.hewanRepository)
        }
        initializer {
            UpdateViewModelHewan(
                this.createSavedStateHandle(),
                MonitoringApplication().container.hewanRepository
            )
        }
        initializer {
            HomeViewModelPetugas(MonitoringApplication().container.petugasRepository)
        }
        initializer {
            InsertViewModelPetugas(MonitoringApplication().container.petugasRepository)
        }
        initializer {
            DetailViewModelPetugas(MonitoringApplication().container.petugasRepository)
        }
        initializer {
            UpdateViewModelPetugas(
                this.createSavedStateHandle(),
                MonitoringApplication().container.petugasRepository
            )
        }

        initializer {
            HomeViewModelKandang(MonitoringApplication().container.kandangRepository)
        }
        initializer {
            InsertViewModelKandang(
                MonitoringApplication().container.kandangRepository,
                MonitoringApplication().container.hewanRepository
            )
        }
        initializer {
            DetailViewModelKandang(MonitoringApplication().container.kandangRepository)
        }
        initializer {
            UpdateViewModelKandang(
                this.createSavedStateHandle(),
                MonitoringApplication().container.kandangRepository,
                MonitoringApplication().container.hewanRepository
            )
        }

        initializer {
            HomeViewModelMonitoring(MonitoringApplication().container.monitoringRepository)
        }
        initializer {
            InsertViewModelMonitoring(
                MonitoringApplication().container.monitoringRepository,
            )
        }
        initializer {
            DetailViewModelMonitoring(MonitoringApplication().container.monitoringRepository)
        }
        initializer {
            UpdateViewModelMonitoring(
                this.createSavedStateHandle(),
                MonitoringApplication().container.monitoringRepository,
                MonitoringApplication().container.kandangRepository,
                MonitoringApplication().container.petugasRepository
            )
        }
    }
}

fun CreationExtras.MonitoringApplication(): MonitoringApplication = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MonitoringApplication
        )