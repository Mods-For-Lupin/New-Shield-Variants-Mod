package io.github.jason13official.new_shield_variants;

import java.util.function.Consumer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class NewShieldVariantsClientForge {

  public NewShieldVariantsClientForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> NewShieldVariantsClient.init());
  }
}
