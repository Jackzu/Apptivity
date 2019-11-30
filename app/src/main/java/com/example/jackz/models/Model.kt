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

    val LocationsTime = listOf(
        Location("10-15 Uhr"),
        Location("7-22 Uhr"),
        Location("24/7"),
        Location("7-22 Uhr"),
        Location("7-22 Uhr"),
        Location("7-22 Uhr"),
        Location("7-22 Uhr"),
        Location("7-22 Uhr")
    )

    val LocationsPicture = listOf(
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds"),
        Location("@drawable/mcdonalds")
    )

}