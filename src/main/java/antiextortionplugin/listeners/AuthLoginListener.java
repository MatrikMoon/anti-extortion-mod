package antiextortionplugin.listeners;

import antiextortionplugin.scheduledActions.Authenticate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class AuthLoginListener implements Listener {
    private final JavaPlugin plugin;
    private final CommandPreprocessListener commandListener;
    public List<Player> authenticatedPlayers = new ArrayList<>();

    public AuthLoginListener(JavaPlugin plugin, CommandPreprocessListener commandListener) {
        this.plugin = plugin;
        this.commandListener = commandListener;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            player.sendMessage("You are logging in as an OP. Authenticate.");
            commandListener.Enable(player);
            new Authenticate(player, this, commandListener).runTaskLater(plugin, (20 * 15));
        }
        if (player.getDisplayName().equals("Viyi") || player.getDisplayName().equals("NetworkAuditor")) {
            player.sendMessage(ChatColor.RED + "I don't much appreciate being extorted -Moon");
        }
    }
}
