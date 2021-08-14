package org.mbukachi.data

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

fun JsonObject.content(key: String): String {
    return this[key]!!.jsonPrimitive.content
}

fun JsonObject.contentNullable(key: String): String? {
    return this[key]?.jsonPrimitive?.content
}