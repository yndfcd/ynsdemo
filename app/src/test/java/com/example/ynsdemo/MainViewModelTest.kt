package com.example.ynsdemo

import MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Test
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel // Make sure MainViewModel is initialized correctly

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun testUpdateNameIntent() = runTest {
        val newName = "Alice"
        viewModel.processIntent(MainIntent.UpdateName(newName))
        val state = viewModel.state.first()
        assertEquals(newName, state.name)
    }

    @Test
    fun testSayHelloIntent() = runTest {
        val name = "Bob"
        viewModel.processIntent(MainIntent.UpdateName(name))
        viewModel.processIntent(MainIntent.SayHello)
        val state = viewModel.state.first()
        assertEquals("Hello Bob!", state.greeting)
    }
}