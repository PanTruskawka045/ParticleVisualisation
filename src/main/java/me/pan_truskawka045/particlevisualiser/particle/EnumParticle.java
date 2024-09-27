package me.pan_truskawka045.particlevisualiser.particle;

import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
public enum EnumParticle {

    AMBIENT_ENTITY_EFFECT,
    ANGRY_VILLAGER,
    BLOCK(ParticleEncoders.BLOCK),
    BLOCK_MARKER(ParticleEncoders.BLOCK),
    BUBBLE,
    CLOUD,
    CRIT,
    DAMAGE_INDICATOR,
    DRAGON_BREATH,
    DRIPPING_LAVA,
    FALLING_LAVA,
    LANDING_LAVA,
    DRIPPING_WATER,
    FALLING_WATER,
    DUST(ParticleEncoders.DUST),
    DUST_COLOR_TRANSITION(ParticleEncoders.DUST_TRANSITION),
    EFFECT,
    ELDER_GUARDIAN,
    ENCHANTED_HIT,
    ENCHANT,
    END_ROD,
    ENTITY_EFFECT,
    EXPLOSION_EMITTER,
    EXPLOSION,
    FALLING_DUST(ParticleEncoders.BLOCK),
    FIREWORK,
    FISHING,
    FLAME,
    SOUL_FIRE_FLAME,
    SOUL,
    FLASH,
    HAPPY_VILLAGER,
    COMPOSTER,
    HEART,
    INSTANT_EFFECT,
    ITEM,
    VIBRATION,
    ITEM_SLIME,
    ITEM_SNOWBALL,
    LARGE_SMOKE,
    LAVA,
    MYCELIUM,
    NOTE,
    POOF,
    PORTAL,
    RAIN,
    SMOKE,
    SNEEZE,
    SPIT,
    QUID_INK,
    SWEEP_ATTACK,
    TOTEM_OF_UNDYING,
    UNDERWATER,
    SPLASH,
    WITCH,
    BUBBLE_POP,
    CURRENT_DOWN,
    UBBLE_COLUMN_UP,
    NAUTILUS,
    DOLPHIN,
    CAMPFIRE_COSY_SMOKE,
    CAMPFIRE_SIGNAL_SMOKE,
    DRIPPING_HONEY,
    FALLING_HONEY,
    LANDING_HONEY,
    FALLING_NECTAR,
    FALLING_SPORE_BLOSSOM,
    ASH,
    CRIMSON_SPORE,
    WARPED_SPORE,
    SPORE_BLOSSOM_AIR,
    DRIPPING_OBSIDIAN_TEAR,
    FALLING_OBSIDIAN_TEAR,
    LANDING_OBSIDIAN_TEAR,
    REVERSE_PORTAL,
    WHITE_ASH,
    SMALL_FLAME,
    SNOWFLAKE,
    DRIPPING_DRIPSTONE_LAVA,
    FALLING_DRIPSTONE_LAVA,
    DRIPPING_DRIPSTONE_WATER,
    FALLING_DRIPSTONE_WATER,
    GLOW_SQUID_INK,
    GLOW,
    WAX_ON,
    WAX_OFF,
    ELECTRIC_SPARK,
    SCRAPE;

    private final BiFunction<Map<String, String>, ParticleType<?>, ParticleOptions> dataWriter;

    EnumParticle(BiFunction<Map<String, String>, ParticleType<?>, ParticleOptions> dataWriter) {
        this.dataWriter = dataWriter;
    }

    EnumParticle() {
        this.dataWriter = (stringStringMap, var) -> null;
    }
}
