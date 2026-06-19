package io.github.jason13official.new_shield_variants.impl.common.registry;

import io.github.jason13official.new_shield_variants.Constants;
import io.github.jason13official.new_shield_variants.NewShieldVariants;
import io.github.jason13official.new_shield_variants.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static CreativeModeTab NEW_SHIELD_VARIANTS;

  public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {

    NEW_SHIELD_VARIANTS = Services.PLATFORM.tabBuilder()
        .icon(() -> new ItemStack(ModItems.DRAGON_HEAD_SHIELD))
        .title(Component.translatable("itemGroup.newShieldVariants"))
        .displayItems((itemDisplayParameters, output) -> {
          ModItems.ITEMS.forEach(output::accept);
        })
        .build();

    consumer.accept(NEW_SHIELD_VARIANTS, NewShieldVariants.identifier(Constants.MOD_ID));
  }
}
