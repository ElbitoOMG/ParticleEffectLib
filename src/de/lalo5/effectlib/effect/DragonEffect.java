package de.lalo5.effectlib.effect;

import de.lalo5.effectlib.Effect;
import de.lalo5.effectlib.EffectManager;
import de.lalo5.effectlib.EffectType;
import de.lalo5.effectlib.util.MathUtils;
import de.lalo5.effectlib.util.ParticleEffect;
import de.lalo5.effectlib.util.RandomUtils;
import de.lalo5.effectlib.util.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DragonEffect extends Effect {

    protected final List<Float> rndF;
    protected final List<Double> rndAngle;
    /**
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.PORTAL;
    /**
     * Pitch of the dragon arc
     */
    public float pitch = .1f;
    /**
     * Arcs to build the breath
     */
    public int arcs = 20;
    /**
     * Particles per arc
     */
    public int particles = 30;
    /**
     * Steps per iteration
     */
    public int stepsPerIteration = 2;
    /**
     * Length in blocks
     */
    public float length = 4;
    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    public DragonEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = 200;
        rndF = new ArrayList<>(arcs);
        rndAngle = new ArrayList<>(arcs);
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int j = 0; j < stepsPerIteration; j++) {
            if (step % particles == 0) {
                rndF.clear();
                rndAngle.clear();
            }
            while (rndF.size() < arcs) {
                rndF.add(RandomUtils.random.nextFloat());
            }
            while (rndAngle.size() < arcs) {
                rndAngle.add(RandomUtils.getRandomAngle());
            }
            for (int i = 0; i < arcs; i++) {
                float pitch = rndF.get(i) * 2 * this.pitch - this.pitch;
                float x = (step % particles) * length / particles;
                float y = (float) (pitch * Math.pow(x, 2));
                Vector v = new Vector(x, y, 0);
                VectorUtils.rotateAroundAxisX(v, rndAngle.get(i));
                VectorUtils.rotateAroundAxisZ(v, -location.getPitch() * MathUtils.degreesToRadians);
                VectorUtils.rotateAroundAxisY(v, -(location.getYaw() + 90) * MathUtils.degreesToRadians);
                display(particle, location.add(v));
                location.subtract(v);
            }
            step++;
        }
    }
}
