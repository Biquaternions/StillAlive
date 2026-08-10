package me.biquaternions.stillalive.listener;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import lombok.RequiredArgsConstructor;
import me.biquaternions.stillalive.manager.TickWatchdog;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
@RequiredArgsConstructor
public class ServerListener implements Listener {

    private final TickWatchdog watchdog;

    @EventHandler
    public void onServerTickEnd(final ServerTickEndEvent event) {
        this.watchdog.setLastTickMillis(System.currentTimeMillis());
    }

    @EventHandler
    public void onServerLoad(final ServerLoadEvent event) {
        this.watchdog.setReady(true);
    }

}
