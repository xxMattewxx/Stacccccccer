# Stacccccccer
Simple mob stacking plugin which replicates Minecraft default drops 

#How does it work?
Once a mob spawner attempts creating an entity, the plugin will check for the location's surroundings for other already existing mobs of the same type, and stack if possible.
Additionally, every N ticks (configurable via schedulerTiming in the config.yml file), the plugin checks stackable mobs for other surrounding mobs of the same type (distance being stackDistance in config). In that case, they're either stacked (up to maxStackCount), or not messed with.
