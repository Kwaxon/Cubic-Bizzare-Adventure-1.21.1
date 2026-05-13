package kwax.cba.misc;

import kwax.cba.CubicBizzareAdventure;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.*;

public class ModStates extends PersistentState {

    public Set<UUID> joinedPlayers = new HashSet<>();
    public Map<UUID, String> bloodlines = new HashMap<>();

    // Default constructor for creating new state
    public ModStates() {}

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        NbtList list = new NbtList();
        joinedPlayers.forEach(uuid -> {
            NbtCompound entry = new NbtCompound();
            entry.putUuid("uuid", uuid);
            list.add(entry);
        });
        nbt.put("joined_players", list);

        NbtList bloodlineList = new NbtList();
        bloodlines.forEach((uuid, bloodline) -> {
            NbtCompound entry = new NbtCompound();
            entry.putUuid("uuid", uuid);
            entry.putString("bloodline", bloodline);
            bloodlineList.add(entry);
        });
        nbt.put("bloodlines", bloodlineList);
        return nbt;
    }

    public static ModStates readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        ModStates state = new ModStates();
        NbtList list = nbt.getList("joined_players", NbtElement.COMPOUND_TYPE);
        list.forEach(entry -> {
            state.joinedPlayers.add(((NbtCompound) entry).getUuid("uuid"));
        });
        NbtList bloodlineList = nbt.getList("bloodlines", NbtElement.COMPOUND_TYPE);
        bloodlineList.forEach(entry -> {
            NbtCompound compound = (NbtCompound) entry;
            state.bloodlines.put(compound.getUuid("uuid"), compound.getString("bloodline"));
        });

        return state;
    }




    public static Type<ModStates> TYPE = new Type<>(
            ModStates::new,
            ModStates::readNbt,
            null
    );

    public static ModStates getServerState(MinecraftServer server) {
        PersistentStateManager manager = server
                .getWorld(World.OVERWORLD)
                .getPersistentStateManager();

        return manager.getOrCreate(ModStates.TYPE, CubicBizzareAdventure.MOD_ID);
    }
}