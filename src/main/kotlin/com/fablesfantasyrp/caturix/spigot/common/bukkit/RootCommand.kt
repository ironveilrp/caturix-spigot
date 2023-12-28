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

package com.fablesfantasyrp.caturix.spigot.common.bukkit

import com.fablesfantasyrp.caturix.CommandCallable
import com.fablesfantasyrp.caturix.CommandException
import com.fablesfantasyrp.caturix.InvocationCommandException
import com.fablesfantasyrp.caturix.argument.Namespace
import com.fablesfantasyrp.caturix.parametric.ProvisionException
import com.fablesfantasyrp.caturix.util.auth.AuthorizationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Entity
import org.bukkit.permissions.Permissible
import kotlin.coroutines.CoroutineContext

class RootCommand(private val launch: (block: suspend CoroutineScope.() -> Unit) -> Unit,
                  private val callable: CommandCallable,
                  aliases: List<String>,
                  private val fallbackPrefix: String) :
        BukkitCommand(aliases[0], callable.description?.shortDescription ?: "", callable.description?.usage ?: "", aliases) {

    init {
        this.description = callable.description?.shortDescription ?: ""
    }

    override fun execute(sender: CommandSender, cmd: String, args: Array<String>): Boolean {
        val namespace = constructNamespace(sender, null)
        val arguments = args.joinToString(" ")

        launch {
            try {
                callable.call(arguments, namespace, emptyList())
            } catch (cx: CommandException) {
                sendError(sender, cx.message ?: "Unknown error.")
            } catch (cx: AuthorizationException) {
                sendError(sender, cx.message ?: "Permission denied.")
            } catch (icx: InvocationCommandException) {
                when (icx.cause) {
                    is AuthorizationException -> { sendError(sender, icx.cause!!.message ?: "Permission denied.") }
                    is CommandException -> { sendError(sender, icx.cause!!.message ?: "Unknown error.") }
                    is ProvisionException -> { sendError(sender, icx.cause!!.message ?: "Unknown error") }
                    else -> {
                        sendError(sender, "An internal server error occurred")
                        icx.printStackTrace()
                    }
                }
            } catch (ex: Exception) {
                sendError(sender, "An internal server error occurred")
                ex.printStackTrace()
            }
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>, location: Location?): List<String> {
        return runBlocking { callable.getSuggestions(args.joinToString(" "), constructNamespace(sender, location)) }
    }

    private fun sendError(p: CommandSender, message: String) {
        p.sendMessage(ChatColor.RED.toString() + "Error: " + ChatColor.DARK_RED + message)
    }

    private fun constructNamespace(sender: CommandSender, location: Location?): Namespace {
        var loc: Location? = location
        if (loc == null && sender is Entity) loc = sender.location

        val namespace = Namespace()
        namespace.put("sender", sender)
        namespace.put("senderClass", sender.javaClass)
        namespace.put(Permissible::class.java, sender)
        namespace.put("senderLocation", loc)

        return namespace
    }
}
