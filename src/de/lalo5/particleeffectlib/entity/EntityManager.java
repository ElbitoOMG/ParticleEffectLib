package de.lalo5.particleeffectlib.entity;

import de.lalo5.particleeffectlib.EffectLib;
import de.lalo5.particleeffectlib.listener.ItemListener;
import de.lalo5.particleeffectlib.util.RandomUtils;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

public final class EntityManager {

    private final EffectLib effectLib;
    private final Map<Entity, BukkitTask> entities;
    private boolean disposed = false;

    public EntityManager(EffectLib effectLib) {
        this.effectLib = effectLib;
        this.entities = new HashMap<Entity, BukkitTask>();
    }

    public void removeAll() {
        for (Map.Entry<Entity, BukkitTask> entry : entities.entrySet()) {
            entry.getKey().remove();
            entry.getValue().cancel();
        }
        entities.clear();
    }

    public void remove(Entity entity) {
        entities.get(entity).cancel();
        entities.remove(entity);
        entity.remove();
    }

    public void add(final Entity entity, int duration) {
        if (disposed) {
            throw new IllegalStateException("EffectManager is disposed and not able to accept any effects.");
        }
        BukkitTask task = Bukkit.getScheduler().runTaskLater(effectLib, new Runnable() {

            @Override
            public void run() {
                remove(entity);
            }

        }, duration);
        entities.put(entity, task);
    }

    public Item spawnItem(ItemStack is, Location loc, int duration) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ItemListener.ITEM_IDENTIFIER + ChatColor.RED + RandomUtils.random.nextInt(10000));
        is.setItemMeta(im);

        Item i = loc.getWorld().dropItem(loc, is);
        i.setMetadata(ItemListener.ITEM_IDENTIFIER, new FixedMetadataValue(EffectLib.instance(), 0));
        add(i, duration);
        return i;
    }

    public Entity spawnEntity(EntityType type, Location loc, int duration) {
        Entity e = loc.getWorld().spawnEntity(loc, type);
        e.setMetadata(ItemListener.ITEM_IDENTIFIER, new FixedMetadataValue(EffectLib.instance(), 0));
        add(e, duration);
        return e;
    }

    public void dispose() {
        disposed = true;
        removeAll();
    }

}
