package io.github.jason13official.new_shield_variants;

import io.github.jason13official.new_shield_variants.impl.common.NSVConfig;
import io.github.jason13official.new_shield_variants.platform.Services;
import net.minecraft.resources.Identifier;

public class NewShieldVariants {

  public static void init() {
    NSVConfig.load(Services.PLATFORM.getConfigDirectory());
  }

  public static Identifier identifier(final String path) {
    return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}