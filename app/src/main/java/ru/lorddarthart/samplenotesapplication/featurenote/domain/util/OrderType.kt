package ru.lorddarthart.samplenotesapplication.featurenote.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
