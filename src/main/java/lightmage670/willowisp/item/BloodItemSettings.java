package lightmage670.willowisp.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Rarity;

public class BloodItemSettings extends FabricItemSettings {
	BloodComponent bloodComponent;
	public BloodItemSettings blood(BloodComponent bloodComponent) {
		this.bloodComponent = bloodComponent;
        return this;
    }
    @Override
	public BloodItemSettings food(FoodComponent foodComponent) {
		super.food(foodComponent);
		return this;
	}

	@Override
	public BloodItemSettings maxCount(int maxCount) {
		super.maxCount(maxCount);
		return this;
	}

	@Override
	public BloodItemSettings maxDamageIfAbsent(int maxDamage) {
		super.maxDamageIfAbsent(maxDamage);
		return this;
	}

	@Override
	public BloodItemSettings maxDamage(int maxDamage) {
		super.maxDamage(maxDamage);
		return this;
	}

	@Override
	public BloodItemSettings recipeRemainder(Item recipeRemainder) {
		super.recipeRemainder(recipeRemainder);
		return this;
	}

	@Override
	public BloodItemSettings rarity(Rarity rarity) {
		super.rarity(rarity);
		return this;
	}

	@Override
	public BloodItemSettings fireproof() {
		super.fireproof();
		return this;
	}

	@Override
	public BloodItemSettings requires(FeatureFlag... features) {
		super.requires(features);
		return this;
	}
}
