package dk.muj.derius.parkour;

public class JumpSetting
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final int unitsPerPotionLevel;
	public int getUnitsPerPotionLevel(){ return unitsPerPotionLevel; }
	public JumpSetting withUnitsPerPotionLevel(int unitsPerPotionLevel){ return new JumpSetting(unitsPerPotionLevel, maxPotionLevel); }
	
	private final int maxPotionLevel;
	public JumpSetting withMaxPotionLevel(int unitsPerPotionLevel){ return new JumpSetting(unitsPerPotionLevel, maxPotionLevel); }
	public int getMaxPotionLevel(){ return maxPotionLevel; }
	
	// -------------------------------------------- //
	// CONSTRUCTERS
	// -------------------------------------------- //
	
	// GSON
	private JumpSetting()
	{
		this(0, 0);
	}
	
	// Default
	private JumpSetting(int unitsPerPotionLevel, int maxPotionLevel)
	{
		this.unitsPerPotionLevel = unitsPerPotionLevel;
		this.maxPotionLevel = maxPotionLevel;
	}
	
	// -------------------------------------------- //
	// FACTORY VALUE OF
	// -------------------------------------------- //

	// Default
	public static JumpSetting of(int unitsPerPotionLevel, int maxPotionLevel)
	{
		return new JumpSetting(unitsPerPotionLevel, maxPotionLevel);
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
