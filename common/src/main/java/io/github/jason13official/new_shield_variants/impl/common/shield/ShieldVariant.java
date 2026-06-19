package io.github.jason13official.new_shield_variants.impl.common.shield;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record ShieldVariant(TagKey<Item> repairItems) {

  public static ShieldVariant STONE = new ShieldVariant(ModItemTags.STONE_SHIELD_MATERIALS);
  public static ShieldVariant IRON = new ShieldVariant(ModItemTags.IRON_SHIELD_MATERIALS);
  public static ShieldVariant GOLD = new ShieldVariant(ModItemTags.GOLD_SHIELD_MATERIALS);
  public static ShieldVariant DIAMOND = new ShieldVariant(ModItemTags.DIAMOND_SHIELD_MATERIALS);
  public static ShieldVariant NETHERITE = new ShieldVariant(ModItemTags.NETHERITE_SHIELD_MATERIALS);

  public static ShieldVariant ENDER = new ShieldVariant(ModItemTags.ENDER_SHIELD_MATERIALS);
  public static ShieldVariant BLAZE = new ShieldVariant(ModItemTags.BLAZE_SHIELD_MATERIALS);
  public static ShieldVariant FIRE_CHARGE = new ShieldVariant(ModItemTags.FIRE_CHARGE_SHIELD_MATERIALS);
  public static ShieldVariant SHULKER = new ShieldVariant(ModItemTags.SHULKER_SHIELD_MATERIALS);
  public static ShieldVariant DRAGON_HEAD = new ShieldVariant(ModItemTags.DRAGON_HEAD_SHIELD_MATERIALS);
  public static ShieldVariant TNT = new ShieldVariant(ModItemTags.TNT_SHIELD_MATERIALS);
  public static ShieldVariant REDSTONE = new ShieldVariant(ModItemTags.REDSTONE_SHIELD_MATERIALS);
}
