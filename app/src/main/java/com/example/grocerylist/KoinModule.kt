package com.example.grocerylist

import com.example.grocerylist.checkout.CheckoutViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appKoinModule = module {
    viewModelOf(::CheckoutViewModel)
}
