package io.github.jason13official.new_shield_variants.impl.common.item;

import io.github.jason13official.new_shield_variants.Constants;
import io.github.jason13official.new_shield_variants.impl.common.NSVConfig;
import io.github.jason13official.new_shield_variants.impl.common.shield.RedstoneSignalTracker;
import io.github.jason13official.new_shield_variants.impl.common.shield.ShieldTickInteractions;
import io.github.jason13official.new_shield_variants.impl.common.shield.ShieldUseInteractions;
import io.github.jason13official.new_shield_variants.impl.common.shield.ShieldVariant;
import io.github.jason13official.new_shield_variants.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;

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

  @Override
  public boolean isEnabled(FeatureFlagSet enabledFeatures) {
    return !NSVConfig.BANNED_SHIELD_ITEMS.contains(this);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack itemstack = player.getItemInHand(hand);
    // player.startUsingItem(hand);

    if (player instanceof ServerPlayer) {
      ShieldUseInteractions.handle(itemstack.getItem(), this.variant, (ServerPlayer) player, itemstack, hand);
    }

    // return InteractionResultHolder.consume(itemstack);
    return super.use(level, player, hand);
  }

  @Override
  public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
    super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    if (livingEntity instanceof ServerPlayer player && player.isBlocking()) {
      ShieldTickInteractions.handle(this.variant, player);
    }
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {

    if (this.variant == ShieldVariant.REDSTONE && livingEntity instanceof ServerPlayer player) {
      RedstoneSignalTracker.remove(player.getUUID(), player.level());
    }

    return super.finishUsingItem(stack, level, livingEntity);
  }
}
