package lightmage670.smpcolon3.attachment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

//import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class bloodAttachments {
    public static final AttachmentType<String> BLOOD_TYPE = AttachmentRegistry.create(Identifier.of("smpcolon3", "blood_type"));

    public static BloodData get(AttachmentTarget target, String value) {
        BLOOD_TYPE.copyOnDeath();
		return new BloodData(target, value);
	}

    public record BloodData(AttachmentTarget target, String value){
        public String getBloodType(PlayerEntity target) {
            return this.target.getAttachedOrSet(BLOOD_TYPE, "mortal");
        }
    
        public void setBloodType(AttachmentTarget target, String value) {
            this.target.setAttached(BLOOD_TYPE, value);
        }
    }
}


/*
give blood type
if player dies {
    serverPlayerEvents.copyFrom
}
*/