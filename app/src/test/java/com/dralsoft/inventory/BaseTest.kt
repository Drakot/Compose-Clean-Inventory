package com.dralsoft.inventory

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
abstract class BaseTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun baseSetup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun baseTearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}