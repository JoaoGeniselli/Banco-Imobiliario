package com.dosei.games.toybank.analytics.di

import com.dosei.games.toybank.analytics.Analytics
import com.dosei.games.toybank.analytics.impl.LogCatAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AnalyticsModule {

    @Provides
    fun providesAnalytics(): Analytics = LogCatAnalytics
}