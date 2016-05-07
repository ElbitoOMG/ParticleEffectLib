package de.lalo5.effectlib.effect;

import de.lalo5.effectlib.Effect;
import de.lalo5.effectlib.EffectManager;
import de.lalo5.effectlib.EffectType;
import de.lalo5.effectlib.util.ParticleEffect;
import org.bukkit.Location;

public class IconEffect extends Effect {

    /**
     * ParticleType of spawned particle
     */
    public ParticleEffect particle = ParticleEffect.VILLAGER_ANGRY;

    public int yOffset = 2;

    public IconEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 4;
        iterations = 25;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        location.add(0, yOffset, 0);
        display(particle, location);
    }
}
