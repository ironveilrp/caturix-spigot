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

import com.ironveilrp.caturix.parametric.AbstractModule
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import com.ironveilrp.caturix.spigot.common.Sender

class BukkitSenderModule : AbstractModule() {

    override fun configure() {
        bind(CommandSender::class.java).annotatedWith(Sender::class.java)
            .toProvider(BukkitSenderProvider(CommandSender::class.java))

        bind(ConsoleCommandSender::class.java).annotatedWith(Sender::class.java)
            .toProvider(BukkitSenderProvider(ConsoleCommandSender::class.java))

        bind(BlockCommandSender::class.java).annotatedWith(Sender::class.java)
            .toProvider(BukkitSenderProvider(BlockCommandSender::class.java))

        bind(Player::class.java).annotatedWith(Sender::class.java)
            .toProvider(BukkitSenderProvider(Player::class.java))
    }
}
