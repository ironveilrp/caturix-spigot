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

import com.ironveilrp.caturix.parametric.AbstractModule
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.bukkit.entity.Player

class BukkitModule(private val server: Server) : AbstractModule() {

    override fun configure() {
        bind(Server::class.java).toInstance(server)
        bind(Player::class.java).toProvider(PlayerProvider(server, OfflinePlayerProvider(server)))
        bind(OfflinePlayer::class.java).toProvider(OfflinePlayerProvider(server))
        bind(Material::class.java).toProvider(MaterialProvider())
        bind(Color::class.java).toProvider(ColorProvider())
    }
}
