package net.tetramc.amnesia;

import java.util.UUID;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;

public class NoBedSpawn implements ModInitializer {
    public static final String MOD_ID = "Amnesia";
    
    public MinecraftClient client;
    public ServerWorld world;
    public MinecraftServer server;
    public PlayerManager pm;
    
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraftClient) -> {
            this.client = minecraftClient;
        });
        ServerWorldEvents.LOAD.register((minecraftServer, ServerWorld) -> {
            this.server = minecraftServer;
            this.world = ServerWorld;
            this.pm = this.server.getPlayerManager();
        });
        
        SpawnSetCallback.EVENT.register((allowedSet) ->{
            // init debug message text
            LiteralText debug = new LiteralText("");
            // add debug message contents
            addText(debug, new LiteralText("Interrupted setSpawn()      "), Formatting.RESET);
            if (allowedSet)
                addText(debug, new LiteralText("Ran method"), Formatting.GREEN);
            else
                addText(debug, new LiteralText("Prevented run"), Formatting.RED);
            // debug message broadcast to all
            pm.broadcastChatMessage(debug, MessageType.GAME_INFO, PlayerEntity.getOfflinePlayerUuid("hollikill"));

            if (allowedSet)
                return false;
            return true;
        });

        /*BedUseCallback.EVENT.register((player) -> {
            
            // init debug message text
            LiteralText debug = new LiteralText("");

            // add debug message contents
            addText(debug, new LiteralText(player.getUuidAsString()), Formatting.GRAY);
            LiteralText playerCount = new LiteralText("\n"+server.getCurrentPlayerCount());
            if (server.getCurrentPlayerCount() == 1) addText(debug, playerCount, Formatting.GREEN);
            else addText(debug, playerCount, Formatting.RED);
            LiteralText sleptLongEnough = new LiteralText("\n"+player.isSleepingLongEnough());
            if (server.getCurrentPlayerCount() == 1) addText(debug, sleptLongEnough, Formatting.DARK_GREEN);
            else addText(debug, sleptLongEnough, Formatting.DARK_RED);
            addText(debug, new LiteralText("\n"+((World)world).getTimeOfDay()%24000), Formatting.YELLOW);
            
            // debug message send
            player.sendMessage(debug, false);
            
            // actual sleep ability check
            if (player.isSleepingLongEnough() && server.getCurrentPlayerCount() == 1) {
                ((ServerWorldProperties)player.world.getLevelProperties()).setTimeOfDay(((World)world).getTimeOfDay()%24000);
            }
            
            return ActionResult.FAIL;
        });*/
    }

    public void addText(MutableText baseText, MutableText appText, Formatting formatting) {
        baseText.append(appText.setStyle(Style.EMPTY.withFormatting(formatting)));
        return;
    }
}
