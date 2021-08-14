package com.chepsi.survey.app.data.db

import io.realm.Realm
import java.lang.reflect.Modifier

fun isRealmInitialized(): Boolean {
    val current: Class<*> = Realm::class.java
    for (field in current.declaredFields) {
        if (!Modifier.isStatic(field.modifiers)) {
            continue
        }

        if (field.name == "defaultConfiguration") {
            field.isAccessible = true
            val isInitialized = field.get(current) != null
            field.isAccessible = false
            return isInitialized
        }
    }

    return false
}