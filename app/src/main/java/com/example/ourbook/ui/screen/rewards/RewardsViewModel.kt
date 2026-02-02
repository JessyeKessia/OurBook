package com.example.ourbook.ui.screen.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.model.*
import com.example.ourbook.data.local.*
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RewardsUiState(
    val coins: Int = 0,
    val rewards: List<RewardItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class RewardsViewModel(
    private val repository: OurBookRepositoryLocal // Agora recebe apenas o repositório local
) : ViewModel() {

    private val _uiState = MutableStateFlow(RewardsUiState())
    val uiState: StateFlow<RewardsUiState> = _uiState.asStateFlow()

    init {
        observeUser()
        loadRewards()
    }

    private fun observeUser() {
        viewModelScope.launch {
            repository.currentUser.collectLatest { user ->
                _uiState.update { it.copy(coins = user?.coins ?: 0) }
            }
        }
    }

    private fun loadRewards() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val rewards = repository.getRewards() // Busca do banco local
                _uiState.update { it.copy(rewards = rewards, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Erro ao carregar recompensas", isLoading = false) }
            }
        }
    }

    fun redeem(rewardId: Long, cost: Int) {
        viewModelScope.launch {
            try {
                repository.redeemReward(rewardId, cost) // Executa a lógica no banco local
                loadRewards()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Erro ao resgatar recompensa") }
            }
        }
    }
}
