package me.pan_truskawka045.particlevisualiser;

import lombok.Getter;
import lombok.Setter;
import me.pan_truskawka045.particlevisualiser.listener.Listeners;
import me.pan_truskawka045.particlevisualiser.particle.ParticleTask;
import me.pan_truskawka045.particlevisualiser.web.ParticleRequest;
import me.pan_truskawka045.particlevisualiser.web.SparkWebServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Setter
@Getter
public class ParticleVisualiserPlugin extends JavaPlugin {

    private final SparkWebServer sparkWebServer = new SparkWebServer(this);
    private final ParticleTask particleTask = new ParticleTask(this);

    private ParticleRequest request;

    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        try {
            sparkWebServer.init(getDataFolder());
        } catch (Exception exception) {
            exception.printStackTrace();
            Bukkit.shutdown();
        }

        saveDefaultConfig();
        particleTask.init();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
    }

}
