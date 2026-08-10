package me.biquaternions.stillalive.manager;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

public class TickWatchdog {

    private static final long STALLED_THRESHOLD = Duration.ofSeconds(2).toMillis();

    @Getter
    @Setter
    private volatile long lastTickMillis;

    @Getter
    @Setter
    private volatile boolean ready;

    public boolean isStalled() {
        return (System.currentTimeMillis() - this.lastTickMillis) > STALLED_THRESHOLD;
    }

}
