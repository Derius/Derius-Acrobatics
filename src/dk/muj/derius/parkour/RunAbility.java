package dk.muj.derius.parkour;

import org.bukkit.entity.Player;

import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.Skill;
import dk.muj.derius.entity.ability.DeriusAbility;
import dk.muj.derius.util.LevelUtil;

public class RunAbility extends DeriusAbility
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static RunAbility i = new RunAbility();
	public static RunAbility get() { return i; }
	private RunAbility()
	{
		super.setDesc("Run faster");
		
		super.setName("Run");
		
		super.setType(AbilityType.PASSIVE);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Skill getSkill()
	{
		return ParkourSkill.get();
	}
	
	@Override
	public String getId()
	{
		return "derius:parkour:run";
	}
	
	@Override
	public String getLvlDescriptionMsg(int level)
	{
		float speed = getPlayerSpeed(level);
		float bonus = speed / Const.DEFAULT_WALK_SPEED;
		if ( ((int) (bonus * 100)) == 100) return "<i>none";
		return String.format("%.2f <i>times faster than the normal", bonus);
	}
	
	@Override
	public Object onActivate(DPlayer dplayer, Object other)
	{
		if ( ! dplayer.isPlayer()) return null;
		Player player = dplayer.getPlayer();
		
		float speed;
		
		if (other instanceof Number)
		{
			speed =  ((Number) other).floatValue();
		}
		else
		{
			speed = getPlayerSpeed(dplayer.getLvl(this.getSkill()));
		}
		
		player.setWalkSpeed(speed);
		
		return speed;
	}
	
	@Override
	public void onDeactivate(DPlayer p, Object other)
	{
		
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static float getPlayerSpeed(int level)
	{
		double ret = LevelUtil.getLevelSettingFloat(ParkourSkill.getSpeedBoosts(), level).orElse(Const.DEFAULT_WALK_SPEED);
		return (float) ret;
	}
	
	
}
