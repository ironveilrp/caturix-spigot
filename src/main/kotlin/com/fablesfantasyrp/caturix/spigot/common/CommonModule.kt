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

import com.fablesfantasyrp.caturix.spigot.common.provider.DurationProvider
import com.fablesfantasyrp.caturix.spigot.common.provider.ToggleProvider
import com.fablesfantasyrp.caturix.parametric.AbstractModule
import java.time.Duration

class CommonModule : AbstractModule() {

    override fun configure() {
        bind(Boolean::class.javaObjectType).annotatedWith(Toggle::class.java).toProvider(ToggleProvider())
        bind(Boolean::class.java).annotatedWith(Toggle::class.java).toProvider(ToggleProvider())
        bind(Duration::class.java).toProvider(DurationProvider())
    }
}
