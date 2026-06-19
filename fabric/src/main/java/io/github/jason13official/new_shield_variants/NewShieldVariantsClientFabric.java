package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModEntities;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModTabs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class NewShieldVariantsClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    NewShieldVariantsClient.init();

    // the "blocking" item-property predicate API is gone; the blocking model swap now needs to
    // be driven by the item model's own data-driven condition (asset migration, not code)

    CreativeModeTabEvents.modifyOutputEvent(ModTabs.KEY).register(output -> {
      ModItems.ITEMS.forEach(item -> output.accept(new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
    });

    EntityRendererRegistry.register(ModEntities.THROWN_TNT, ThrownItemRenderer::new);
    EntityRendererRegistry.register(ModEntities.THROWN_FIRE_CHARGE, ThrownItemRenderer::new);
  }
}
