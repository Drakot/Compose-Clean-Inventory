package com.dralsoft.inventory.core.ui

import android.os.Build
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.BuildConfig
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private fun debugCheckMainThread() {
    if (BuildConfig.DEBUG) {
        check(Looper.getMainLooper() === Looper.myLooper()) {
            "Expected to be called on the main thread but was " + Thread.currentThread().name
        }
    }
}

abstract class AbstractMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> :
    MviViewModel<I, S, E>, ViewModel() {
    protected open val rawLogTag: String? = null

    private val _viewState: MutableStateFlow<S> by lazy {
        MutableStateFlow(initState())
    }
    override val viewState: StateFlow<S> = _viewState

    abstract override fun submitIntent(intent: I)
    abstract fun initState(): S

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

    protected fun submitSingleEvent(event: E) {
        debugCheckMainThread()

        eventChannel.trySend(event)
            .onSuccess { Timber.tag(logTag).d("sendEvent: event=$event") }
            .onFailure {
                Timber
                    .tag(logTag)
                    .e(it, "Failed to send event: $event")
            }
            .getOrThrow()
    }

    fun submitState(state: S) {
        viewModelScope.launch {
            _viewState.value = state
        }
    }

    private companion object {
        private const val MAX_TAG_LENGTH = 23
    }
}
