package de.lalo5.particleeffectlib.effect;

import de.lalo5.particleeffectlib.Effect;
import de.lalo5.particleeffectlib.EffectType;
import de.lalo5.particleeffectlib.util.ParticleEffect;
import de.lalo5.particleeffectlib.EffectManager;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class TraceEffect extends Effect {

    /**
     * Particle to spawn
     */
    public ParticleEffect particle = ParticleEffect.FLAME;

    /**
     * Interations to wait before refreshing particles
     */
    public int refresh = 5;

    /**
     * Maximum amount of way points
     */
    public int maxWayPoints = 30;

    /**
     * Waypoints of the trace
     */
    protected final List<Vector> wayPoints = new ArrayList<Vector>();

    /**
     * Internal counter
     */
    protected int step = 0;

    /**
     * World of the trace
     */
    protected World world;

    public TraceEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 600;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        if (world == null) {
            world = location.getWorld();
        } else if (!location.getWorld().equals(world)) {
            cancel(true);
            return;
        }

        synchronized(wayPoints)
        {
            if (wayPoints.size() >= maxWayPoints) {
                wayPoints.remove(0);
            }
        }

        wayPoints.add(location.toVector());
        step++;
        if (step % refresh != 0) {
            return;
        }

        synchronized(wayPoints)
        {
            for (Vector position : wayPoints) {
                Location particleLocation = new Location(world, position.getX(), position.getY(), position.getZ());
                display(particle, particleLocation);
            }
        }
    }

}
