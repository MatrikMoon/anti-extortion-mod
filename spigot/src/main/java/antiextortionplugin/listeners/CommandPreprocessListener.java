package antiextortionplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandPreprocessListener implements Listener {
    private List<Player> blockedPlayers = new ArrayList<>();

    public void Enable(Player player) {
        blockedPlayers.add(player);
    }

    public void Disable(Player player) {
        blockedPlayers.remove(player);
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPreprocess(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().split(" ");

        if (blockedPlayers.contains(event.getPlayer()) && (!command[0].equals("/auth"))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "DENIED.");
        }
    }
}
