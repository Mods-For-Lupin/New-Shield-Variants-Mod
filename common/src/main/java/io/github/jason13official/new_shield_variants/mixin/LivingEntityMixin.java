package io.github.jason13official.new_shield_variants.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

  @Inject(at = @At(value = "TAIL"), method = "hurt")
  private void new_shield_variants$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (!entity.isInvulnerableTo(source) || !entity.level().isClientSide() || !entity.isDeadOrDying() || !(source.is(
        DamageTypeTags.IS_FIRE) && entity.hasEffect(MobEffects.FIRE_RESISTANCE))) {
      if (amount > 0.0F && ((LivingEntityAccessor) entity).new_shield_variants$blockedByShield(source)) {
        // Handle Shield
        ((LivingEntityAccessor) entity).new_shield_variants$damageShield(amount);
        if (!source.is(DamageTypeTags.IS_PROJECTILE)) {
          Entity sourceEntity = source.getEntity();

          if (sourceEntity instanceof LivingEntity) {
            ((LivingEntityAccessor) entity).new_shield_variants$takeShieldHit((LivingEntity) sourceEntity);
          }
        }
      }
    }
  }
}
