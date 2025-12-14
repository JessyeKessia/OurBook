package com.example.ourbook.ui.screen.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.example.ourbook.data.repository.OurBookRepository
import com.example.ourbook.data.remote.OurBookRepositoryRemote
import com.example.ourbook.data.model.RewardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val repository: OurBookRepositoryRemote = OurBookRepositoryRemote
) : ViewModel() {

    private val _uiState = MutableStateFlow(RewardsUiState())
    val uiState: StateFlow<RewardsUiState> = _uiState

    init {
        observeUser()
        loadRewards()
    }

    // atualiza a porcaria das moedas
    private fun observeUser() {
        viewModelScope.launch {
            repository.currentUser.collectLatest { user ->
                _uiState.update {
                    it.copy(coins = user?.coins ?: 0)
                }
            }
        }
    }

    // carrega as recompensas
    private fun loadRewards() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val rewards = repository.getRewards()
                _uiState.update {
                    it.copy(rewards = rewards, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Erro ao carregar recompensas", isLoading = false)
                }
            }
        }
    }
    fun redeem(rewardId: String) {
        viewModelScope.launch {
            try {
                repository.redeemReward(rewardId)
                loadRewards() // recarrega apenas as recompensas
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Erro ao resgatar recompensa")
                }
            }
        }
    }
}
