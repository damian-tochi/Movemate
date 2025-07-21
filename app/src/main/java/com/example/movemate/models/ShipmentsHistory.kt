package com.example.movemate.models

data class ShipmentsHistory(
    val status: String,
    val title: String,
    val trackId: String,
    val from: String,
    val to: String,
    val amount: String,
    val date: String
)
