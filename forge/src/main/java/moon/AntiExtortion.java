package moon;

import moon.commands.Login;
import moon.commands.Register;
import moon.listeners.CommandListener;
import moon.listeners.LoginListener;
import moon.listeners.PlayerEventListener;
import moon.listeners.Scheduler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(
        modid = AntiExtortion.MODID,
        name = AntiExtortion.NAME,
        version = AntiExtortion.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
public class AntiExtortion
{
    public static final String MODID = "anti-extortion-mod";
    public static final String NAME = "Anti Extortion Mod";
    public static final String VERSION = "1.0";

    private Scheduler scheduler = new Scheduler();
    private CommandListener commandListener = new CommandListener();
    private PlayerEventListener playerListener = new PlayerEventListener();
    private LoginListener loginListener = new LoginListener(scheduler, commandListener, playerListener);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(scheduler);
        MinecraftForge.EVENT_BUS.register(loginListener);
        MinecraftForge.EVENT_BUS.register(commandListener);
        MinecraftForge.EVENT_BUS.register(playerListener);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new Login(loginListener, commandListener, playerListener));
        event.registerServerCommand(new Register());
    }
}
