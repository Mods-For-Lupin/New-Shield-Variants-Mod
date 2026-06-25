package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.NSVConfig;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModBlocks;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModEntities;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModItems;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModMenus;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModParticles;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModTabs;
import io.github.jason13official.new_shield_variants.impl.common.registry.ModTiles;
import io.github.jason13official.new_shield_variants.platform.Services;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.resource.DataResourceLoaderImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class NewShieldVariantsFabric implements ModInitializer {

  @Override
  public void onInitialize() {

    bind(BuiltInRegistries.BLOCK, ModBlocks::register);
    bind(BuiltInRegistries.ENTITY_TYPE, ModEntities::register);
    bind(BuiltInRegistries.ITEM, ModItems::register);
    bind(BuiltInRegistries.PARTICLE_TYPE, ModParticles::register);
    bind(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(BuiltInRegistries.MENU, ModMenus::register);
    bind(BuiltInRegistries.CREATIVE_MODE_TAB, ModTabs::register);

    NewShieldVariants.init();

    DataResourceLoaderImpl.get(PackType.SERVER_DATA).registerReloadListener(NewShieldVariants.identifier(Constants.MOD_ID), new ResourceReloadListener());
  }

  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, Identifier>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
  }

  public static class ResourceReloadListener extends SimplePreparableReloadListener<Void> {

    @Override
    public String getName() {
      return NewShieldVariants.identifier(Constants.MOD_ID).toString();
    }

    @Override
    protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      // ModConfig.load(Services.PLATFORM.getConfigDirectory());
      NSVConfig.load(Services.PLATFORM.getConfigDirectory());
    }

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      return null;
    }
  }
}
