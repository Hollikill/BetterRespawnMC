package net.tetramc.betterrespawn.mixin;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tetramc.betterrespawn.BedUseCallback;
import net.minecraft.entity.player.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BedBlock.class})
public class BedUseMixin {
  @Inject(at = {@At(value = "INVOKE", ordinal = 0)}, method = {"onUse"}, cancellable = true)
  public void onSleep(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<Boolean> info) {
    ActionResult result = ((BedUseCallback)BedUseCallback.EVENT.invoker()).interact(player);
    if (result == ActionResult.FAIL)
        info.cancel();
  }
}