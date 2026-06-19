package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

  public static final TagKey<Item> STONE_SHIELD_MATERIALS = bind(Constants.MOD_ID, "stone_shield_materials");
  public static final TagKey<Item> IRON_SHIELD_MATERIALS = bind(Constants.MOD_ID, "iron_shield_materials");
  public static final TagKey<Item> GOLD_SHIELD_MATERIALS = bind(Constants.MOD_ID, "gold_shield_materials");
  public static final TagKey<Item> DIAMOND_SHIELD_MATERIALS = bind(Constants.MOD_ID, "diamond_shield_materials");
  public static final TagKey<Item> NETHERITE_SHIELD_MATERIALS = bind(Constants.MOD_ID, "netherite_shield_materials");

  public static final TagKey<Item> ENDER_SHIELD_MATERIALS = bind(Constants.MOD_ID, "ender_shield_materials");
  public static final TagKey<Item> BLAZE_SHIELD_MATERIALS = bind(Constants.MOD_ID, "blaze_shield_materials");
  public static final TagKey<Item> FIRE_CHARGE_SHIELD_MATERIALS = bind(Constants.MOD_ID, "fire_charge_shield_materials");
  public static final TagKey<Item> SHULKER_SHIELD_MATERIALS = bind(Constants.MOD_ID, "shulker_shield_materials");
  public static final TagKey<Item> DRAGON_HEAD_SHIELD_MATERIALS = bind(Constants.MOD_ID, "dragon_head_shield_materials");
  public static final TagKey<Item> TNT_SHIELD_MATERIALS = bind(Constants.MOD_ID, "tnt_shield_materials");
  public static final TagKey<Item> REDSTONE_SHIELD_MATERIALS = bind(Constants.MOD_ID, "redstone_shield_materials");

  public static TagKey<Item> bind(final String namespace, final String path) {
    return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, path));
  }
}
