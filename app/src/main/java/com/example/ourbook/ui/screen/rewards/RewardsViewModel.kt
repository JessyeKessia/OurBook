package com.example.ourbook.ui.screen.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.OurBookRepository
import com.example.ourbook.data.model.RewardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RewardsUiState(
    val coins: Int = 0,
    val rewards: List<RewardItem> = emptyList()
)

class RewardsViewModel(
    private val repository: OurBookRepository = OurBookRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RewardsUiState())
    val uiState: StateFlow<RewardsUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.currentUser.collectLatest { user ->
                _uiState.update { it.copy(coins = user?.coins ?: 0) }
            }
        }
        viewModelScope.launch {
            repository.rewards.collectLatest { rewards ->
                _uiState.update { it.copy(rewards = rewards) }
            }
        }
    }

    fun redeem(rewardId: String) {
        viewModelScope.launch {
            repository.redeemReward(rewardId)
        }
    }
}
