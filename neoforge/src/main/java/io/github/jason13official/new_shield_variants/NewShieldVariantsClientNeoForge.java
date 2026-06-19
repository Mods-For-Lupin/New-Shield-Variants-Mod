package io.github.jason13official.new_shield_variants;

import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class NewShieldVariantsClientNeoForge {

  public NewShieldVariantsClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> NewShieldVariantsClient.init());
  }
}
