package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.registry.ModEntities;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModTabs;
import java.util.function.Consumer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class NewShieldVariantsClientNeoForge {

  public NewShieldVariantsClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {

      NewShieldVariantsClient.init();

      // the "blocking" item-property predicate API is gone; the blocking model swap now needs to
      // be driven by the item model's own data-driven condition (asset migration, not code)
    });

    modEventBus.addListener((Consumer<BuildCreativeModeTabContentsEvent>) event -> {
      if (event.getTabKey() == ModTabs.KEY) {
        ModItems.ITEMS.forEach(item -> event.accept(new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
      }
    });

    modEventBus.addListener((Consumer<EntityRenderersEvent.RegisterRenderers>) event -> {
      event.registerEntityRenderer(ModEntities.THROWN_TNT, ThrownItemRenderer::new);
      event.registerEntityRenderer(ModEntities.THROWN_FIRE_CHARGE, ThrownItemRenderer::new);
    });
  }
}
