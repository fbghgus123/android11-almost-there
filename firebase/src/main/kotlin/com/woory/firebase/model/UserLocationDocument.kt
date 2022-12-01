package com.woory.firebase.model

import com.google.firebase.firestore.GeoPoint
import com.woory.firebase.util.TimeConverter.asMillis
import org.threeten.bp.OffsetDateTime

data class UserLocationDocument(
    val id: String = "",
    val location: GeoPoint = GeoPoint(0.0, 0.0),
    val updatedAt: Long = OffsetDateTime.now().asMillis()
)