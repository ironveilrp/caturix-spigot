# caturix-spigot [![](https://jitpack.io/v/com.github.fablesfantasyrp/caturix-spigot.svg)](https://jitpack.io/#com.github.fablesfantasyrp/caturix-spigot)
An adaptation of [Caturix](https://github.com/fablesfantasyrp/caturix) for Spigot.

A basic example (written in [Kotlin](https://kotlinlang.org/)):
```kotlin
import com.fablesfantasyrp.caturix.spigot.common.CommonModule
import com.fablesfantasyrp.caturix.spigot.common.CommandTarget
import com.fablesfantasyrp.caturix.spigot.common.Toggle
import com.fablesfantasyrp.caturix.spigot.common.bukkit.BukkitAuthorizer
import com.fablesfantasyrp.caturix.spigot.common.bukkit.provider.BukkitModule
import com.fablesfantasyrp.caturix.spigot.common.bukkit.provider.sender.BukkitSenderModule
import com.fablesfantasyrp.caturix.spigot.common.bukkit.registerCommand
import com.fablesfantasyrp.caturix.Intake
import com.fablesfantasyrp.caturix.fluent.CommandGraph
import com.fablesfantasyrp.caturix.parametric.ParametricBuilder
import com.fablesfantasyrp.caturix.parametric.provider.PrimitivesModule
import com.fablesfantasyrp.caturix.Command
import com.fablesfantasyrp.caturix.Require
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MyPlugin : JavaPlugin() {
    override fun onEnable() {
        val injector = Intake.createInjector()
        injector.install(PrimitivesModule())
        injector.install(BukkitModule(server))
        injector.install(BukkitSenderModule())
        injector.install(CommonModule())

        val builder = ParametricBuilder(injector)
        builder.authorizer = BukkitAuthorizer()

        val dispatcher = CommandGraph()
                .builder(builder)
                .commands()
                .registerMethods(MyCommands())
                .graph()
                .dispatcher

        registerCommand(dispatcher, this, dispatcher.aliases.toList())
    }
}

class MyCommands {
    @Command(aliases = ["mycommand", "mc"], desc = "Toggle my command")
    @Require("mypermission")
    fun mycommand(@Toggle value: Boolean, @CommandTarget("mycommand.others") target: Player) {
        // value is a boolean, but instead of the usual true/false,
        // it is called using on/off in the command, by the @Toggle
        // so you call this command like `mycommand on` or `mycommand off`
        //
        // The @CommandTarget target: Player with the associated permission will
        // be the command sender himself, or optionally, if the command sender has the
        // associated permission *and* has specified another player,
        // the specified player. Like as follows:
        // `mycommand on SomePlayername`, `mycommand off SomePlayername`
        //
        // for more in-depth explanation and examples of how to use Intake in
        // general, of course, see the Intake documentation.
        // ...
    }
}
```
