package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.NewShieldVariants;
import io.github.jason13official.new_shield_variants.impl.common.item.NSVShieldItem;
import io.github.jason13official.new_shield_variants.impl.common.shield.ShieldVariant;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
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

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {

    STONE_SHIELD = construct("stone_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.STONE, new Properties().durability(500)), consumer);
    IRON_SHIELD = construct("iron_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.IRON, new Properties().durability(1000)), consumer);
    GOLD_SHIELD = construct("gold_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.GOLD, new Properties().durability(750)), consumer);
    DIAMOND_SHIELD = construct("diamond_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.DIAMOND, new Properties().durability(1750)), consumer);
    NETHERITE_SHIELD = construct("netherite_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.NETHERITE, new Properties().durability(2000)), consumer);

    ENDER_SHIELD = construct("ender_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.ENDER, new Properties().durability(2250)), consumer);
    BLAZE_SHIELD = construct("blaze_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.BLAZE, new Properties().durability(2250)), consumer);
    FIRE_CHARGE_SHIELD = construct("fire_charge_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.FIRE_CHARGE, new Properties().durability(2250)), consumer);
    SHULKER_SHIELD = construct("shulker_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.SHULKER, new Properties().durability(2250)), consumer);
    DRAGON_HEAD_SHIELD = construct("dragon_head_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.DRAGON_HEAD, new Properties().durability(2250)), consumer);
    TNT_SHIELD = construct("tnt_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.TNT, new Properties().durability(2250)), consumer);
    REDSTONE_SHIELD = construct("redstone_shield", NSVShieldItem::new, new Pair<>(ShieldVariant.REDSTONE, new Properties().durability(2250)), consumer);
  }

  private static Item construct(String name, BiFunction<ShieldVariant, Properties, Item> constructor, Pair<ShieldVariant, Properties> properties,  BiConsumer<Item, ResourceLocation> consumer) {

    // required registration logic
    ResourceLocation id = NewShieldVariants.identifier(name);
    Item item = constructor.apply(properties.getA(), properties.getB());
    consumer.accept(item, id);

    // addtiional logic
    ITEMS.add(item);

    return item;
  }
}
