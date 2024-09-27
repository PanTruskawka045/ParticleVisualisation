package me.pan_truskawka045.particlevisualiser.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.particlevisualiser.ParticleVisualiserPlugin;
import spark.Request;
import spark.Response;
import spark.Route;

@RequiredArgsConstructor
public class ParticleApiRoute implements Route {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ParticleVisualiserPlugin plugin;

    @Override
    public Object handle(Request request, Response response) {
        try {
            String body = request.body();
            ParticleRequest particleRequest = objectMapper.readValue(body, ParticleRequest.class);
            plugin.setRequest(particleRequest);
            response.status(200);
            return "{\"success\": true}";
        } catch (Exception exception) {
            response.status(400);
            return "{\"success\": false}";
        }

    }
}
