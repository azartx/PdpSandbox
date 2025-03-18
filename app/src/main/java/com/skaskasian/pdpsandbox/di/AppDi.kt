package com.skaskasian.pdpsandbox.di

import kotlin.reflect.KClass

val SCOPE_SHARED = "SCOPE_SHARED"

typealias Scope = Any
typealias Key = KClass<out Any>
typealias Dependency = Any
typealias Dependencies = HashMap<Key, Lazy<Dependency>>

/**
 * Simple manual di with one single shared scope and 'n' feature scopes
 */
object AppDi {

    private val store = hashMapOf<Scope, Dependencies>()

    fun init(dependencies: Dependencies) {
        store.clear()
        store[SCOPE_SHARED] = dependencies
    }

    fun initScope(scope: Scope, dependencies: Dependencies) {
        store[scope] = dependencies
    }

    fun closeScope(scope: Scope) {
        store.remove(scope)
    }

    fun getDependency(scope: Scope, dependencyKey: Key): Dependency {
        return store[scope]?.get(dependencyKey)
            ?: let { store[SCOPE_SHARED]?.get(dependencyKey) }
            ?: throw IllegalArgumentException("Dependency not found")
    }
}

interface Injectable {

    fun getScope(): Scope {
        return SCOPE_SHARED
    }
}

inline fun <reified Dep : Any> Injectable.inject(): Lazy<Dep> {
    return AppDi.getDependency(getScope(), Dep::class) as Lazy<Dep>
}

inline fun <reified Dep : Any> Injectable.get(): Dep {
    return (AppDi.getDependency(getScope(), Dep::class) as Lazy<Dep>).value
}