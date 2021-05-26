package net.tetramc.betterrespawn;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface SpawnSetCallback {
    public static final Event<SpawnSetCallback> EVENT = EventFactory.createArrayBacked(SpawnSetCallback.class, (listeners) -> (allowedSet) -> {
        for (SpawnSetCallback listener : listeners) {
            listener.interact(allowedSet);
        }
        if (allowedSet != true)
            return allowedSet;
        return true;
    });
    Boolean interact(Boolean allowedSet);
}