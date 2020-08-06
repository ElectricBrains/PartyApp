package com.example.myapplication.tools.interfaces

import com.example.myapplication.models.Place
import com.example.myapplication.models.PlaceCoordinates

//needs for first setup and fill json
interface IPlaceResolver {
    suspend fun resolve(place: Place) : PlaceCoordinates?
}