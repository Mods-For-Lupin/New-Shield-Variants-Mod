package io.github.jason13official.new_shield_variants.mixin;

import io.github.jason13official.new_shield_variants.impl.common.item.NSVShieldItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

//    @Shadow(prefix = "shadow$")
//    protected ItemStack shadow$useItem;

  @Inject(method = "hurtCurrentlyUsedShield", at = @At("HEAD"))
  private void new_shield_variants$hurtCurrentlyUsedShield(float damage, CallbackInfo ci) {

    Player self = (Player) (Object) this;

    // if (this.useItem.is(Items.SHIELD)) {
    if (self.getUseItem().getItem() instanceof NSVShieldItem shield) {
      if (!self.level().isClientSide) {
        self.awardStat(Stats.ITEM_USED.get(self.getUseItem().getItem()));
      }

      if (damage >= 3.0F) {
        int i = 1 + Mth.floor(damage);
        InteractionHand interactionhand = self.getUsedItemHand();
        self.getUseItem().hurtAndBreak(i, self, (p_219739_) -> {
          p_219739_.broadcastBreakEvent(interactionhand);
        });
        if (self.getUseItem().isEmpty()) {
          if (interactionhand == InteractionHand.MAIN_HAND) {
            self.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
          } else {
            self.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
          }

          // self.useItem = ItemStack.EMPTY;
          self.setItemSlot(
              self.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND,
              ItemStack.EMPTY);
          self.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + self.level().random.nextFloat() * 0.4F);
        }
      }

    }
  }

  @Inject(method = "disableShield", at = @At("HEAD"))
  private void new_shield_variants$disableShield(boolean becauseOfAxe, CallbackInfo ci) {

    Player self = (Player) (Object) this;
    ItemStack usedStack = self.getUseItem();
    if (!(usedStack.getItem() instanceof NSVShieldItem shield)) {
      return;
    }

    float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(self) * 0.05F;
    if (becauseOfAxe) {
      f += 0.75F;
    }

    if (self.getRandom().nextFloat() < f) {
      self.getCooldowns().addCooldown(shield, 100);
      self.stopUsingItem();
      // self.level().broadcastEntityEvent(self, (byte)30);
      self.level().broadcastEntityEvent(self, EntityEvent.SHIELD_DISABLED);
    }
  }

//    @Inject(method = "tick", at = @At("TAIL"))
//    private void nsv$tick(CallbackInfo ci) {
//        Player self = (Player) (Object) this;
//        if (!(self instanceof ServerPlayer player)) return;
//        if (!player.isBlocking()) return;
//        NSVTickInteractions.handle(player);
//    }
}
