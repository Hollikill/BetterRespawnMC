package net.tetramc.betterrespawn;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface BedUseCallback {
  public static final Event<BedUseCallback> EVENT = EventFactory.createArrayBacked(BedUseCallback.class, (listeners) -> (player) -> {
    for (BedUseCallback listener : listeners) {
      ActionResult result = listener.interact(player);
      if(result != ActionResult.PASS) {
        return result;
      }
    }
    return ActionResult.FAIL;
  });
  ActionResult interact(PlayerEntity player);
}