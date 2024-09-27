package me.pan_truskawka045.particlevisualiser.particle;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.particlevisualiser.ParticleVisualiserPlugin;
import me.pan_truskawka045.particlevisualiser.web.ParticleRequest;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;

@RequiredArgsConstructor
public class ParticleTask {

    private final ParticleVisualiserPlugin plugin;


    public void init() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            ParticleRequest request = plugin.getRequest();
            if (request == null) {
                return;
            }

            ParticleType<?> particleType = Registry.PARTICLE_TYPE.byId(request.getParticle().ordinal());

            ParticleOptions apply = request.getParticle().getDataWriter().apply(request.getExtraOptions(), particleType);

            ClientboundLevelParticlesPacket particles = new ClientboundLevelParticlesPacket(apply == null ? (SimpleParticleType) particleType : apply, true,
                    0d, 64d, 0d,
                    request.getOffset().getX(), request.getOffset().getY(), request.getOffset().getZ(),
                    request.getSpeed(), request.getCount()
            );

            Bukkit.getOnlinePlayers().forEach(player -> ((CraftPlayer) player).getHandle().networkManager.send(particles));

        }, 1, 1);
    }


}
