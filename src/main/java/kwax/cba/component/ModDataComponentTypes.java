package kwax.cba.component;

import com.jcraft.jorbis.Block;
import com.mojang.serialization.Codec;
import kwax.cba.CubicBizzareAdventure;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Boolean> FirstJoin =
            register("first_join", builder -> builder.codec(Codec.BOOL));


    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(CubicBizzareAdventure.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    };

    public static void registerComponents() {
        CubicBizzareAdventure.LOGGER.info("Registering Components");
    }
}
