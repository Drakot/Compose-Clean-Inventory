package com.dralsoft.inventory

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executors

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
abstract class BaseTest {

    val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun baseSetup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun baseTearDown() {
       Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
       // mainThreadSurrogate.close()
    }
}

class MainDispatcherRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) : TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}


abstract class CoroutineBasedTest {

    @get:Rule
    val coroutineScope = TestCoroutineScopeRule()

    class TestCoroutineScopeRule : TestWatcher() {

        lateinit var scope: CoroutineScope
        val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

        override fun starting(description: Description?) {
            super.starting(description)
            scope = CoroutineScope(Job() + mainThreadSurrogate)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            scope.cancel()
        }
    }

}
