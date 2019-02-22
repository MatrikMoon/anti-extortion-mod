package antiextortionplugin.commands;

import antiextortionplugin.listeners.AuthLoginListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Auth implements CommandExecutor {
    private final AuthLoginListener loginListener;

    public Auth(AuthLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!loginListener.authenticatedPlayers.contains(player)) {
                if (args.length > 0) {
                    if (args[0].equals(player.getDisplayName().substring(0, 3))) loginListener.authenticatedPlayers.add(player);
                }
                else {
                    return false;
                }
            }
        }
        else return false;

        return true;
    }
}