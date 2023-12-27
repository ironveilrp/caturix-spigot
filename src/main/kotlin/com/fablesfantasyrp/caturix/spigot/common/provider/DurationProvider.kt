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

package com.fablesfantasyrp.caturix.spigot.common.provider

import com.fablesfantasyrp.caturix.argument.ArgumentException
import com.fablesfantasyrp.caturix.argument.CommandArgs
import com.fablesfantasyrp.caturix.argument.Namespace
import com.fablesfantasyrp.caturix.parametric.Provider
import com.fablesfantasyrp.caturix.parametric.ProvisionException
import java.time.Duration
import kotlin.time.toJavaDuration

class DurationProvider : Provider<Duration> {
	override val isProvided = false

	@Throws(ArgumentException::class, ProvisionException::class)
	@OptIn(kotlin.time.ExperimentalTime::class)
	override fun get(arguments: CommandArgs, modifiers: List<Annotation>)
		= kotlin.time.Duration.parse(arguments.next()).toJavaDuration()

	override fun getSuggestions(prefix: String, locals: Namespace, modifiers: List<Annotation>): List<String>
		= emptyList()
}
