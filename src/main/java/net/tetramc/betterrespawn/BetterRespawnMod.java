package net.tetramc.betterrespawn;

import java.util.List;

import com.oroarmor.config.*;

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
import net.minecraft.util.Formatting;
import net.tetramc.betterrespawn.config.BetterRespawnConfig;

public class BetterRespawnMod implements ModInitializer {
    public static final String MOD_ID = "betterrespawn";
    
    public static Config CONFIG = new BetterRespawnConfig();

    List<ConfigItem<?>> configs = CONFIG.getConfigs().get(0).getConfigs();

    public MinecraftClient client;
    public ServerWorld world;
    public MinecraftServer server;
    public PlayerManager pm;
    
    public void onInitialize() {
        // global varible gathering
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraftClient) -> {
            this.client = minecraftClient;
        });
        ServerWorldEvents.LOAD.register((minecraftServer, ServerWorld) -> {
            this.server = minecraftServer;
            this.world = ServerWorld;
            this.pm = this.server.getPlayerManager();
        });
        
        // Mixin implementation
        SpawnSetCallback.EVENT.register((allowedSet) ->{
            // init debug message text
            LiteralText debug = new LiteralText("spawnpoint");

            if ((boolean)configs.get(0).getValue() == true) {
                if (allowedSet)
                    addText(debug, new LiteralText(" saved"), Formatting.GREEN);
                else
                    addText(debug, new LiteralText(" cleared"), Formatting.RED);
                return (boolean)configs.get(1).getValue();
            };

            // add debug message contents
            if (allowedSet)
                addText(debug, new LiteralText(" saved"), Formatting.GREEN);
            else
                addText(debug, new LiteralText(" cleared"), Formatting.RED);
            // debug message broadcast to all
            pm.broadcastChatMessage(debug, MessageType.GAME_INFO, PlayerEntity.getOfflinePlayerUuid("hollikill"));

            if (allowedSet)
                return false;
            return true;
        });
    }

    //just a simple shortcut function
    public void addText(MutableText baseText, MutableText appText, Formatting formatting) {
        baseText.append(appText.setStyle(Style.EMPTY.withFormatting(formatting)));
        return;
    }
}
