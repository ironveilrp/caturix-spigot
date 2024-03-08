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

package com.fablesfantasyrp.caturix.spigot.common.bukkit.provider

import com.fablesfantasyrp.caturix.argument.ArgumentParseException
import com.fablesfantasyrp.caturix.argument.CommandArgs
import com.fablesfantasyrp.caturix.argument.Namespace
import com.fablesfantasyrp.caturix.parametric.Provider
import org.bukkit.Color

class ColorProvider : Provider<Color> {
	override val isProvided: Boolean = false

	private val colors = mapOf(
		Pair("AQUA", Color.AQUA),
		Pair("BLACK", Color.BLACK),
		Pair("BLUE", Color.BLUE),
		Pair("FUCHSIA", Color.FUCHSIA),
		Pair("GRAY", Color.GRAY),
		Pair("GREEN", Color.GREEN),
		Pair("LIME", Color.LIME),
		Pair("MAROON", Color.MAROON),
		Pair("NAVY", Color.NAVY),
		Pair("OLIVE", Color.OLIVE),
		Pair("ORANGE", Color.ORANGE),
		Pair("PURPLE", Color.PURPLE),
		Pair("RED", Color.RED),
		Pair("SILVER", Color.SILVER),
		Pair("TEAL", Color.TEAL),
		Pair("WHITE", Color.WHITE),
		Pair("YELLOW", Color.YELLOW)
	)

	override suspend fun get(arguments: CommandArgs, modifiers: List<Annotation>): Color {
		val arg = arguments.next()

		if (arg.startsWith("#")) {
			val value = arg.removePrefix("#").toIntOrNull(16)
				?: throw ArgumentParseException("Could not parse hex color.")
			return Color.fromRGB(value)
		} else {
			return colors[arg.uppercase()] ?: throw ArgumentParseException("Unknown color '$arg'")
		}
	}

	override suspend fun getSuggestions(prefix: String, locals: Namespace, modifiers: List<Annotation>): List<String>
		= colors.keys.toList()
}
