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

package com.ironveilrp.caturix.spigot.common.bukkit.provider

import com.ironveilrp.caturix.argument.ArgumentException
import com.ironveilrp.caturix.argument.ArgumentParseException
import com.ironveilrp.caturix.argument.CommandArgs
import com.ironveilrp.caturix.argument.Namespace
import com.ironveilrp.caturix.parametric.Provider
import com.ironveilrp.caturix.parametric.ProvisionException
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PlayerProvider(private val server: Server, private val offlinePlayerProvider: Provider<OfflinePlayer>) : Provider<Player> {

    override val isProvided = false

    @Throws(ArgumentException::class, ProvisionException::class)
    override suspend fun get(arguments: CommandArgs, modifiers: List<Annotation>): Player {
        val sender = arguments.namespace.get("sender") as CommandSender

        // Vanilla command target selector
        if (arguments.hasNext() && arguments.peek()!!.startsWith("@")) {
            try {
                return server.selectEntities(sender, arguments.next()).filterIsInstance<Player>().firstOrNull()
                    ?: throw ArgumentParseException("No player found")
            } catch (ex: IllegalArgumentException) { throw ArgumentParseException(ex.message) }
        }

        val op = offlinePlayerProvider.get(arguments, modifiers)
        if (op.player != null) {
            if (!op.isOnline) throw ArgumentParseException("Player not found")

            return op.player!!
        } else {
            throw ArgumentParseException("Player is not online")
        }
    }

    override suspend fun getSuggestions(prefix: String, locals: Namespace, modifiers: List<Annotation>): List<String> {
        // TODO UUID suggestions?
        val matches = server.matchPlayer(prefix)
        val suggestions = ArrayList<String>()
        matches.forEach { match -> suggestions.add(match.name) }
        return suggestions
    }
}
