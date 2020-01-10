package com.example.jackz.models

class ResultData(val results: List<Results>)

class Results(val geometry: Geometry, val icon: String, val id: String, val name: String, val photos: List<PhotoData>, val place_id: String, val reference: String, val scope: String, val types: List<String>, val vicinity: String)

class Geometry(val location: Coordinates, val viewport: Viewport)

class Coordinates(val lat: Double, val lng: Double)
class Viewport(val northeast: Coordinates, val southwest: Coordinates)

class PhotoData(val height: Int, val html_attributions: List<String>, val photo_reference: String, val width :Int)