package net.tetramc.amnesia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.server.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class NoSleep implements ModInitializer {
  public static final String MOD_ID = "dontsleep";
  
  public ServerWorld world;
  
  public ServerPlayerEntity player2;
  
  public MinecraftServer server;
  
  public PlayerManager pm;
  
  public void onInitialize() {
    ServerWorldEvents.LOAD.register((minecraftServer, ServerWorld) -> {
          this.world = ServerWorld;
          this.server = minecraftServer;
          this.pm = this.server.getPlayerManager();
        });
    BedUseCallback.EVENT.register((player) -> {
          this.player2 = this.pm.getPlayer(player.getUuidAsString());
          if (this.player2.world.getRegistryKey() == ClientWorld.OVERWORLD) {
            player2.sendSystemMessage((Text)new LiteralText("Sleep is elusive, "+player2.getPlayerListName()+"."), player2.getUuid());
            world.setTimeOfDay(0L);
            return ActionResult.FAIL;
          } 
          return ActionResult.FAIL;
        });
  }
}
