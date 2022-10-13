package com.paradoxo.hifood.sampleData

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.paradoxo.hifood.model.Produto
import java.math.BigDecimal

val loremIpsum100 = LoremIpsum(100).values.first()

val sampleDataProduct = listOf(
    Produto(
        "01",
        "Uvas",
        loremIpsum100,
        BigDecimal(42.00),
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ46c5O3LQEA0JF7KzWjk0bscVjg60tYmBMFg&usqp=CAU"
    ), Produto(
        "02",
        "Morangos",
        loremIpsum100,
        BigDecimal(51),
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ46c5O3LQEA0JF7KzWjk0bscVjg60tYmBMFg&usqp=CAU"
    )
)