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
		
		this.setName("Parkour");
		
		this.setIcon(Material.LEATHER_BOOTS);
		
		this.setDesc("Makes you better at jumping, falling & sprinting");
		
		// Setting JSON
		this.writeConfig(Const.JSON_EXP_PER_BLOCK_FALLEN, 50);
		this.writeConfig(Const.JSON_DAMAGE_LESS_SNEAK_MULTIPLIER, 1.5);
		this.writeConfig(Const.JSON_DAMAGE_LESS_PER_LEVEL, 50);
		this.writeConfig(Const.JSON_EXP_SNEAK_MULTIPLIER, 3.0);
		this.writeConfig(Const.JSON_JUMP_STEPS, MUtil.map(10, new JumpSetting(10, 5) ), new TypeToken<Map<Integer, JumpSetting>>(){});
		this.writeConfig(Const.JSON_SPEED_BOOST, MUtil.map(10, (float) 0.1));
		this.writeConfig(Const.JSON_JUMP_WAIT_UNITS, 20);
		this.writeConfig(Const.JSON_UNITS_PER_SECOND, 10);
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
		return get().readConfig(Const.JSON_EXP_PER_BLOCK_FALLEN, Integer.TYPE);
	}
	
	public static int getDamageLessPerLevel()
	{
		return get().readConfig(Const.JSON_DAMAGE_LESS_PER_LEVEL, Integer.TYPE);
	}
	
	public static double getDamageLessSneakMutiplier()
	{
		return get().readConfig(Const.JSON_DAMAGE_LESS_SNEAK_MULTIPLIER, Double.TYPE);
	}
	
	public static double getExpSneakMutiplier()
	{
		return get().readConfig(Const.JSON_EXP_SNEAK_MULTIPLIER, Double.TYPE);
	}
	
	public static Map<Integer, JumpSetting> getJumpSteps()
	{
		return get().readConfig(Const.JSON_JUMP_STEPS, new TypeToken<Map<Integer, JumpSetting>>(){});
	}
	
	public static Map<Integer, Float> getSpeedBoosts()
	{
		return get().readConfig(Const.JSON_SPEED_BOOST, new TypeToken<Map<Integer, Float>>(){});
	}
	
	public static short getWaitUnits()
	{
		return get().readConfig(Const.JSON_JUMP_WAIT_UNITS, Short.TYPE);
	}
	
	public static short getUnitsPerSecond()
	{
		return get().readConfig(Const.JSON_UNITS_PER_SECOND, Short.TYPE);
	}
	
}
