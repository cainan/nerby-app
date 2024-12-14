package com.cso.nearby.ui.screen.market_details

import com.cso.nearby.data.model.Rule

data class MarketDetailsUiState(
    val rules: List<Rule>? = null,
    val coupon: String? = null,
)
