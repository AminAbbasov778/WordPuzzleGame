package com.example.wordsapp.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> createNavType(): NavType<T> = object : NavType<T>(false) {
    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { Json.decodeFromString<T>(it) }

    override fun parseValue(value: String): T =
        Json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: T): String =
        Uri.encode(Json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(value))
    }
}

