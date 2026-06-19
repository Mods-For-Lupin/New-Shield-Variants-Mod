package io.github.jason13official.new_shield_variants.impl.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.jason13official.new_shield_variants.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

  public static NSVConfig get() {
    return INSTANCE;
  }

  // belongs to the object for serialization purposes
  public final List<String> bannedShieldItemIds = new ArrayList<>();

  public static void load(Path configDir) {

    // reset original values
    NSVConfig.BANNED_SHIELD_ITEMS.clear();
    INSTANCE.bannedShieldItemIds.clear();

    File configDirectory = new File(configDir.toUri());
    if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
      System.out.println("Failed to get or create config directory " + configDirectory.getAbsolutePath());
      NSVConfig.willDefault();
      return;
    }

    File configFile = new File(configDir.resolve(Constants.MOD_ID + "-server.json").toUri());

    if (!configFile.isFile()) {

      // write the file, leave banned bows empty
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(configFile))) {

        gson.toJson(INSTANCE, bw);

      } catch (Exception e) {
        System.out.println("Failed to write config file " + configFile.getAbsolutePath());
        NSVConfig.willDefault();
      }

    } else {

      // read the file, see if we are banning valid bows
      Gson gson = new Gson();
      try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {

        // do actual reading
        NSVConfig newConfigObj = gson.fromJson(br, NSVConfig.class);
        INSTANCE = newConfigObj;

        System.out.println("Loaded config object: " + newConfigObj);
        System.out.println("New config instance:  " + INSTANCE);

        // process our new config, which may throw other errors
        INSTANCE.bannedShieldItemIds.forEach(s -> {

          // "minecraft:shield" to ResourceLocation object
          String[] parts = s.split(":");
          ResourceLocation id = new ResourceLocation(parts[0], parts[1]);

          // if it's a valid item ID ban it, otherwise log a warning.
          if (!BuiltInRegistries.ITEM.containsKey(id)) {
            System.out.println("Illegal item id in config: " + id + " is not a valid identifier. (example: new_shield_variants:tnt_shield)");
          } else {
            BANNED_SHIELD_ITEMS.add(BuiltInRegistries.ITEM.get(id));
          }
        });

      } catch (Exception e) {
        System.out.println("Failed to read config file " + configFile.getAbsolutePath());
        NSVConfig.willDefault();
      }
    }
  }

  private static void willDefault() {
    System.out.println(Constants.MOD_NAME + " will use default configuration values.");
  }
}
