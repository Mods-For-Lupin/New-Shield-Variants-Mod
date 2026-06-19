package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import java.util.function.Consumer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class NewShieldVariantsClientForge {

  public NewShieldVariantsClientForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {
      NewShieldVariantsClient.init();

      // Forge Note https://docs.minecraftforge.net/en/1.20.1/resources/client/models/itemproperties/
      // Use FMLClientSetupEvent#enqueueWork to proceed with the tasks, since the data structures
      // in ItemProperties are not thread-safe.
      event.enqueueWork(() -> {
        ModItems.ITEMS.forEach(item -> {
          ItemProperties.register(item, new ResourceLocation("blocking"),
              (stack, level, entity, useTicks) -> {
                return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
              });
        });
      });
    });
  }
}
