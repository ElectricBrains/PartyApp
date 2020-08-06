package com.example.myapplication.repositories

import android.content.Context
import com.example.myapplication.models.Contact
import com.example.myapplication.models.Party
import com.example.myapplication.models.Place
import com.example.myapplication.models.dto.StoragePartyInfo
import com.example.myapplication.repositories.interfaces.IPartyRepository
import com.example.myapplication.tools.interfaces.IContactProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class JSonPartyRepository (private val mApplicationContext: Context, private val mContactProvider: IContactProvider): IPartyRepository {
    private val mCashedData: MutableList<Party> = mutableListOf()

    override suspend fun getAvailableParties(): List<Party> {
        if (mCashedData.isEmpty())
            refreshCachedDataData()
        return mCashedData
    }

    override suspend fun getPartyById(id: Int): Party {
        return mCashedData.first {
            party -> party.id == id
        }
    }

    @Synchronized
    private fun refreshCachedDataData() {
        mCashedData.clear()
        try {
            val contactList = mContactProvider.getContactList()
            val jsonString = mApplicationContext.assets.open("places.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<StoragePartyInfo>>() {}.type
            val partyStorageInfo: List<StoragePartyInfo> = Gson().fromJson(jsonString, type)
            mCashedData.addAll(partyStorageInfo.map {
                var partyOwner: Contact? = null
                val sameVisitorsContacts = mutableListOf<Contact>()

                if (contactList.isNotEmpty()) {
                    partyOwner = contactList.random()
                    val countOfSameVisitors = (0..2).random()
                    for (i in 0 until countOfSameVisitors) {
                        sameVisitorsContacts.add(contactList.random())
                    }
                }


                return@map Party(System.currentTimeMillis().toInt(),
                    it.eventName,
                    it.imageUrl,
                    partyOwner,
                    sameVisitorsContacts,
                    Place(it.addressName ,it.address )
                )
            })


        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
            return
        }
    }
}