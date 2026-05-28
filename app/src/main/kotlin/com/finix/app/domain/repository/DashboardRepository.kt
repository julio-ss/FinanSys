package com.finix.app.domain.repository

import com.finix.app.domain.model.Dashboard
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getDashboard(userId: String): Flow<Dashboard>
    suspend fun refreshDashboard(userId: String): Result<Dashboard>
}
