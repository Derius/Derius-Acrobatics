package dk.muj.derius.parkour;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;

import dk.muj.derius.api.skill.DeriusSkill;

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
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getId()
	{
		return "derius:parkour";
	}
	

	@Override
	public Plugin getPlugin()
	{
		return DeriusParkour.get();
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
	
	// We get this value atlast 10 times per second.
	// So we cach it and ask for a new value every once in a while.
	public static final transient int STEPS_MAX_ATTEMPTS = 100;
	private transient static int stepsLastCall = STEPS_MAX_ATTEMPTS-1;
	private transient static Map<Integer, JumpSetting> stepCach = new HashMap<>();
	public static Map<Integer, JumpSetting> getJumpSteps()
	{
		// Use the cache
		if (++stepsLastCall % STEPS_MAX_ATTEMPTS == 0)
		{
			stepCach = get().readConfig(Const.JSON_JUMP_STEPS, new TypeToken<Map<Integer, JumpSetting>>(){});
		}
		
		return stepCach;
	}
	
	public static Map<Integer, Float> getSpeedBoosts()
	{
		return get().readConfig(Const.JSON_SPEED_BOOST, new TypeToken<Map<Integer, Float>>(){});
	}
	
	public static short getWaitUnits()
	{
		return get().readConfig(Const.JSON_JUMP_WAIT_UNITS, Short.TYPE);
	}

}
