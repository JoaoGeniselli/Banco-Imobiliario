package com.dosei.games.toybank.commons.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dosei.games.toybank.analytics.Analytics

@Composable
fun rememberAnalytics() = remember { Analytics.getInstance() }