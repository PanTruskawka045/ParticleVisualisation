package me.pan_truskawka045.particlevisualiser.particle;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ParticleEncoders {

    public static final BiFunction<Map<String, String>, ParticleType<?>, ParticleOptions> BLOCK = (options, particleType) -> {
        int block = Integer.parseInt(options.get("block"));
        return new BlockParticleOption((ParticleType<BlockParticleOption>) particleType, Block.BLOCK_STATE_REGISTRY.byId(block));
    };

    public static final BiFunction<Map<String, String>, ParticleType<?>, ParticleOptions> DUST = (options, particleType) -> {
        String color = options.get("color").replace("#", "");
        float scale = Float.parseFloat(options.get("scale"));
        int colorInt = Integer.parseInt(color, 16);
        return new DustParticleOptions(toVector3f(Vec3.fromRGB24(colorInt)), scale);
    };


    public static final BiFunction<Map<String, String>, ParticleType<?>, ParticleOptions> DUST_TRANSITION = (options, particleType) -> {
        String color1 = options.get("colorFrom").replace("#", "");
        int colorInt1 = Integer.parseInt(color1, 16);

        float scale = Float.parseFloat(options.get("scale"));

        String color2 = options.get("colorTo").replace("#", "");
        int colorInt2 = Integer.parseInt(color2, 16);

        return new DustColorTransitionOptions(toVector3f(Vec3.fromRGB24(colorInt1)), toVector3f(Vec3.fromRGB24(colorInt2)), scale);
    };

    public static final BiConsumer<FriendlyByteBuf, Map<String, String>> VIBRATION = (byteMessage, stringStringMap) -> {
        int x1 = (int) Float.parseFloat(stringStringMap.get("x1"));
        int y1 = (int) Float.parseFloat(stringStringMap.get("y1"));
        int z1 = (int) Float.parseFloat(stringStringMap.get("z1"));
        byteMessage.writeLong(asLong(x1, y1, z1));
    };


    private static long asLong(int x, int y, int z) {
        return (((long) x & (long) 67108863) << 38) | (((long) y & (long) 4095)) | (((long) z & (long) 67108863) << 12); // Paper - inline constants and simplify
    }

    private static Vector3f toVector3f(Vec3 vec3) {
        return new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

}
