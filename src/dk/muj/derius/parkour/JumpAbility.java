package dk.muj.derius.parkour;

import java.util.Optional;

import com.massivecraft.massivecore.util.TimeUnit;

import dk.muj.derius.api.ability.DeriusAbility;
import dk.muj.derius.api.player.DPlayer;
import dk.muj.derius.api.skill.Skill;
import dk.muj.derius.api.util.LevelUtil;

public class JumpAbility extends DeriusAbility
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static JumpAbility i = new JumpAbility();
	public static JumpAbility get() { return i; }
	private JumpAbility()
	{
		this.setDesc("Jump up high");
		
		this.setName("Jump");
		
		this.setType(AbilityType.PASSIVE);
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public String getId()
	{
		return "derius:parkour:jump";
	}

	@Override
	public String getLvlDescriptionMsg(int lvl)
	{
		Optional<JumpSetting> optSetting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), lvl);
		if ( ! optSetting.isPresent()) return "no jump bonus";
		
		JumpSetting setting = optSetting.get();
		
		int maxUnits = setting.getMaxUnits();
		int unitsPerSecond = Const.UNITS_PER_SECOND;
		long millis = (maxUnits/unitsPerSecond) * TimeUnit.MILLIS_PER_SECOND;
		return String.format("<i>Wait time:<h>%ss <i>Potion effect: <h>%s", millis, setting.getMaxPotionLevel());
	}
	
	@Override
	public Skill getSkill()
	{
		return ParkourSkill.get();
	}
	
	// -------------------------------------------- //
	// OVERRIDE: ACTIVATE
	// -------------------------------------------- //

	@Override
	public Object onActivate(DPlayer dplayer, Object other)
	{
		if ( ! dplayer.isPlayer()) return null;
		Optional<JumpSetting> optSetting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), dplayer.getLvl(this.getSkill()));
		if ( ! optSetting.isPresent()) return null;
		JumpSetting setting = optSetting.get();
		Short units = DeriusParkour.sneakTime.get(dplayer.getId());
		if (other != null && other instanceof Number) units = ((Number) other).shortValue();
		
		int potionLevel = setting.getPotionLevel(units);
		
		dplayer.msg(String.valueOf(potionLevel));
		
		return units;
	}

	@Override
	public void onDeactivate(DPlayer p, Object other)
	{
		// Lul
	}

}
