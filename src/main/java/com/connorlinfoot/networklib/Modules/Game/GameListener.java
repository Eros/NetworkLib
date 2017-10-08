package com.connorlinfoot.networklib.Modules.Game;

import com.connorlinfoot.networklib.Events.RealPlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class GameListener implements Listener {
    private Game game;

    public GameListener(Game game) {
        this.game = game;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChatHigh(AsyncPlayerChatEvent event) {
        if ((game.getGameState() != GameState.WAITING && !GameSettings.CHAT_IN_LOBBY.is()) || !GameSettings.CHAT_IN_GAME.is()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRealPlayerMove(RealPlayerMoveEvent event) {
	if (GameSettings.PLAYER_FROZEN.is())
			event.setCanceled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onHungerChange(FoodLevelChangeEvent event) {
        if (game.getGameState() != GameState.INGAME || !GameSettings.HUNGER.is())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onItemDrop(PlayerDropItemEvent event) {
        if (game.getGameState() != GameState.INGAME || !GameSettings.DROP_ITEMS.is())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (game.getGameState() != GameState.INGAME || !GameSettings.PICKUP_ITEMS.is())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        if (game.getGameState() != GameState.INGAME || !GameSettings.DESTROY.is())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (game.getGameState() != GameState.INGAME || !GameSettings.BUILD.is())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBucketUse(PlayerInteractEvent event) {
        if (game.getGameState() == GameState.INGAME && !GameSettings.BUCKETS.is() && event.getItem() != null && event.getItem().getType().toString().toUpperCase().contains("BUCKET"))
            event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPvP(EntityDamageByEntityEvent event){
        if(!GameSettings.PVP.is() && event.getDamager() instanceof Player)
            event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onDropItemsOnDeath(PlayerDeathEvent event){
        if(GameSettings.DROP_ITEMS_ON_DEATH.is()) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}
