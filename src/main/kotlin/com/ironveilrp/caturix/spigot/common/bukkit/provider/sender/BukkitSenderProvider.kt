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

package com.ironveilrp.caturix.spigot.common.bukkit.provider.sender

import com.ironveilrp.caturix.argument.ArgumentException
import com.ironveilrp.caturix.argument.CommandArgs
import com.ironveilrp.caturix.argument.Namespace
import com.ironveilrp.caturix.parametric.Provider
import com.ironveilrp.caturix.parametric.ProvisionException
import org.bukkit.command.CommandSender

class BukkitSenderProvider<T: CommandSender>(val clazz: Class<T>) : Provider<T> {
    override val isProvided = true

    @Throws(ArgumentException::class, ProvisionException::class)
    override suspend fun get(commandArgs: CommandArgs, list: List<Annotation>): T {
        val namespace = commandArgs.namespace
        val senderClass = namespace.get("senderClass") as? Class<*>
                ?: throw ProvisionException("Sender's class was not set on namespace")

        if (!namespace.containsKey("sender")) {
            throw ProvisionException("Sender was not set on namespace")
        }

        val rawSender = namespace.get("sender")
        // ! rawSender instanceof clazz
        if (!clazz.isInstance(rawSender)) {
            throw InvalidSenderTypeException(senderClass.simpleName, clazz.simpleName)
        }

        val sender = namespace.get("sender") as T? ?:
            throw ProvisionException("Sender was set on namespace, but is null")

        return sender
    }


    override suspend fun getSuggestions(prefix: String, locals: Namespace, modifiers: List<Annotation>): List<String> {
        return emptyList()
    }
}
