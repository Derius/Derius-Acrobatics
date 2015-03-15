package dk.muj.derius.parkour;

public class JumpSetting
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private int unitsPerPotionLevel;
	public int getUnitsPerPotionLevel(){ return unitsPerPotionLevel; }
	public void setUnitsPerPotionLevel(int unitsPerPotionLevel){ this.unitsPerPotionLevel = unitsPerPotionLevel; }
	
	private int maxPotionLevel;
	public int getMaxPotionLevel(){ return maxPotionLevel; }
	public void setMaxPotionLevel(int maxPotionLevel){ this.maxPotionLevel = maxPotionLevel; }
	
	// -------------------------------------------- //
	// CONSTRUCTERS
	// -------------------------------------------- //
	
	// GSON
	@Deprecated
	public JumpSetting()
	{
		this(0, 0);
	}
	
	// Default
	public JumpSetting(int unitsPerPotionLevel, int maxPotionLevel)
	{
		this.setUnitsPerPotionLevel(unitsPerPotionLevel);
		this.setMaxPotionLevel(maxPotionLevel);
	}
	
	// -------------------------------------------- //
	// CALCULATIONS
	// -------------------------------------------- //
	
	public int getPotionLevel(int units)
	{
		units = this.prepareUnits(units);
		if (units == 0 || this.getUnitsPerPotionLevel() == 0) return 0;
		return units / this.getUnitsPerPotionLevel();
	}
	
	public int getMaxUnits()
	{
		return this.getUnitsPerPotionLevel() * this.getMaxPotionLevel();
	}
	
	public int prepareUnits(int units)
	{
		int max = this.getMaxUnits();
		if (units >= max) return max;
		return units;
	}
	
}
