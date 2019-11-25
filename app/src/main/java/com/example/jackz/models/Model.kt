package com.example.jackz.models

data class Location(var title: String)

object Supplier{
    val Locations = listOf(
        Location("McDonalds"),
        Location("Burger King"),
        Location("KFC"),
        Location("Döner"),
        Location("Chinesisch"),
        Location("Kaufland"),
        Location("Lidl"),
        Location("Kik")
    )
}