package io.github.jason13official.new_shield_variants.impl.common.shield;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ShieldTickInteractions {

  public static void handle(ShieldVariant variant, ServerPlayer player) {

    if (variant == ShieldVariant.ENDER) {
      tickEnderShield(player.level(), player);
    } else if (variant == ShieldVariant.SHULKER) {
      tickShulkerShield(player);
    } else if (variant == ShieldVariant.REDSTONE) {
      tickRedstoneShield(player);
    }
  }

  private static void tickShulkerShield(Player player) {
    if (player.getMainHandItem().is(ModItems.SHULKER_SHIELD) || player.getOffhandItem().is(ModItems.SHULKER_SHIELD)) {
      player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) (player.getRandom().nextDouble() * 40)));
      player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (player.getRandom().nextDouble() * 160)));
    }
  }

  private static void tickEnderShield(ServerLevel level, Player player) {

    if (player.getMainHandItem().is(ModItems.ENDER_SHIELD) || player.getOffhandItem().is(ModItems.ENDER_SHIELD)) {

      LivingEntity lastAttacker = player.getLastHurtByMob();
      List<LivingEntity> nearbyLivingEntities = level.getNearbyEntities(LivingEntity.class,
          TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(3));
      LivingEntity targetForTeleportation =
          lastAttacker == null && !nearbyLivingEntities.isEmpty() ? nearbyLivingEntities.get(0) : lastAttacker;

      if (targetForTeleportation != null && randomTeleport(targetForTeleportation)) {
        player.stopUsingItem();
        player.setLastHurtByMob(null);

        player.getCooldowns().addCooldown(player.getItemInHand(player.getUsedItemHand()), 60);
      }
    }
  }

  public static boolean randomTeleport(LivingEntity livingEntity) {

    Level level = livingEntity.level();

    double randomXPosRange = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * 16.0;
    double randomYPosRange = Mth.clamp(livingEntity.getY() + (double) (livingEntity.getRandom().nextInt(16) - 8),
        level.getMinY(), level.getMinY() + ((ServerLevel) level).getLogicalHeight() - 1);
    double randomZPosRange = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * 16.0;
    if (livingEntity.isPassenger()) {
      livingEntity.stopRiding();
    }

    Vec3 entityPos = livingEntity.position();
    if (livingEntity.randomTeleport(randomXPosRange, randomYPosRange, randomZPosRange, true)) {
      level.gameEvent(GameEvent.TELEPORT, entityPos, GameEvent.Context.of(livingEntity));
      level.playSound(null, BlockPos.containing(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()),
          SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL);
      livingEntity.resetFallDistance();
      return true;
    } else {
      level.gameEvent(GameEvent.TELEPORT, entityPos, GameEvent.Context.of(livingEntity));
      level.playSound(null, BlockPos.containing(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()),
          SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL);
      livingEntity.resetFallDistance();
      return false;
    }

  }

  private static void tickRedstoneShield(ServerPlayer player) {
    RedstoneSignalTracker.update(player.getUUID(), player.blockPosition(), player.level());
  }
}
