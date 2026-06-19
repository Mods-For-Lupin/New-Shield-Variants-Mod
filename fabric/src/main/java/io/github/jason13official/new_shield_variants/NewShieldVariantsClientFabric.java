package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class NewShieldVariantsClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    NewShieldVariantsClient.init();

    ModItems.ITEMS.forEach(item -> {
      ItemProperties.register(item, new ResourceLocation("blocking"),
          (stack, level, entity, useTicks) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
          });
    });
  }
}
