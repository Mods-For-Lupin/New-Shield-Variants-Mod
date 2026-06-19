package io.github.jason13official.new_shield_variants.mixin;

import io.github.jason13official.new_shield_variants.impl.common.shield.RedstoneSignalTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class SignalGetterMixin {

  @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
  private void new_shield_variants$getSignal(BlockGetter level, BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir) {
    if (level instanceof Level lvl && !lvl.isClientSide && RedstoneSignalTracker.isActive(pos)) {
      cir.setReturnValue(15);
    }
  }

}
