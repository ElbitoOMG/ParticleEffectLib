package de.lalo5.particleeffectlib.effect;

import de.lalo5.particleeffectlib.Effect;
import de.lalo5.particleeffectlib.EffectManager;
import de.lalo5.particleeffectlib.EffectType;
import de.lalo5.particleeffectlib.util.ParticleEffect;
import de.lalo5.particleeffectlib.util.VectorUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class GridEffect extends Effect {

    /**
     * ParticleType of the nucleus
     */
    public ParticleEffect particle = ParticleEffect.FLAME;

    /**
     * Rows of the grid
     */
    public int rows = 5;

    /**
     * Columns of the grid
     */
    public int columns = 10;

    /**
     * Width per cell in blocks
     */
    public float widthCell = 1;

    /**
     * Height per cell in blocks
     */
    public float heightCell = 1;

    /**
     * Particles to be spawned on the horizontal borders of the cell
     */
    public int particlesWidth = 4;

    /**
     * Particles to be spawned on the vertical borders of the cell
     */
    public int particlesHeight = 3;

    /**
     * Rotation around the Y-axis
     */
    public double rotation = 0;

    public GridEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
        period = 5;
        iterations = 50;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        // Draw rows
        Vector v = new Vector();
        for (int i = 0; i <= (rows + 1); i++) {
            for (int j = 0; j < particlesWidth * (columns + 1); j++) {
                v.setY(i * heightCell);
                v.setX(j * widthCell / particlesWidth);
                addParticle(location, v);
            }
        }
        // Draw columns
        for (int i = 0; i <= (columns + 1); i++) {
            for (int j = 0; j < particlesHeight * (rows + 1); j++) {
                v.setX(i * widthCell);
                v.setY(j * heightCell / particlesHeight);
                addParticle(location, v);
            }
        }
    }

    protected void addParticle(Location location, Vector v) {
        v.setZ(0);
        VectorUtils.rotateAroundAxisY(v, rotation);
        location.add(v);
        display(particle, location);
        location.subtract(v);
    }

}
