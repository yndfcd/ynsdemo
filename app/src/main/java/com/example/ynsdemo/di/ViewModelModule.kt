package com.example.ynsdemo.di

import com.example.ynsdemo.cart.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CartViewModel(get())
    }
}
