package me.biquaternions.stillalive.concurrent;

import lombok.RequiredArgsConstructor;
import me.biquaternions.stillalive.manager.TickWatchdog;
import me.biquaternions.stillalive.misc.Constants;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@NullMarked
@RequiredArgsConstructor
public class KeepAliveSender implements Runnable {

    private static final Method KEEP_CONNECTION_ALIVE;

    static {
        try {
            Method method = ServerCommonPacketListenerImpl.class.getDeclaredMethod("keepConnectionAlive");
            method.setAccessible(true);

            KEEP_CONNECTION_ALIVE = method;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private final JavaPlugin plugin;
    private final TickWatchdog watchdog;

    private long tick = 0;

    @Override
    public void run() {
        if (!this.watchdog.isReady()) {
            return;
        }
        if (!this.watchdog.isStalled()) {
            return;
        }

        if (++this.tick % Constants.TICKS_PER_SECOND == 0) {
            this.plugin.getSLF4JLogger().warn("Server stalled, sending keepalive packets asynchronously...");
        }

        try {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                final CraftPlayer craftPlayer = (CraftPlayer) player;
                KEEP_CONNECTION_ALIVE.invoke(craftPlayer.getHandle().connection);
            }
        } catch (InvocationTargetException | IllegalAccessException exception) {
            throw new IllegalStateException(exception);
        }
    }

}
