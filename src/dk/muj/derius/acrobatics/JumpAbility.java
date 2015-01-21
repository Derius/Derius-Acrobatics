package dk.muj.derius.acrobatics;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.ability.Ability;
import dk.muj.derius.ability.AbilityType;
import dk.muj.derius.acrobatics.entity.JumpSetting;
import dk.muj.derius.acrobatics.entity.MConf;
import dk.muj.derius.entity.MPlayer;
import dk.muj.derius.skill.Skill;
import dk.muj.derius.util.AbilityUtil;

public class JumpAbility extends Ability
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static JumpAbility i = new JumpAbility();
	public static JumpAbility get() { return i; }
	private JumpAbility()
	{
		super.setDescription("Jump up high");
		
		super.setName("Jump");
		
		super.setType(AbilityType.PASSIVE);
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public int getId()
	{
		return MConf.get().getJumpId();
	}

	@Override
	public String getLvlDescription(int lvl)
	{
		JumpSetting setting = AbilityUtil.getLevelSetting(MConf.get().jumpSteps, lvl);
		return Txt.parse("<i>Wait time:<h>%ss <i>Vector:<h>%s", setting.getMaxUnits()/10, setting.getMaxVector());
	}
	
	@Override
	public Skill getSkill()
	{
		return AcrobaticsSkill.get();
	}
	
	// -------------------------------------------- //
	// OVERRIDE: ACTIVATE
	// -------------------------------------------- //

	@Override
	public Object onActivate(MPlayer mplayer, Object other)
	{
		JumpSetting setting = AbilityUtil.getLevelSetting(MConf.get().jumpSteps, mplayer.getLvl(AcrobaticsSkill.get()));
		Byte unit = DeriusAcrobatics.sneakTime.get(mplayer.getId());
		if (other != null && other instanceof Number) unit = ((Number) other).byteValue();
		
		Vector direction = mplayer.getPlayer().getVelocity().setY(setting.getVector(unit));
		mplayer.getPlayer().setVelocity(direction);
		
		return unit;
	}

	@Override
	public void onDeactivate(MPlayer p, Object other)
	{
		
	}

}
