package dk.muj.derius.acrobatics.entity;

import com.massivecraft.massivecore.store.Entity;

public class MConf extends Entity<MConf>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	protected static transient MConf i;
	public static MConf get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private int skillId = 40;
	public int getSkillId() { return skillId; }
	
	private int fallId = 41;
	public int getFallId() { return fallId; }
	
	private int dodgeId = 42;
	public int getDodgeId() { return dodgeId; }
	
	public String skillName = "Acrobatics";
	
	public int levelsPerBlock = 50;
	
	public int expPerBlock = 50;
}
