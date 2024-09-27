package me.pan_truskawka045.particlevisualiser.web;

import lombok.Getter;
import lombok.ToString;
import me.pan_truskawka045.particlevisualiser.particle.EnumParticle;

import java.util.Map;

@ToString
@Getter
public class ParticleRequest {

    private EnumParticle particle;
    private Map<String, String> extraOptions;
    private Offset offset;
    private int count;
    private int speed;

}
