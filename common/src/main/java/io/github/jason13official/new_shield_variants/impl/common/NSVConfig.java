package io.github.jason13official.new_shield_variants.impl.common;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.github.jason13official.new_shield_variants.Constants;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class NSVConfig {

  private static NSVConfig INSTANCE = new NSVConfig();

  // belongs to the class so we can reference actual items properly
  public static final List<Item> BANNED_SHIELD_ITEMS = new ArrayList<>();

  // belongs to the object for serialization purposes
  public final List<String> bannedShields = new ArrayList<>();

  public static void load(Path configDir) {

    // reset original values
    NSVConfig.BANNED_SHIELD_ITEMS.clear();
    INSTANCE.bannedShields.clear();

    File configDirectory = new File(configDir.toUri());
    if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
      try {
        Files.createDirectories(configDir); // similar to mkdirs, we're just trying again
      } catch (Exception e) {
        System.out.println("Failed to get or create config directory " + configDirectory.getAbsolutePath());
        NSVConfig.willDefault();
        return;
      }
    }

    Path configFilepath = configDir.resolve(Constants.MOD_ID + "-server.toml");
    File configFile = new File(configFilepath.toUri());

    try (CommentedFileConfig config = CommentedFileConfig.builder(configFile).build()) {
      if (Files.exists(configFilepath)) {
        config.load();
      }

      NSVConfig newConfigObj = new NSVConfig();

      // actually read the config file
      ArrayList<String> itemIds = config.getOrElse("banned_shields", new ArrayList<String>());
      newConfigObj.bannedShields.addAll(itemIds);

      // operate on our read values
      newConfigObj.bannedShields.forEach(s -> {
        // "minecraft:shield" to ResourceLocation object
        String[] parts = s.split(":");
        ResourceLocation id;
        if (parts.length == 2) {
          id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
        } else {
          id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, s);
        }

        // if it's a valid item ID ban it, otherwise log a warning.
        if (!BuiltInRegistries.ITEM.containsKey(id)) {
          System.out.println("Illegal item id in config: " + id + " is not a valid identifier. (example: new_shield_variants:tnt_shield)");
        } else {
          System.out.println("Banning shield " + s);
          BANNED_SHIELD_ITEMS.add(BuiltInRegistries.ITEM.get(id));
        }
      });

      INSTANCE = newConfigObj;

      // do the writing stuff
      config.setComment("banned_shields", " Shields that should be disabled, example: new_shield_variants:tnt_shield");
      config.set("banned_shields", INSTANCE.bannedShields);
      config.save();

    } catch (Exception e) {
      System.out.println("Failed to get or create config file " + configFile.getAbsolutePath());
      NSVConfig.willDefault();
      INSTANCE = new NSVConfig();
    }
  }

  private static void willDefault() {
    System.out.println(Constants.MOD_NAME + " will use default configuration values.");
  }
}
