package dk.muj.derius.parkour;

import java.util.Map;

import org.bukkit.Material;

import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;

import dk.muj.derius.entity.skill.DeriusSkill;

public class ParkourSkill extends DeriusSkill
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static ParkourSkill i = new ParkourSkill();
	public static ParkourSkill get() { return i; }
	
	
	public ParkourSkill()
	{
		this.addEarnExpDescs("Fall down");
		
		this.setName("parkour");
		
		this.setIcon(Material.LEATHER_BOOTS);
		
		this.setDesc("Makes you better at mining");
		
		// Setting JSON
		this.writeConfig(Const.JSON_EXP_PER_BLOCK_FALLEN, 50);
		this.writeConfig(Const.JSON_DAMAGE_LESS_PER_LEVEL, 50);
		this.writeConfig(Const.JSON_SNEAK_MULTIPLIER, 3.0);
		this.writeConfig(Const.JSON_JUMP_STEPS, MUtil.map( 10, new JumpSetting(0.1, 2) ), new TypeToken<Map<Integer, JumpSetting>>(){});
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getId()
	{
		return "derius:parkour";
	}
	
	// -------------------------------------------- //
	// CONF
	// -------------------------------------------- //
	
	public static int getExpPerBlockFallen()
	{
		return i.readConfig(Const.JSON_EXP_PER_BLOCK_FALLEN, Integer.class);
	}
	
	public static int getDamageLessPerLevel()
	{
		return i.readConfig(Const.JSON_DAMAGE_LESS_PER_LEVEL, Integer.class);
	}
	
	public static double getSneakMutiplier()
	{
		return i.readConfig(Const.JSON_SNEAK_MULTIPLIER, Double.class);
	}
	
	public static Map<Integer, JumpSetting> getJumpSteps()
	{
		return i.readConfig(Const.JSON_JUMP_STEPS, new TypeToken<Map<Integer, JumpSetting>>(){});
	}
	
}