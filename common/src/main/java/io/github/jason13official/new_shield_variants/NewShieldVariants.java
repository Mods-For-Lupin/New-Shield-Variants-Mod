package io.github.jason13official.new_shield_variants;

import net.minecraft.resources.ResourceLocation;

public class NewShieldVariants {

  public static void init() {
  }

  public static ResourceLocation identifier(final String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}