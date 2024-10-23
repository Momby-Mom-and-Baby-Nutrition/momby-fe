package com.example.momby.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.momby.data.dao.HistoryDao
import com.example.momby.data.entity.HistoryEntity
import com.example.momby.data.model.MenuOptimized
import com.example.momby.model.History
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val historyDao: HistoryDao
) : ViewModel() {
    private val _historyList = MutableStateFlow<List<History?>>(emptyList())
    val historyList: StateFlow<List<History?>> = _historyList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchHistory()

        val curUID = auth.currentUser?.uid
        println("Nilai curUID = " + curUID)
    }

    //TESTING FUNCTION
    private fun checkMenuList() {
        if (_historyList.value.isNotEmpty()) {
            for (i in 0 until _historyList.value.size) {
                println("Nilai dari index ke: $i, adalah ${_historyList.value[i]}")
            }
        } else {
            println("Daftar kosong")
        }
    }

    fun fetchHistory() {
        viewModelScope.launch {
            try {
                val curUID = auth.currentUser?.uid
                if (curUID != null) {
                    historyDao.getAllHistoryByUid(curUID).collect { historyEntity ->
                        val updateHistoryList = historyEntity.map {
                            History(
                                menuOptimized = it.menuOptimized,
                                date = Date(it.date)
                            )
                        }
                        _historyList.value = updateHistoryList
                        println("BERHASIL FETCH MENU")
                        checkMenuList()
                        if (_historyList.value != null){
                            _isLoading.value = false
                        }
                    }
                } else {
                    println("User is not authenticated, unable to fetch history")
                }
            } catch (e: Exception) {
                println("Terjadi error saat fetch history data: $e")
            } finally {
                _isLoading.value = false
                println("Is Lading BERUBAH false")

            }
        }
    }

    fun formatDate(date: Date?): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")) // Format: 25 Oktober 2024
        return date?.let { dateFormat.format(it) } ?: "Tanggal tidak tersedia"
    }

}