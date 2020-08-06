package com.example.myapplication.tools.contacts

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.example.myapplication.models.Contact
import com.example.myapplication.tools.interfaces.IContactProvider


class ContactProvider(private val mApplicationContext: Context) : IContactProvider{
    override fun getContactList(): List<Contact> {
        val result = mutableListOf<Contact>()

        val cr: ContentResolver = mApplicationContext.contentResolver
        val cur: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        if ((cur?.count ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val id: String? = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name: String? = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (id.isNullOrEmpty() || name.isNullOrEmpty())
                    continue

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    pCur?.let { cursor ->
                        while (cursor.moveToNext()) {
                            val phoneNo: String = cursor.getString(
                                cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            val contactUri: Uri =
                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
                            result.add(Contact(name, phoneNo, contactUri))

                        }

                    }

                    pCur?.close()
                }
            }
        }
        cur?.close()
        return result
    }
}