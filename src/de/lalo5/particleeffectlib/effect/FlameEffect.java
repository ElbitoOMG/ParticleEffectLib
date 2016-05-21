package de.lalo5.particleeffectlib.effect;

import de.lalo5.particleeffectlib.Effect;
import de.lalo5.particleeffectlib.EffectManager;
import de.lalo5.particleeffectlib.EffectType;
import de.lalo5.particleeffectlib.util.ParticleEffect;
import de.lalo5.particleeffectlib.util.RandomUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class FlameEffect extends Effect {

    public FlameEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        iterations = 600;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int i = 0; i < 10; i++) {
            Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6d);
            v.setY(RandomUtils.random.nextFloat() * 1.8);
            location.add(v);
            ParticleEffect.FLAME.display(0, 0, 0, speed, 1, location, visibleRange);
            location.subtract(v);
        }
    }

}
