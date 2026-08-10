package lightmage670.willowisp.attachment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

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
            return this.target.getAttachedOrSet(BLOOD_TYPE, "mortal");
        }
    
        public void setBloodType(PlayerEntity target, String value) {
            this.target.setAttached(BLOOD_TYPE, value);
        }
    }
}