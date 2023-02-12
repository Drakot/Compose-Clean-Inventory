package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.core.ui.UiSingleEvent

sealed class ListUiSingleEvent : UiSingleEvent {

    data class OpenDetailScreen(val navRoute: String) : ListUiSingleEvent()
}