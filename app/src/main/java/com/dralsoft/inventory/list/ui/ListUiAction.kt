package com.dralsoft.inventory.list.ui

import androidx.compose.foundation.interaction.Interaction
import com.dralsoft.inventory.core.ui.UiAction

sealed class ListUiAction : UiAction {
    object Load : ListUiAction()
    data class UserClick(val userId: Long, val interaction: Interaction) : ListUiAction()
    data class PostClick(val postId: Long, val interaction: Interaction) : ListUiAction()
}