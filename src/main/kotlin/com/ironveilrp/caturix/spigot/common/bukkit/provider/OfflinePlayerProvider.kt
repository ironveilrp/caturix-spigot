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
import com.ironveilrp.caturix.parametric.Provider
import com.ironveilrp.caturix.parametric.ProvisionException
import com.ironveilrp.caturix.spigot.common.CommandTarget
import com.ironveilrp.caturix.argument.Namespace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permissible
import java.util.UUID

class OfflinePlayerProvider(private val server: Server) : Provider<OfflinePlayer> {

    override val isProvided = false


    @Throws(ArgumentException::class, ProvisionException::class)
    override suspend fun get(arguments: CommandArgs, modifiers: List<Annotation>): OfflinePlayer {
        val sender = arguments.namespace.get("sender") as CommandSender
        var targetAnnotation: com.ironveilrp.caturix.spigot.common.CommandTarget? = null

        for (annotation in modifiers) {
            if (annotation is com.ironveilrp.caturix.spigot.common.CommandTarget) {
                targetAnnotation = annotation
                break
            }
        }

        var p: OfflinePlayer? = null

        if (arguments.hasNext()) {
            val arg = arguments.next()

            p = if (arg.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}".toRegex())) {
                server.getOfflinePlayer(UUID.fromString(arg))
            } else {
                withContext(Dispatchers.IO) { @Suppress("DEPRECATION") server.getOfflinePlayer(arg) }
            }
        } else if (targetAnnotation != null && sender is OfflinePlayer) {
            p = sender
        } else {
            // Generate MissingArgumentException
            arguments.next()
        }

        if (p == null || (!p.hasPlayedBefore() && !p.isOnline)) {
            throw ArgumentParseException("Player not found")
        }

        if (targetAnnotation != null &&
                p != sender &&
                !arguments.namespace.get(Permissible::class.java)!!.hasPermission(targetAnnotation.value)) {
            throw ArgumentParseException("You need " + targetAnnotation.value)
        }

        return p
    }


    override suspend fun getSuggestions(prefix: String, locals: Namespace, modifiers: List<Annotation>): List<String> {
        return server.onlinePlayers.map { it.name }.filter { it.lowercase().startsWith(prefix.lowercase()) }
    }
}
