package de.lalo5.particleeffectlib.effect;

import de.lalo5.particleeffectlib.Effect;
import de.lalo5.particleeffectlib.EffectManager;
import de.lalo5.particleeffectlib.EffectType;
import de.lalo5.particleeffectlib.util.MathUtils;
import de.lalo5.particleeffectlib.util.ParticleEffect;
import de.lalo5.particleeffectlib.util.RandomUtils;
import de.lalo5.particleeffectlib.util.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StarEffect extends Effect {

    /**
     * Particles to create the star
     */
    public ParticleEffect particle = ParticleEffect.FLAME;

    /**
     * Particles per spike
     */
    public int particles = 50;

    /**
     * Height of the spikes in blocks
     */
    public float spikeHeight = 3.5f;

    /**
     * Half amount of spikes. Creation is only done half and then mirrored.
     */
    public int spikesHalf = 3;

    /**
     * Inner radius of the star. (0.5)
     */
    public float innerRadius = 0.5f;

    public StarEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 4;
        iterations = 50;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        float radius = 3 * innerRadius / MathUtils.SQRT_3;
        for (int i = 0; i < spikesHalf * 2; i++) {
            double xRotation = i * Math.PI / spikesHalf;
            for (int x = 0; x < particles; x++) {
                double angle = 2 * Math.PI * x / particles;
                float height = RandomUtils.random.nextFloat() * spikeHeight;
                Vector v = new Vector(Math.cos(angle), 0, Math.sin(angle));
                v.multiply((spikeHeight - height) * radius / spikeHeight);
                v.setY(innerRadius + height);
                VectorUtils.rotateAroundAxisX(v, xRotation);
                location.add(v);
                display(particle, location);
                location.subtract(v);
                VectorUtils.rotateAroundAxisX(v, Math.PI);
                VectorUtils.rotateAroundAxisY(v, Math.PI / 2);
                location.add(v);
                display(particle, location);
                location.subtract(v);
            }
        }
    }

}
