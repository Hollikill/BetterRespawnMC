package net.tetramc.amnesia.mixin;

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
import net.tetramc.amnesia.SpawnSetCallback;

@Mixin({ServerPlayerEntity.class})
public class SpawnSetMixin {
    
    @Inject(method = "setSpawnPoint", at = {@At(value = "INVOKE", ordinal = 0)}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void setSpawnPoint(RegistryKey<World> dimension, @Nullable BlockPos pos, float angle, boolean spawnPointSet, boolean bl, CallbackInfo info) {
        Boolean result = ((SpawnSetCallback)SpawnSetCallback.EVENT.invoker()).interact(spawnPointSet);
        if (result == false)
            info.cancel();
        
        /*if (pos != null) {
            boolean bl2 = pos.equals(Local.valueOf("spawnPointPosition")) && dimension.equals(this.spawnPointDimension);
            if (bl && !bl2) {
                this.sendSystemMessage(new TranslatableText("block.minecraft.set_spawn"), Util.NIL_UUID);
            }
            
            this.spawnPointPosition = pos;
            this.spawnPointDimension = dimension;
            this.spawnAngle = angle;
            this.spawnPointSet = spawnPointSet;
        } else {
            this.spawnPointPosition = null;
            this.spawnPointDimension = World.OVERWORLD;
            this.spawnAngle = 0.0F;
            this.spawnPointSet = false;
        }*/
        
    }
    
    /*@Inject(method = {"setSpawnPoint"}, at = {@At(value = "INVOKE", ordinal = 0)}, cancellable = true)
    public void onSleep(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<Boolean> info) {
        ActionResult result = ((BedUseCallback)BedUseCallback.EVENT.invoker()).interact(player);
        if (result == ActionResult.FAIL)
        info.cancel();
    }*/
}