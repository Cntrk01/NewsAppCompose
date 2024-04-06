package com.mckapp.newsappcomse.domain.usecases.app_entry

import com.mckapp.newsappcomse.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}