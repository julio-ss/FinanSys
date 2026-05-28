package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val userId: String,
    val name: String,
    val icon: String,
    val color: String,
    val type: String,
    val isCustom: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

object DefaultCategories {
    fun getExpenseCategories() = listOf(
        Category("1", "", "Alimentação", "🍔", "#EF4444", "expense"),
        Category("2", "", "Transporte", "🚗", "#3B82F6", "expense"),
        Category("3", "", "Saúde", "🏥", "#10B981", "expense"),
        Category("4", "", "Lazer", "🎮", "#F59E0B", "expense"),
        Category("5", "", "Educação", "📚", "#8B5CF6", "expense"),
        Category("6", "", "Compras", "🛍️", "#EC4899", "expense"),
        Category("7", "", "Assinaturas", "📺", "#06B6D4", "expense"),
        Category("8", "", "Utilidades", "🔌", "#14B8A6", "expense")
    )

    fun getIncomeCategories() = listOf(
        Category("20", "", "Salário", "💼", "#10B981", "income"),
        Category("21", "", "Freelance", "💻", "#6366F1", "income"),
        Category("22", "", "Investimentos", "📈", "#F59E0B", "income"),
        Category("23", "", "Bônus", "🎁", "#EC4899", "income"),
        Category("24", "", "Outros", "📝", "#64748B", "income")
    )
}
