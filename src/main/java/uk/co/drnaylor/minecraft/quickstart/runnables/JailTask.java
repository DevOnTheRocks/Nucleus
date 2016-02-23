/*
 * This file is part of QuickStart, licensed under the MIT License (MIT). See the LICENCE.txt file
 * at the root of this project for more details.
 */
package uk.co.drnaylor.minecraft.quickstart.runnables;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import uk.co.drnaylor.minecraft.quickstart.QuickStart;
import uk.co.drnaylor.minecraft.quickstart.Util;
import uk.co.drnaylor.minecraft.quickstart.api.PluginModule;
import uk.co.drnaylor.minecraft.quickstart.internal.TaskBase;
import uk.co.drnaylor.minecraft.quickstart.internal.annotations.Modules;
import uk.co.drnaylor.minecraft.quickstart.internal.services.UserConfigLoader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;

@Modules(PluginModule.JAILS)
public class JailTask implements TaskBase {
    @Inject
    private QuickStart plugin;

    @Override
    public void accept(Task task) {
        Collection<Player> pl = Sponge.getServer().getOnlinePlayers();
        UserConfigLoader ucl = plugin.getUserLoader();
        pl.stream().map(x -> {
            try {
                return ucl.getUser(x);
            } catch (IOException | ObjectMappingException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(x -> x == null || x.getJailData().isPresent()).forEach(x -> Util.testForEndTimestamp(x.getJailData(), () -> plugin.getJailHandler().unjailPlayer(x.getUser())));
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public int secondsPerRun() {
        return 2;
    }
}