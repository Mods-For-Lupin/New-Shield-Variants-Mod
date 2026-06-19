package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.NewShieldVariants;
import io.github.jason13official.new_shield_variants.impl.common.shield.projectile.ThrownFireChargeFabric;
import io.github.jason13official.new_shield_variants.impl.common.shield.projectile.ThrownTNTFabric;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

  public static EntityType<ThrownTNTFabric> THROWN_TNT;
  public static EntityType<ThrownFireChargeFabric> THROWN_FIRE_CHARGE;

  public static void register(BiConsumer<EntityType<?>, ResourceLocation> consumer) {

    THROWN_TNT = EntityType.Builder.<ThrownTNTFabric>of(ThrownTNTFabric::new, MobCategory.MISC)
        .build("new_shield_variants:ignored_thrown_tnt");
    THROWN_FIRE_CHARGE = EntityType.Builder.<ThrownFireChargeFabric>of(ThrownFireChargeFabric::new, MobCategory.MISC)
        .build("new_shield_variants:ignored_thrown_fire_charge");

    consumer.accept(THROWN_TNT, NewShieldVariants.identifier("ignored_thrown_tnt"));
    consumer.accept(THROWN_FIRE_CHARGE, NewShieldVariants.identifier("ignored_thrown_fire_charge"));
  }
}
