package com.dralsoft.inventory.core.ui.mvi

import android.os.Build
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.domain.ValidationResult
import com.dralsoft.inventory.detail.ui.LoadingState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber


abstract class AbstractMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> :
    MviViewModel<I, S, E>, ViewModel() {
    protected open val rawLogTag: String? = null

    private val _viewState: MutableStateFlow<S> by lazy {
        MutableStateFlow(initState())
    }
    override val viewState: StateFlow<S> = _viewState

    private val _viewLoadingState: MutableStateFlow<LoadingState> by lazy {
        MutableStateFlow(LoadingState(false))
    }
    override val viewLoadingState: StateFlow<LoadingState> = _viewLoadingState

    abstract override fun submitIntent(intent: I)
    abstract fun initState(): S

    init {
        submitState(initState())
    }

    protected val logTag by lazy(LazyThreadSafetyMode.PUBLICATION) {
        (rawLogTag ?: this::class.java.simpleName).let { tag: String ->
            // Tag length limit was removed in API 26.
            if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
                tag
            } else {
                tag.take(MAX_TAG_LENGTH)
            }
        }
    }

    private val eventChannel = Channel<E>(Channel.UNLIMITED)
    override val singleEvent: Flow<E> = eventChannel.receiveAsFlow()


    @CallSuper
    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
        Timber.tag(logTag).d("onCleared")
    }

    fun submitSingleEvent(event: E) {

        eventChannel.trySend(event)
            .onSuccess { Timber.tag(logTag).d("sendEvent: event=$event") }
            .onFailure {
                Timber
                    .tag(logTag)
                    .e(it, "Failed to send event: $event")
            }
            .getOrThrow()
    }

    fun submitStateScope(state: S) {
        viewModelScope.launch {
            _viewState.value = state
        }
    }

    fun submitState(state: S) {
        _viewState.value = state
    }

    fun submitLoadingState(state: LoadingState) {
        _viewLoadingState.value = state
    }

    fun showLoading(state: Boolean) {
        submitLoadingState(LoadingState(state))
    }

    fun formValidation(vararg fieldIsValid: Flow<ValidationResult>): Flow<ValidationResult> {
        val isValid: Flow<ValidationResult> = when {
            fieldIsValid.isEmpty() -> flowOf(ValidationResult(true)) // ensure at least one value emitted
            else -> combine(*fieldIsValid) { values ->
                ValidationResult(
                    values.all { item -> item.successful },
                    values.first().errorMessage
                )
            }
                .distinctUntilChanged()
        }
        return isValid
    }

    private companion object {
        private const val MAX_TAG_LENGTH = 23
    }
}
