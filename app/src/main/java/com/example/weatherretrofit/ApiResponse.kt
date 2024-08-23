package com.example.weatherretrofit

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: String,
    val result: Result,
    val records: Records
)

@Serializable
data class Result(
    val resource_id: String,
    val fields: List<Field>
)

@Serializable
data class Field(
    val id: String,
    val type: String
)

@Serializable
data class Records(
    val location: List<Location>
)

@Serializable
data class Location(
    val station: Station,
    val stationObsTimes: StationObsTimes
)

@Serializable
data class Station(
    val StationID: String,
    val StationName: String,
    val StationNameEN: String,
    val StationAttribute: String
)

@Serializable
data class StationObsTimes(
    val stationObsTime: List<StationObsTime>
)

@Serializable
data class StationObsTime(
    val weatherElements: WeatherElements,
    val Date: String
)

@Serializable
data class WeatherElements(
    val Precipitation: String
)
