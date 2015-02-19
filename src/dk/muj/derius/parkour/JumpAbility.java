package dk.muj.derius.parkour;

import java.util.Optional;

import org.bukkit.util.Vector;

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
		super.setDesc("Jump up high");
		
		super.setName("Jump");
		
		super.setType(AbilityType.PASSIVE);
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
		return String.format("<i>Wait time:<h>%ds <i>Vector:<h>%d", setting.get().getMaxUnits()/10.0, setting.get().getMaxVector());
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
	public Object onActivate(DPlayer mplayer, Object other)
	{
		Optional<JumpSetting> setting = LevelUtil.getLevelSetting(ParkourSkill.getJumpSteps(), mplayer.getLvl(ParkourSkill.get()));
		if ( ! setting.isPresent()) return null;
		Short unit = DeriusAcrobatics.sneakTime.get(mplayer.getId());
		if (other != null && other instanceof Number) unit = ((Number) other).shortValue();
		
		Vector direction = mplayer.getPlayer().getVelocity().setY(setting.get().getVector(unit));
		mplayer.getPlayer().setVelocity(direction);
		
		return unit;
	}

	@Override
	public void onDeactivate(DPlayer p, Object other)
	{
		
	}

}
