package io.github.jason13official.new_shield_variants.impl.common.shield;

import io.github.jason13official.new_shield_variants.impl.common.shield.projectile.ThrownFireCharge;
import io.github.jason13official.new_shield_variants.impl.common.shield.projectile.ThrownTNT;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;

public class ShieldUseInteractions {

  public static void handle(Item shield, ShieldVariant variant, ServerPlayer player, ItemStack stack,
      InteractionHand hand) {

    if (variant == ShieldVariant.BLAZE) {
      ShieldUseInteractions.useBlazeShield(shield, player.serverLevel(), player);
    } else if (variant == ShieldVariant.DRAGON_HEAD) {
      ShieldUseInteractions.useDragonHeadShield(shield, player);
    } else if (variant == ShieldVariant.FIRE_CHARGE) {
      ShieldUseInteractions.useFireChargeShield(shield, player.serverLevel(), player, hand);
    } else if (variant == ShieldVariant.TNT) {
      ShieldUseInteractions.useTntShield(shield, player.serverLevel(), player, hand);
    }
  }

  private static void useBlazeShield(Item shield, ServerLevel level, ServerPlayer player) {
    List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class,
        player.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));

    AtomicBoolean ignited = new AtomicBoolean(false);
    list.forEach(entity -> {

      boolean ignites = false;

      // instead of many if statements, we stop checking after the first one sets ignite to true
      if (entity instanceof Monster) {
        ignites = true;
      } else if (entity == player.getLastHurtByMob()) {
        ignites = true;
      } else if (entity.getLastHurtMob() == player) {
        ignites = true;
      } else if (entity.canAttack(player)) {
        ignites = true;
      } else if (entity.attackable() && (entity instanceof Player other && other.getTeam() != player.getTeam())) {
        ignites = true;
      }

      // make sure we don't ignite ourselves...
      if (entity == player) {
        ignites = false;
      }

      if (ignites) {
        if (!entity.isOnFire()) {
          // entity.setSecondsOnFire(2);
          entity.setRemainingFireTicks(2 * 20);
        }
        ignited.set(true);
      }
    });

    if (ignited.get()) {
      player.getCooldowns().addCooldown(shield, 100);
    }
  }

  private static void useDragonHeadShield(Item shield, Player player) {
    List<LivingEntity> list = player.level()
        .getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
    AreaEffectCloud areaeffectcloud = new AreaEffectCloud(player.level(), player.xOld, player.yOld, player.zOld);

    areaeffectcloud.setOwner(player);

    areaeffectcloud.setParticle(ParticleTypes.DRAGON_BREATH);
    areaeffectcloud.setRadius(3.0F);
    areaeffectcloud.setDuration(100);
    areaeffectcloud.setRadiusPerTick((7.0F - areaeffectcloud.getRadius()) / (float) areaeffectcloud.getDuration());
    areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 5));
    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 50, 2));

    LivingEntity lastHurtBy = player.getLastHurtByMob();
    if (!list.isEmpty() || lastHurtBy != null) {
      if (lastHurtBy != null) {
        areaeffectcloud.setPos(lastHurtBy.getX(), lastHurtBy.getY(), lastHurtBy.getZ());
      } else {
        for (LivingEntity livingentity : list) {
          double d0 = player.distanceToSqr(livingentity);
          if (d0 < 16.0D) {
            areaeffectcloud.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            break;
          }
        }
      }
    }

    player.level()
        .levelEvent(LevelEvent.PARTICLES_DRAGON_FIREBALL_SPLASH, player.blockPosition(), player.isSilent() ? -1 : 1);
    player.level().addFreshEntity(areaeffectcloud);

    player.getCooldowns().addCooldown(shield, 100);
  }

  private static void useFireChargeShield(Item shield, Level level, Player player, InteractionHand hand) {
    ItemStack itemstack = player.getItemInHand(hand);

    if (!level.isClientSide) {

      ThrownFireCharge throwntnt = new ThrownFireCharge(level, player);
      throwntnt.setItem(Items.FIRE_CHARGE.getDefaultInstance());
      throwntnt.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
      level.addFreshEntity(throwntnt);
    }
    itemstack.setDamageValue(itemstack.getDamageValue() - 20);

    player.getCooldowns().addCooldown(shield, 20);
  }

  private static void useTntShield(Item shield, Level level, Player player, InteractionHand hand) {
    ItemStack itemstack = player.getItemInHand(hand);

    if (!level.isClientSide) {

      ThrownTNT throwntnt = new ThrownTNT(level, player);
      throwntnt.setItem(Items.TNT.getDefaultInstance());
      throwntnt.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
      level.addFreshEntity(throwntnt);
    }

    itemstack.setDamageValue(itemstack.getDamageValue() - 20);

    player.getCooldowns().addCooldown(shield, 60);
  }
}
