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

package com.ironveilrp.caturix.spigot.common

import com.ironveilrp.caturix.Command
import com.ironveilrp.caturix.CommandCallable
import com.ironveilrp.caturix.dispatcher.Dispatcher
import com.ironveilrp.caturix.parametric.annotation.Optional
import org.bukkit.command.CommandSender


class CommonCommands {
    private lateinit var dispatcher: Dispatcher

    @Command(aliases = [ "help", "?" ], desc = "Show common help", usage = "[common=overview]")
    fun help(@Sender sender: CommandSender, @Optional callable: CommandCallable?) {
        if (callable != null) {
			sendCommandHelp(callable, sender)
        } else {
			sendCommandHelp(dispatcher, sender)
        }
    }

    /**
     * This is a bit hacky, CommonCommands depends on the dispatcher it belongs to, but when we *have* to register
     * the CommonCommands, the dispatcher is not initialized yet. So we have to do late-initialization as you can see below
     *
     * @param dispatcher The dispatcher this belongs to.
     */
    fun lateInit(dispatcher: Dispatcher) {
        this.dispatcher = dispatcher
    }
}
