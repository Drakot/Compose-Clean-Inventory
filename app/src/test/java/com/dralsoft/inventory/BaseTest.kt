package com.dralsoft.inventory

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
abstract class BaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var testDispatcher = MainCoroutineRule()

    @Before
    fun baseSetup() {
        // Dispatchers.setMain(testDispatcher)
    }

    @After
    fun baseTearDown() {
        Dispatchers.resetMain()
        //  testDispatcher.scheduler.cancel()
        // mainThreadSurrogate.close()
    }
}


@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

public suspend fun <T> Flow<T>.testFlow(
    scope: TestScope,
    onFinish: suspend ReceiveTurbine<T>.() -> Unit,
) {
    test {

        scope.advanceTimeBy(1000)
        onFinish()

        cancelAndConsumeRemainingEvents()
    }
}