package com.example.myapplication.tools.map

import android.content.Context
import android.location.Geocoder
import com.example.myapplication.models.Place
import com.example.myapplication.models.PlaceCoordinates
import com.example.myapplication.tools.interfaces.IPlaceResolver

//use application context
class PlaceResolver(private val mContext: Context): IPlaceResolver {

    override suspend fun resolve(place: Place): PlaceCoordinates? {
        val addresses = Geocoder(mContext).getFromLocationName(place.address, 1);
        if(addresses.size > 0) {
            return PlaceCoordinates(addresses[0].longitude, addresses[0].latitude)
        }
        return null
    }
}