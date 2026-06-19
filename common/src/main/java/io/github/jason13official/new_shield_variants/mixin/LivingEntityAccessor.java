package io.github.jason13official.new_shield_variants.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

  @Invoker(value = "isDamageSourceBlocked")
  boolean new_shield_variants$blockedByShield(DamageSource source);

  @Invoker(value = "hurtCurrentlyUsedShield")
  void new_shield_variants$damageShield(float amount);

  @Invoker(value = "blockUsingShield")
  void new_shield_variants$takeShieldHit(LivingEntity attacker);
}
