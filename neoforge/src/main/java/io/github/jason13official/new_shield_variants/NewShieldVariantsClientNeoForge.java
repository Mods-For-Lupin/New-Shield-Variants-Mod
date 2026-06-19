package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModEntities;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import java.util.function.Consumer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class NewShieldVariantsClientNeoForge {

  public NewShieldVariantsClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {

      NewShieldVariantsClient.init();

      event.enqueueWork(() -> {
        ModItems.ITEMS.forEach(item -> {
          ItemProperties.register(item, ResourceLocation.withDefaultNamespace("blocking"),
              (stack, level, entity, useTicks) -> {
                return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
              });
        });
      });
    });

    modEventBus.addListener((Consumer<EntityRenderersEvent.RegisterRenderers>) event -> {
      event.registerEntityRenderer(ModEntities.THROWN_TNT, ThrownItemRenderer::new);
      event.registerEntityRenderer(ModEntities.THROWN_FIRE_CHARGE, ThrownItemRenderer::new);
    });
  }
}
