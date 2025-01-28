package com.yafidaffaaa.uas_pam.ui.viewmodel.kandang

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.yafidaffaaa.uas_pam.model.Kandang
import com.yafidaffaaa.uas_pam.repository.KandangRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiStateKandang {
    data class Success(val kandang: List<Kandang>) : HomeUiStateKandang()
    object Loading : HomeUiStateKandang()
    data class Error(val message: String) : HomeUiStateKandang()
}

class HomeViewModelKandang(private val knd: KandangRepository) : ViewModel() {
    var kndUIState: HomeUiStateKandang by mutableStateOf(HomeUiStateKandang.Loading)
        private set

    init {
        getKandang()
    }

    fun getKandang() {
        viewModelScope.launch {
            kndUIState = HomeUiStateKandang.Loading
            kndUIState = try {
                HomeUiStateKandang.Success(knd.getKandang())
            } catch (e: IOException) {
                Log.e("KandangViewModel", "Kesalahan jaringan: ${e.message}", e)
                HomeUiStateKandang.Error("Kesalahan jaringan: ${e.message}")
            } catch (e: HttpException) {
                Log.e("KandangViewModel", "Kesalahan server: ${e.message}", e)
                HomeUiStateKandang.Error("Kesalahan server: ${e.message}")
            } catch (e: Exception) {
                Log.e("KandangViewModel", "Terjadi kesalahan tak terduga: ${e.message}", e)
                HomeUiStateKandang.Error("Terjadi kesalahan tak terduga: ${e.message}")
            }
        }
    }

    fun searchKandang(query: String) {
        kndUIState = HomeUiStateKandang.Loading
        viewModelScope.launch {
            try {
                val result = knd.searchKandang(query)
                if (result.isNotEmpty()) {
                    kndUIState = HomeUiStateKandang.Success(result)
                } else {
                    kndUIState = HomeUiStateKandang.Error("No matching enclosures found")
                }
            } catch (e: Exception) {
                Log.e("KandangViewModel", "Error searching enclosure: ${e.message}", e)
                kndUIState = HomeUiStateKandang.Error("An error occurred: ${e.message}")
            }
        }
    }
}