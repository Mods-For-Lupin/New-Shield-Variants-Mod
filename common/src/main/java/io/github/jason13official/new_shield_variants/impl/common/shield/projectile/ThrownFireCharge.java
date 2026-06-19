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
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownFireCharge extends ThrowableItemProjectile {

  public ThrownFireCharge(EntityType<? extends ThrowableItemProjectile> type, Level level) {
    super(type, level);
  }

  public ThrownFireCharge(Level level, LivingEntity owner) {
    super(ModEntities.THROWN_FIRE_CHARGE, owner, level, Items.FIRE_CHARGE.getDefaultInstance());
    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println("created ThrownFireChargeFabric instance");
    }
  }

  public ThrownFireCharge(Level level, double x, double y, double z) {
    super(ModEntities.THROWN_FIRE_CHARGE, x, y, z, level, Items.FIRE_CHARGE.getDefaultInstance());
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
        this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(Items.FIRE_CHARGE.getDefaultInstance())),
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

      BlockPos blockpos = this.blockPosition().relative(this.getDirection());
      if (BaseFireBlock.canBePlacedAt(this.level(), blockpos, this.getDirection())) {
        BlockState blockstate = BaseFireBlock.getState(this.level(), blockpos);
        this.level().setBlock(blockpos, blockstate, BaseFireBlock.UPDATE_ALL_IMMEDIATE);
      }

      this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
      this.discard();
    }
  }

  @Override
  protected Item getDefaultItem() {
    return Items.FIRE_CHARGE;
  }
}

