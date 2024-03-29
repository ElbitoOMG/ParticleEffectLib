package de.lalo5.particleeffectlib.effect;

import de.lalo5.particleeffectlib.Effect;
import de.lalo5.particleeffectlib.EffectType;
import de.lalo5.particleeffectlib.util.RandomUtils;
import de.lalo5.particleeffectlib.util.VectorUtils;
import de.lalo5.particleeffectlib.EffectManager;
import de.lalo5.particleeffectlib.util.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class AtomEffect extends Effect {

    /**
     * ParticleType of the nucleus
     */
    public ParticleEffect particleNucleus = ParticleEffect.DRIP_WATER;
    public Color colorNucleus = null;

    /**
     * ParticleType of orbitals
     */
    public ParticleEffect particleOrbital = ParticleEffect.DRIP_LAVA;
    public Color colorOrbital = null;

    /**
     * Radius of the atom
     */
    public double radius = 3;

    /**
     * Radius of the nucleus as a fraction of the atom-radius
     */
    public float radiusNucleus = .2f;

    /**
     * Particles to be spawned in the nucleus per iteration
     */
    public int particlesNucleus = 10;

    /**
     * Particles to be spawned per orbital per iteration
     */
    public int particlesOrbital = 10;

    /**
     * Orbitals around the nucleus
     */
    public int orbitals = 3;

    /**
     * Rotation around the Y-axis
     */
    public double rotation = 0;

    /**
     * Velocity of the orbitals
     */
    public double angularVelocity = Math.PI / 80d;

    /**
     * Internal counter
     */
    protected int step = 0;

    public AtomEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = 200;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int i = 0; i < particlesNucleus; i++) {
            Vector v = RandomUtils.getRandomVector().multiply(radius * radiusNucleus);
            location.add(v);
            display(particleNucleus, location, colorNucleus);
            location.subtract(v);
        }
        for (int i = 0; i < particlesOrbital; i++) {
            double angle = step * angularVelocity;
            for (int j = 0; j < orbitals; j++) {
                double xRotation = (Math.PI / orbitals) * j;
                Vector v = new Vector(Math.cos(angle), Math.sin(angle), 0).multiply(radius);
                VectorUtils.rotateAroundAxisX(v, xRotation);
                VectorUtils.rotateAroundAxisY(v, rotation);
                location.add(v);
                display(particleOrbital, location, colorOrbital);
                location.subtract(v);
            }
            step++;
        }
    }

}
