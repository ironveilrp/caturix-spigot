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

package com.ironveilrp.caturix.spigot.common.bukkit

import com.ironveilrp.caturix.CommandCallable
import kotlinx.coroutines.CoroutineScope
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.plugin.Plugin

private fun getBukkitCommandMap(): CommandMap {
    val bukkitCommandMap = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
    bukkitCommandMap.isAccessible = true
    return bukkitCommandMap.get(Bukkit.getServer()) as CommandMap
}

fun registerCommand(cmd: CommandCallable,
                    plugin: Plugin,
                    aliases: List<String>,
                    launch: (block: suspend CoroutineScope.() -> Unit) -> Unit): Command? {
    try {
        val commandMap = getBukkitCommandMap()
        val fallbackPrefix = plugin.name.lowercase()
        val command = RootCommand(launch, cmd, aliases, fallbackPrefix)
        commandMap.register(fallbackPrefix, command)
        return command
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    }
    return null
}

fun unregisterCommand(command: Command): Boolean {
    try {
        val commandMap = getBukkitCommandMap()
        return command.unregister(commandMap)
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    }
    return false
}
