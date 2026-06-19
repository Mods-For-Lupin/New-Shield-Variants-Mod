package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.Constants;
import io.github.jason13official.new_shield_variants.NewShieldVariants;
import io.github.jason13official.new_shield_variants.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  // CreativeModeTab.Output is protected, so display items can't be populated via .displayItems(...)
  // from outside net.minecraft.world.item anymore - platforms populate it via their own tab-output event instead.
  public static final ResourceKey<CreativeModeTab> KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
      NewShieldVariants.identifier(Constants.MOD_ID));

  public static CreativeModeTab NEW_SHIELD_VARIANTS;

  public static void register(BiConsumer<CreativeModeTab, Identifier> consumer) {

    NEW_SHIELD_VARIANTS = Services.PLATFORM.tabBuilder()
        .icon(() -> new ItemStack(ModItems.DRAGON_HEAD_SHIELD))
        .title(Component.translatable("itemGroup.newShieldVariants"))
        .build();

    consumer.accept(NEW_SHIELD_VARIANTS, KEY.identifier());
  }
}
