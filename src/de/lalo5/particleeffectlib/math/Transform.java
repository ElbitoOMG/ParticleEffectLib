package de.lalo5.particleeffectlib.math;

import org.bukkit.configuration.ConfigurationSection;

public interface Transform {
    void load(ConfigurationSection parameters);
    double get(double t);
}
