package com.mckapp.newsappcomse.presentation.onboarding

sealed class OnBoardingEvent {
    data object SaveAppEntry : OnBoardingEvent()
}