package dk.muj.derius.parkour;

import java.util.Optional;

import dk.muj.derius.api.ability.AbilityAbstract;
import dk.muj.derius.api.player.DPlayer;
import dk.muj.derius.api.skill.Skill;
import dk.muj.derius.api.util.LevelUtil;

public class JumpAbility extends AbilityAbstract
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
	public Optional<String> getLvlDescriptionMsg(int lvl)
	{
		Optional<JumpSetting> optSetting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), lvl);
		if ( ! optSetting.isPresent()) return Optional.empty();
		
		JumpSetting setting = optSetting.get();
		
		int maxUnits = setting.getMaxUnits();
		int unitsPerSecond = Const.UNITS_PER_SECOND;
		double secs = Math.ceil(( (double) maxUnits/unitsPerSecond));
		return Optional.of(String.format("<i>Wait time:<h>%ss <i>Potion effect: <h>%s", secs, setting.getMaxPotionLevel()));
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
	public void onDeactivate(DPlayer p, Object other) {}

}
