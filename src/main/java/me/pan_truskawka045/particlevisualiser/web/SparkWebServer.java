package me.pan_truskawka045.particlevisualiser.web;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.particlevisualiser.ParticleVisualiserPlugin;

import java.io.File;

import static spark.Spark.*;

@RequiredArgsConstructor
public class SparkWebServer {

    private final ParticleVisualiserPlugin plugin;

    public void init(File pluginDir) throws Exception {
        File webapp = new File(pluginDir, "webapp");
        if (!webapp.exists()) {
            webapp.mkdir();
            throw new Exception("Webapp directory not found");
        }

        staticFiles.externalLocation(webapp.getAbsolutePath());

        port(plugin.getConfig().getInt("port", 4567));
        post("/api/particle", new ParticleApiRoute(plugin));
    }

}
