package dk.muj.derius.parkour;

import java.util.Optional;

import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.Skill;
import dk.muj.derius.entity.ability.DeriusAbility;
import dk.muj.derius.util.LevelUtil;

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
		Optional<JumpSetting> setting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), lvl);
		if ( ! setting.isPresent()) return "no jump bonus";
		return String.format("<i>Wait time:<h>%ss <i>Potion:<h>%s", setting.get().getMaxUnits()/Const.UNITS_PER_SECOND, setting.get().getMaxPotionLevel());
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
		// Player player = dplayer.getPlayer();
		
		int potionLevel = setting.getPotionLevel(units);
		
		dplayer.msg(String.valueOf(potionLevel));
		
		return units;
	}

	@Override
	public void onDeactivate(DPlayer p, Object other)
	{
		
	}

}
