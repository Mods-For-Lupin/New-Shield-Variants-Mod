package io.github.jason13official.new_shield_variants.impl.common.shield.projectile;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModEntities;
import io.github.jason13official.new_shield_variants.platform.Services;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownTNTFabric extends ThrowableItemProjectile {

  public ThrownTNTFabric(EntityType<? extends ThrowableItemProjectile> type, Level level) {
    super(type, level);
  }

  public ThrownTNTFabric(Level level, LivingEntity owner) {
    super(ModEntities.THROWN_TNT, owner, level);
    setItem(Items.TNT.getDefaultInstance());
    this.setItemSlot(EquipmentSlot.MAINHAND, Items.TNT.getDefaultInstance());
    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println("created ThrownTNTFabric instance");
    }
  }

  public ThrownTNTFabric(Level level, double x, double y, double z) {
    super(ModEntities.THROWN_TNT, x, y, z, level);
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),
        PartPose.offset(0.0F, 0, 0));

    return LayerDefinition.create(meshdefinition, 128, 128);
  }

  @Override
  public void handleEntityEvent(byte eventFlag) {
    if (eventFlag == EntityEvent.DEATH) {
      double mod = 0.08;

      for (int i = 0; i < 8; ++i) {
        this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, Items.FIRE_CHARGE.getDefaultInstance()),
            this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - (double) 0.5F) * mod,
            ((double) this.random.nextFloat() - (double) 0.5F) * mod,
            ((double) this.random.nextFloat() - (double) 0.5F) * mod);
      }
    }
  }

  @Override
  protected void onHitEntity(EntityHitResult result) {
    super.onHitEntity(result);
    result.getEntity().setRemainingFireTicks(4 * 20);
  }

  @Override
  protected void onHit(HitResult hitResult) {
    super.onHit(hitResult);
    if (!this.level().isClientSide()) {
      Level level = this.level();
      BlockPos blockpos = this.blockPosition().relative(this.getDirection());
      PrimedTnt primedtnt = new PrimedTnt(level, (double) blockpos.getX() + (double) 0.5F, blockpos.getY(),
          (double) blockpos.getZ() + (double) 0.5F, null);
      level.addFreshEntity(primedtnt);
      level.playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED,
          SoundSource.BLOCKS, 1.0F, 1.0F);
      level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
      this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
      this.discard();
    }
  }

  @Override
  protected Item getDefaultItem() {
    return Items.TNT;
  }
}

