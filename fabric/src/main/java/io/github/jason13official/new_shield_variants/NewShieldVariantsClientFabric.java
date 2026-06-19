package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModEntities;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class NewShieldVariantsClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    NewShieldVariantsClient.init();

    ModItems.ITEMS.forEach(item -> {
      ItemProperties.register(item, ResourceLocation.withDefaultNamespace("blocking"),
          (stack, level, entity, useTicks) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
          });
    });

    EntityRendererRegistry.register(ModEntities.THROWN_TNT, ThrownItemRenderer::new);
    EntityRendererRegistry.register(ModEntities.THROWN_FIRE_CHARGE, ThrownItemRenderer::new);
  }
}
