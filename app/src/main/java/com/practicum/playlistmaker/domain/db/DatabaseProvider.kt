package com.practicum.playlistmaker.domain.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object DatabaseProvider {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val instance: DatabaseMock = DatabaseMock(scope)
}
