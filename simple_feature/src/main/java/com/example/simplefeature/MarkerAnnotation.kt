package com.example.simplefeature

import kotlin.reflect.KClass

annotation class MarkerAnnotation(
    val clazz: KClass<*>
)
