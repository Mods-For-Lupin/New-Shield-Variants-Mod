package io.github.jason13official.new_shield_variants.mixin;

import io.github.jason13official.new_shield_variants.impl.common.item.NSVShieldItem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

  @Inject(method = "maybeDisableShield", at = @At("HEAD"))
  private void new_shield_variants$maybeDisableShield(Player player, ItemStack mobItemStack, ItemStack playerItemStack,
      CallbackInfo ci) {

    Mob self = (Mob) (Object) this;

    if (!(playerItemStack.getItem() instanceof NSVShieldItem shield)) {
      return;
    }

    // if (!mobItemStack.isEmpty() && !playerItemStack.isEmpty() && mobItemStack.getItem() instanceof AxeItem && playerItemStack.is(Items.SHIELD)) {
    if (!mobItemStack.isEmpty() && !playerItemStack.isEmpty()
        && mobItemStack.getItem() instanceof AxeItem /*&& playerItemStack.is(Items.SHIELD) ignored due to instanceof check*/) {
      float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(self) * 0.05F;
      if (self.getRandom().nextFloat() < f) {
        player.getCooldowns().addCooldown(shield, 100);
        self.level().broadcastEntityEvent(player, (byte) 30);
      }
    }
  }
}
