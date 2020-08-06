package com.example.myapplication.tools.databinding

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.myapplication.R
import com.example.myapplication.models.Contact
import com.example.myapplication.viewmodels.PartyViewModel
import com.example.myapplication.databinding.ContactLayoutBinding
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl", "error")
fun loadImage(view: ImageView, url: String, error: Drawable) {
    Picasso.get().load(url).error(error).into(view)
}

@BindingAdapter("imageContact")
fun loadImage(view: ImageView, contact: Contact?) {
    if (contact == null) {
        view.setImageDrawable(Utils.generateEmptyAvatar("?"))
        return
    }
    Picasso.get()
        .load(contact.avatar)
        .placeholder(Utils.generateEmptyAvatar(contact.name))
        .into(view)
}

@BindingAdapter("partyOwner")
fun loadOwner(view: TextView, contact: Contact?) {
    if (contact == null) {
        view.visibility == View.INVISIBLE
        return
    }
    val firstPart= view.context.getString(R.string.owner_decription)
    view.text = "$firstPart ${contact.name}"
}

@BindingAdapter("fillPartyFriends")
fun fillFriends(layout: LinearLayout?, party: PartyViewModel?) {
    layout?.removeAllViews()
    for (friend in party!!.additionalVisitors) {
        val inflater = LayoutInflater.from(layout!!.context)
        val binding = ContactLayoutBinding.inflate(inflater, layout, false)
        binding.contact = friend
        layout.addView(binding.root)
    }
}

object Utils {
    private val mTextDrawableBuilder = TextDrawable.builder()
    .beginConfig()
    .height(100)
    .width(100)
    .endConfig()
    private val mColorGenerator = ColorGenerator.MATERIAL

fun generateEmptyAvatar(name: String?): Drawable {
    val leters = generateAvatarText (name)
    return mTextDrawableBuilder.buildRound(generateAvatarText (name), mColorGenerator.getColor(leters))
}

private fun generateAvatarText(name: String?): String {
    name?.let {
        val nameParts = it.trim().split(" ")
        if (nameParts.isNotEmpty()) {
            if (nameParts[0].isNotEmpty())
                return nameParts[0][0].toString().toUpperCase()
        }
    }
    return ""
}
}