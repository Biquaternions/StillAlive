package me.biquaternions.stillalive;

import me.biquaternions.stillalive.concurrent.KeepAliveSender;
import me.biquaternions.stillalive.listener.ServerListener;
import me.biquaternions.stillalive.manager.TickWatchdog;
import me.biquaternions.stillalive.misc.Constants;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@NullMarked
public final class StillAlive extends JavaPlugin {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(task -> {
        final Thread thread = new Thread(task);
        thread.setName("Asynchronous KeepAlive Thread");
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
    });

    private final TickWatchdog watchdog = new TickWatchdog();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ServerListener(this.watchdog), this);
        this.scheduler.scheduleAtFixedRate(new KeepAliveSender(this, this.watchdog),
                0, Constants.TICK_DURATION_MILLIS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDisable() {
        this.watchdog.setReady(false);
        this.scheduler.shutdown();
    }

}
