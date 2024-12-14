package com.cso.nearby.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cso.nearby.core.network.NearbyRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MarketDetailsUiState())
    val uiState: StateFlow<MarketDetailsUiState> = _uiState.asStateFlow()

    fun onEvent(event: MarketDetailsUiEvent) {
        when (event) {
            is MarketDetailsUiEvent.OnFetchRules -> fetchRules(event.marketId)
            is MarketDetailsUiEvent.OnFetchCoupon -> fetchCoupon(event.qrCodeContent)
            is MarketDetailsUiEvent.OnResetCoupon -> resetCoupon()
        }
    }


    private fun fetchRules(marketId: String) {
        viewModelScope.launch {
            NearbyRemoteDataSource.getMarketDetails(marketId).fold(
                onSuccess = { marketDetails ->
                    _uiState.update { currentUiState ->
                        currentUiState.copy(rules = marketDetails.rules)
                    }
                },
                onFailure = {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(rules = emptyList())
                    }
                }
            )
        }
    }

    private fun fetchCoupon(qrCodeContent: String) {
        viewModelScope.launch {
            NearbyRemoteDataSource.patchCoupon(qrCodeContent).fold(
                onSuccess = { coupon ->
                    _uiState.update { currentUiState ->
                        currentUiState.copy(coupon = coupon.coupon)
                    }
                },
                onFailure = {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(coupon = "")
                    }
                }
            )
        }
    }

    private fun resetCoupon() {
        _uiState.update { currentUiState ->
            currentUiState.copy(coupon = null)
        }
    }

}