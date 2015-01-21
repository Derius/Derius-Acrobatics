package dk.muj.derius.acrobatics.entity;

import java.util.Map;

import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;

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
	
	private int jumpId = 42;
	public int getJumpId() { return jumpId; }
	
	public String skillName = "Acrobatics";
	
	public int levelsPerBlock = 50;
	
	public int expPerBlock = 50;
	
	public int sneakMultiplier = 3;
	
	public Map<Integer, JumpSetting> jumpSteps = MUtil.map(
			10, new JumpSetting(1, 0.5)
			);
}
