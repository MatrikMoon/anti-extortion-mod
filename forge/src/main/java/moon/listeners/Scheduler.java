package moon.listeners;

import moon.misc.Tuple;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moon on 2/20/2019
 * Handles event scheduling based on time
 */

@Mod.EventBusSubscriber
public class Scheduler {
    private static int TICKS_PER_SECOND = 20;
    private List<Tuple<Integer, Runnable>> EVENTS = new ArrayList<>();

    public void addDelayedEvent(int delayInSeconds, Runnable r) {
        addDelayedEventInTicks(delayInSeconds * TICKS_PER_SECOND, r);
    }

    public void addDelayedEventInTicks(int delayInTicks, Runnable r) {
        EVENTS.add(new Tuple<>(delayInTicks, r));
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent tickEvent) {
        List<Tuple<Integer, Runnable>> done = new ArrayList<>();

        for (Tuple<Integer, Runnable> event : EVENTS) {
            event.setFirst(event.getFirst() - 1);

            if (event.getFirst() <= 0) {
                event.getSecond().run();
                done.add(event);
            }
        }

        EVENTS.removeAll(done);
    }
}
