package antiextortionplugin.scheduledActions;

import antiextortionplugin.listeners.AuthLoginListener;
import antiextortionplugin.listeners.CommandPreprocessListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Authenticate extends BukkitRunnable {
    private final Player player;
    private final AuthLoginListener loginListener;
    private final CommandPreprocessListener commandListener;

    public Authenticate(Player player, AuthLoginListener loginListener, CommandPreprocessListener commandListener) {
        this.player = player;
        this.loginListener = loginListener;
        this.commandListener = commandListener;
    }

    @Override
    public void run() {
        if (!loginListener.authenticatedPlayers.contains(player)) {
            player.kickPlayer("You did not authenticate.");
        }
        else {
            player.sendMessage(ChatColor.GREEN + "AUTHENTICATED SUCCESSFULLY.");
            commandListener.Disable(player);
        }
        this.cancel();
    }
}
