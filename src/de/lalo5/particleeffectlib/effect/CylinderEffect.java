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

import java.util.Random;

public class CylinderEffect extends Effect {

    /**
     * Particle of the cube
     */
    public ParticleEffect particle = ParticleEffect.FLAME;

    /**
     * Radius of cylinder
     */
    public float radius = 1;

    /**
     * Height of Cylinder
     */
    public float height = 3;

    /**
     * Turns the cube by this angle each iteration around the x-axis
     */
    public double angularVelocityX = Math.PI / 200;

    /**
     * Turns the cube by this angle each iteration around the y-axis
     */
    public double angularVelocityY = Math.PI / 170;

    /**
     * Turns the cube by this angle each iteration around the z-axis
     */
    public double angularVelocityZ = Math.PI / 155;

    /**
     * Rotation of the cylinder
     */
    public double rotationX, rotationY, rotationZ;

    /**
     * Particles in each row
     */
    public int particles = 100;

    /**
     * True if rotation is enable
     */
    public boolean enableRotation = true;

    /**
     * Toggles the cylinder to be solid
     */
    public boolean solid = false;

    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    /**
     * Ratio of sides to entire surface
     */
    protected float sideRatio = 0;

    public CylinderEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 2;
        iterations = 200;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        if (sideRatio == 0) {
            calculateSideRatio();
        }
        Random r = RandomUtils.random;
        double xRotation = rotationX, yRotation = rotationY, zRotation = rotationZ;
        if (enableRotation) {
            xRotation += step * angularVelocityX;
            yRotation += step * angularVelocityY;
            zRotation += step * angularVelocityZ;
        }
        for (int i = 0; i < particles; i++) {
            float multi = (solid) ? r.nextFloat() : 1;
            Vector v = RandomUtils.getRandomCircleVector().multiply(radius);
            if (r.nextFloat() <= sideRatio) {
                // SIDE PARTICLE
                v.multiply(multi);
                v.setY((r.nextFloat() * 2 - 1) * (height / 2));
            } else {
                // GROUND PARTICLE
                v.multiply(r.nextFloat());
                if (r.nextFloat() < 0.5) {
                    // TOP
                    v.setY(multi * (height / 2));
                } else {
                    // BOTTOM
                    v.setY(-multi * (height / 2));
                }
            }
            if (enableRotation) {
                VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
            }
            particle.display(0, 0, 0, speed, 1, location.add(v), visibleRange);
            location.subtract(v);
        }
        display(particle, location);
        step++;
    }

    protected void calculateSideRatio() {
        float grounds, side;
        grounds = MathUtils.PI * MathUtils.PI * radius * 2;
        side = 2 * MathUtils.PI * radius * height;
        sideRatio = side / (side + grounds);
    }
}
