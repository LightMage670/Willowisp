package lightmage670.willowisp.item;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

public class BloodComponent extends FoodComponent {
	private final boolean meat;
	private final boolean alwaysEdible;
	private final boolean snack;
	private final List<Pair<StatusEffectInstance, Float>> statusEffects;

    BloodComponent(int hunger, float saturationModifier, boolean meat, boolean alwaysEdible, boolean snack, List<Pair<StatusEffectInstance, Float>> statusEffects) {
        super(hunger, saturationModifier, meat, alwaysEdible, snack, statusEffects);
		this.meat = meat;
		this.alwaysEdible = alwaysEdible;
		this.snack = snack;
		this.statusEffects = statusEffects;
    }

	/**
	 * Checks if a food item can be fed to dogs.
	 */
	public boolean isMeat() {
		return this.meat;
	}

	/**
	 * Checks if a food item can be eaten when the current hunger bar is full.
	 */
	public boolean isAlwaysEdible() {
		return this.alwaysEdible;
	}

	/**
	 * Checks if a food item is snack-like and is eaten quickly.
	 */
	public boolean isSnack() {
		return this.snack;
	}

	/**
	 * Gets a list of all status effect instances that may be applied when a food item is consumed.
	 * 
	 * <p>The first value in the pair is the status effect instance to be applied.
	 * <p>The second value is the chance the status effect gets applied, on a scale between {@code 0.0F} and {@code 1.0F}.
	 */
	public List<Pair<StatusEffectInstance, Float>> getStatusEffects() {
		return this.statusEffects;
	}

	public static class Builder {
		private int hunger;
		private float saturationModifier;
		private boolean meat;
		private boolean alwaysEdible;
		private boolean snack;
		private final List<Pair<StatusEffectInstance, Float>> statusEffects = Lists.newArrayList();

		/**
		 * Specifies the amount of hunger a food item will fill.
		 * 
		 * <p>One hunger is equivalent to half of a hunger bar icon.
		 * 
		 * @param hunger the amount of hunger
		 */
		public BloodComponent.Builder hunger(int hunger) {
			this.hunger = hunger;
			return this;
		}

		/**
		 * Specifies the saturation modifier of a food item.
		 * 
		 * <p>This value is typically used to determine how long a player can sustain the current hunger value before the hunger is used.
		 * 
		 * @param saturationModifier the saturation modifier
		 */
		public BloodComponent.Builder saturationModifier(float saturationModifier) {
			this.saturationModifier = saturationModifier;
			return this;
		}

		/**
		 * Specifies that a food item can be fed to dogs.
		 */
		public BloodComponent.Builder meat() {
			this.meat = true;
			return this;
		}

		/**
		 * Specifies that a food item can be eaten when the current hunger bar is full.
		 */
		public BloodComponent.Builder alwaysEdible() {
			this.alwaysEdible = true;
			return this;
		}

		/**
		 * Specifies that a food item is snack-like and is eaten quickly.
		 */
		public BloodComponent.Builder snack() {
			this.snack = true;
			return this;
		}

		/**
		 * Specifies a status effect to apply to an entity when a food item is consumed.
		 * This method may be called multiple times to apply several status effects when food is consumed.
		 * 
		 * @param chance the chance the status effect is applied, on a scale of {@code 0.0F} to {@code 1.0F}
		 * @param effect the effect instance to apply
		 */
		public BloodComponent.Builder statusEffect(StatusEffectInstance effect, float chance) {
			this.statusEffects.add(Pair.of(effect, chance));
			return this;
		}

		public BloodComponent build() {
			return new BloodComponent(this.hunger, this.saturationModifier, this.meat, this.alwaysEdible, this.snack, this.statusEffects);
		}
	}
}