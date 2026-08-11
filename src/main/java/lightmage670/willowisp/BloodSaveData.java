package lightmage670.willowisp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

public class BloodSaveData extends PersistentState {
    private static final String STORAGE_KEY = "willowisp_blood";
    private final Map<UUID, String> playerBlood = new HashMap<>();

    public static BloodSaveData get(MinecraftServer server) {
        return server.getWorld(World.OVERWORLD).getPersistentStateManager().getOrCreate(BloodSaveData::fromNbt, BloodSaveData::new, STORAGE_KEY);
    }

    public String getSelection(UUID uuid) {
        String blood = playerBlood.get(uuid);
        return blood == null || blood.isBlank() ? "mortal" : blood;
    }

    public void setSelection(UUID uuid, String blood) {
        playerBlood.put(uuid, blood == null ? "mortal" : blood);
        markDirty();
    }

    private static BloodSaveData fromNbt(NbtCompound nbt) {
        BloodSaveData data = new BloodSaveData();
        NbtList entries = nbt.getList("playerBlood", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < entries.size(); i++) {
            NbtCompound entry = entries.getCompound(i);
            data.playerBlood.put(entry.getUuid("uuid"), entry.getString("blood"));
        }
        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList entries = new NbtList();
        playerBlood.forEach((uuid, blood) -> {
            NbtCompound entry = new NbtCompound();
            entry.putUuid("uuid", uuid);
            entry.putString("blood", blood);
            entries.add(entry);
        });
        nbt.put("playerBlood", entries);
        return nbt;
    }
}