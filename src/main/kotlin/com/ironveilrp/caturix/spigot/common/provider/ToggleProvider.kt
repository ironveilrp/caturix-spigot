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

package com.ironveilrp.caturix.spigot.common.provider


import com.ironveilrp.caturix.argument.ArgumentException
import com.ironveilrp.caturix.argument.ArgumentParseException
import com.ironveilrp.caturix.argument.CommandArgs
import com.ironveilrp.caturix.argument.Namespace
import com.ironveilrp.caturix.parametric.Provider
import com.ironveilrp.caturix.parametric.ProvisionException

/**
 * A boolean provider, but instead of using true/false, on/off
 */
class ToggleProvider : Provider<Boolean> {
    override val isProvided = false

    @Throws(ArgumentException::class, ProvisionException::class)
    override suspend fun get(arguments: CommandArgs, modifiers: List<Annotation>): Boolean {

        val activated: Boolean = when (val next = arguments.next()) {
            "on" -> true
            "off" -> false
            else -> throw ArgumentParseException("$next is not a valid toggle value. Expected on/off")
        }

        return activated
    }

    override suspend fun getSuggestions(prefix: String, locals: Namespace, modifiers: List<Annotation>): List<String>
        = listOf("off", "on")
}
