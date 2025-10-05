package com.example.wordsapp.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.wordsapp.home.presentation.GameRouteUi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object NavType : NavType<GameRouteUi>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): GameRouteUi? {
        return bundle.getString(key)?.let { Json.decodeFromString<GameRouteUi>(it) }
    }

    override fun parseValue(value: String): GameRouteUi {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: GameRouteUi): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: GameRouteUi) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
