package io.github.jason13official.new_shield_variants.impl.common.item;

import io.github.jason13official.new_shield_variants.impl.common.shield.ShieldVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class NSVShieldItem extends ShieldItem {

  private final ShieldVariant variant;

  public NSVShieldItem(ShieldVariant variant, Properties properties) {
    super(properties);
    this.variant = variant;
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return repair.is(this.variant.repairItems());
  }
}
