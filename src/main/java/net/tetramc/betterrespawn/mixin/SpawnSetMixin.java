package net.tetramc.betterrespawn.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.tetramc.betterrespawn.SpawnSetCallback;

@Mixin({ServerPlayerEntity.class})
public class SpawnSetMixin {
    
    @Inject(method = "setSpawnPoint", at = {@At(value = "INVOKE", ordinal = 0)}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void setSpawnPoint(RegistryKey<World> dimension, @Nullable BlockPos pos, float angle, boolean spawnPointSet, boolean bl, CallbackInfo info) {
        Boolean result = ((SpawnSetCallback)SpawnSetCallback.EVENT.invoker()).interact(spawnPointSet);
        if (result == false)
        info.cancel();
    }
}