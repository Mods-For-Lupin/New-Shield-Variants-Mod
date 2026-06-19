package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.NewShieldVariants;
import io.github.jason13official.new_shield_variants.impl.common.item.NSVShieldItem;
import io.github.jason13official.new_shield_variants.impl.common.shield.ShieldVariant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.BlocksAttacks;
import oshi.util.tuples.Pair;

public class ModItems {

  public static final ArrayList<Item> ITEMS = new ArrayList<>();

  public static Item STONE_SHIELD;
  public static Item IRON_SHIELD;
  public static Item GOLD_SHIELD;
  public static Item DIAMOND_SHIELD;
  public static Item NETHERITE_SHIELD;

  public static Item ENDER_SHIELD;
  public static Item BLAZE_SHIELD;
  public static Item FIRE_CHARGE_SHIELD;
  public static Item SHULKER_SHIELD;
  public static Item DRAGON_HEAD_SHIELD;
  public static Item TNT_SHIELD;
  public static Item REDSTONE_SHIELD;

  public static void register(BiConsumer<Item, Identifier> consumer) {

    STONE_SHIELD = construct("stone_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.STONE, shieldProperties(ShieldVariant.STONE, 500)), consumer);
    IRON_SHIELD = construct("iron_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.IRON, shieldProperties(ShieldVariant.IRON, 1000)), consumer);
    GOLD_SHIELD = construct("gold_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.GOLD, shieldProperties(ShieldVariant.GOLD, 750)), consumer);
    DIAMOND_SHIELD = construct("diamond_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.DIAMOND, shieldProperties(ShieldVariant.DIAMOND, 1750)), consumer);
    NETHERITE_SHIELD = construct("netherite_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.NETHERITE, shieldProperties(ShieldVariant.NETHERITE, 2000)), consumer);

    ENDER_SHIELD = construct("ender_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.ENDER, shieldProperties(ShieldVariant.ENDER, 2250)), consumer);
    BLAZE_SHIELD = construct("blaze_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.BLAZE, shieldProperties(ShieldVariant.BLAZE, 2250)), consumer);
    FIRE_CHARGE_SHIELD = construct("fire_charge_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.FIRE_CHARGE, shieldProperties(ShieldVariant.FIRE_CHARGE, 2250)), consumer);
    SHULKER_SHIELD = construct("shulker_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.SHULKER, shieldProperties(ShieldVariant.SHULKER, 2250)), consumer);
    DRAGON_HEAD_SHIELD = construct("dragon_head_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.DRAGON_HEAD, shieldProperties(ShieldVariant.DRAGON_HEAD, 2250)), consumer);
    TNT_SHIELD = construct("tnt_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.TNT, shieldProperties(ShieldVariant.TNT, 2250)), consumer);
    REDSTONE_SHIELD = construct("redstone_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.REDSTONE, shieldProperties(ShieldVariant.REDSTONE, 2250)), consumer);
  }

  // mirrors vanilla Items.SHIELD's blocking setup, since ShieldItem itself no longer carries any blocking logic;
  // repair validity is now driven by the same per-variant component instead of an Item-level override
  private static Properties shieldProperties(ShieldVariant variant, int durability) {
    return new Properties()
        .durability(durability)
        .repairable(variant.repairItems())
        .equippableUnswappable(EquipmentSlot.OFFHAND)
        .delayedComponent(DataComponents.BLOCKS_ATTACKS, context -> new BlocksAttacks(
            0.25F,
            1.0F,
            List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
            new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
            Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
            Optional.of(SoundEvents.SHIELD_BLOCK),
            Optional.of(SoundEvents.SHIELD_BREAK)));
  }

  private static Item construct(String name, BiFunction<ShieldVariant, Properties, Item> constructor, Pair<ShieldVariant, Properties> properties,  BiConsumer<Item, Identifier> consumer) {

    // required registration logic

    Identifier id = NewShieldVariants.identifier(name);

    // setId required in this version of mc
    Item item = constructor.apply(properties.getA(), properties.getB().setId(ResourceKey.create(Registries.ITEM, id)));
    consumer.accept(item, id);

    // additional logic
    ITEMS.add(item);

    return item;
  }
}
