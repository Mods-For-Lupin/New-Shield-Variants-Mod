package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.NewShieldVariants;
import io.github.jason13official.new_shield_variants.impl.common.shield.projectile.ThrownFireCharge;
import io.github.jason13official.new_shield_variants.impl.common.shield.projectile.ThrownTNT;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

  public static EntityType<ThrownTNT> THROWN_TNT;
  public static EntityType<ThrownFireCharge> THROWN_FIRE_CHARGE;

  public static void register(BiConsumer<EntityType<?>, Identifier> consumer) {

    THROWN_TNT = EntityType.Builder.<ThrownTNT>of(ThrownTNT::new, MobCategory.MISC)
        .build(ResourceKey.create(Registries.ENTITY_TYPE, NewShieldVariants.identifier("ignored_thrown_tnt")));
    THROWN_FIRE_CHARGE = EntityType.Builder.<ThrownFireCharge>of(ThrownFireCharge::new, MobCategory.MISC)
        .build(ResourceKey.create(Registries.ENTITY_TYPE, NewShieldVariants.identifier("ignored_thrown_fire_charge")));

    consumer.accept(THROWN_TNT, NewShieldVariants.identifier("ignored_thrown_tnt"));
    consumer.accept(THROWN_FIRE_CHARGE, NewShieldVariants.identifier("ignored_thrown_fire_charge"));
  }
}
