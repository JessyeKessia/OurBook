package com.example.ourbook.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import kotlinx.coroutines.flow.StateFlow
import android.net.Uri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: OurBookRepositoryLocal
) : ViewModel() {

    val user = repository.currentUser

    fun setPhoto(uri: Uri) {
        // 🔥 salva no banco
        viewModelScope.launch {
            repository.updateUserPhoto(uri)
        }
    }
}