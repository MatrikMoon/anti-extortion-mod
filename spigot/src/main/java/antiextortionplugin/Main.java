package antiextortionplugin;

import antiextortionplugin.commands.Auth;
import antiextortionplugin.listeners.AuthLoginListener;
import antiextortionplugin.listeners.CommandPreprocessListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        //Fired when the server enables the plugin

        CommandPreprocessListener commandListener = new CommandPreprocessListener();
        AuthLoginListener loginListener = new AuthLoginListener(this, commandListener);
        Auth auth = new Auth(loginListener);

        getCommand("auth").setExecutor(auth);
        getServer().getPluginManager().registerEvents(commandListener, this);
        getServer().getPluginManager().registerEvents(loginListener, this);
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }
}