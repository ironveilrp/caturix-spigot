/*
 *  caturix-spigot, an adaptation of Caturix for Spigot plugins.
 *  Copyright (C)  2018-2023 Martijn Heil
 *
 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the
 *  Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.fablesfantasyrp.caturix.spigot.common

import com.fablesfantasyrp.caturix.spigot.common.provider.FixedSuggestionsProvider
import com.fablesfantasyrp.caturix.parametric.AbstractModule
import com.fablesfantasyrp.caturix.parametric.Injector

class FixedSuggestionsModule(private val injector: Injector) : AbstractModule() {
    override fun configure() {
        bind(Boolean::class.javaObjectType).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Boolean::class.javaObjectType)!!))
        bind(Int::class.javaObjectType).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Int::class.javaObjectType)!!))
        bind(Short::class.javaObjectType).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Short::class.javaObjectType)!!))
        bind(Double::class.javaObjectType).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Double::class.javaObjectType)!!))
        bind(Float::class.javaObjectType).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Float::class.javaObjectType)!!))
        bind(String::class.javaObjectType).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(String::class.javaObjectType)!!))

        bind(Boolean::class.java).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Boolean::class.java)!!))
        bind(Int::class.java).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Int::class.java)!!))
        bind(Short::class.java).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Short::class.java)!!))
        bind(Double::class.java).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Double::class.java)!!))
        bind(Float::class.java).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(Float::class.java)!!))
        bind(String::class.java).annotatedWith(FixedSuggestions::class.java).toProvider(FixedSuggestionsProvider(injector.getProvider(String::class.java)!!))
    }
}
