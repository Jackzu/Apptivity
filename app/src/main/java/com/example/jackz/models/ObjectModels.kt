package com.example.jackz.models

class ResultData(val results: List<Results>)

class PlaceResultObject(val result: Results)

class Results(val geometry: Geometry, val icon: String, val id: String, val name: String, val photos: List<PhotoData>? = null, val place_id: String, val reference: String, val scope: String? = null, val types: List<String>? = null, val vicinity: String? = null)

class Geometry(val location: Coordinates? = null, val viewport: Viewport? = null)

class Coordinates(val lat: Double? = null, val lng: Double? = null)
class Viewport(val northeast: Coordinates? = null, val southwest: Coordinates? = null)

class PhotoData(val height: Int, val html_attributions: List<String>? = null, val photo_reference: String? = null, val width :Int? = null)