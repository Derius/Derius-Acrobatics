package dk.muj.derius.acrobatics.entity;

public class JumpSetting
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private float vectorPerUnit;
	public float getVectorPerUnit(){ return vectorPerUnit; }
	public void setVectorPerUnit(float vectorPerUnit){ this.vectorPerUnit = vectorPerUnit; }
	
	private float maxVector;
	public float getMaxVector(){ return maxVector; }
	public void setMaxVector(float maxVector){ this.maxVector = maxVector; }
	
	// -------------------------------------------- //
	// CONSTRUCTERS
	// -------------------------------------------- //
	
	public JumpSetting()
	{
		vectorPerUnit = (float) 0.1;
		maxVector = (float) 2.0;
	}

	public JumpSetting(float vectorPerUnit, float maxVector)
	{
		this.vectorPerUnit = vectorPerUnit;
		this.maxVector = maxVector;
	}
	
	public JumpSetting(double vectorPerUnit, double maxVector)
	{
		this( (float) vectorPerUnit, (float) maxVector);
	}
	
	public JumpSetting(int vectorPerUnit, int maxVector)
	{
		this( (float) vectorPerUnit, (float) maxVector);
	}
	
	// -------------------------------------------- //
	// CALCULATIONS
	// -------------------------------------------- //
	
	public double getVector(int units)
	{
		return vectorPerUnit*units;
	}
	
	public int getMaxUnits()
	{
		return (int) Math.ceil(maxVector/vectorPerUnit);
	}
	
}
