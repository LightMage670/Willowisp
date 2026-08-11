package lightmage670.willowisp.attachment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import lightmage670.willowisp.BloodSaveData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class bloodAttachments {
    public static final AttachmentType<String> BLOOD_TYPE = AttachmentRegistry.create(Identifier.of("willowisp", "blood_type")
    );

    public static BloodData get(PlayerEntity target, String value) {
        BLOOD_TYPE.copyOnDeath();
		return new BloodData(target, value);
	}

    public record BloodData(PlayerEntity target, String value){

        public String getBloodType(PlayerEntity target) {
            if(!target.getWorld().isClient()){
                if (target instanceof ServerPlayerEntity){
                    ServerPlayerEntity player = (ServerPlayerEntity) target;
                    String saveBlood = BloodSaveData.get(player.getServer()).getSelection(player.getUuid());
                    return this.target.getAttachedOrSet(BLOOD_TYPE, saveBlood);
                }
            }
            return this.target.getAttachedOrSet(BLOOD_TYPE, "mortal");
        }
    
        public void setBloodType(PlayerEntity target, String value) {
            if(!target.getWorld().isClient()){
                if (target instanceof ServerPlayerEntity){
                    ServerPlayerEntity player = (ServerPlayerEntity) target;
                    BloodSaveData.get(player.getServer()).setSelection(player.getUuid(), value);
                }
            }
            this.target.setAttached(BLOOD_TYPE, value);
        }
    }
}