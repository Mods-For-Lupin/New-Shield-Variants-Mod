package io.github.jason13official.new_shield_variants.impl.common.shield;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class RedstoneSignalTracker {

  private static final Map<UUID, Long> playerPositions = new HashMap<>();
  private static final Set<Long> activePositions = new HashSet<>();

  /// begin tracking a player's position in the world
  public static void update(UUID playerId, BlockPos pos, Level level) {
    long newLong = pos.asLong();
    Long old = playerPositions.get(playerId);
    if (old != null && old == newLong) {
      return;
    }
    playerPositions.put(playerId, newLong);
    if (old != null) {
      activePositions.remove(old);
      BlockPos oldPos = BlockPos.of(old);
      level.updateNeighborsAt(oldPos, level.getBlockState(oldPos).getBlock());
    }
    activePositions.add(newLong);
    level.updateNeighborsAt(pos, level.getBlockState(pos).getBlock());
  }

  /// stop tracking a player's position in the world
  public static void remove(UUID playerId, Level level) {
    Long old = playerPositions.remove(playerId);
    if (old != null) {
      activePositions.remove(old);
      BlockPos oldPos = BlockPos.of(old);
      level.updateNeighborsAt(oldPos, level.getBlockState(oldPos).getBlock());
    }
  }

  public static boolean isActive(BlockPos pos) {
    return activePositions.contains(pos.asLong());
  }
}
