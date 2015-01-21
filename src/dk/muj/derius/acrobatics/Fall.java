package dk.muj.derius.acrobatics;

import java.util.Optional;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.ability.Ability;
import dk.muj.derius.ability.AbilityType;
import dk.muj.derius.acrobatics.entity.MConf;
import dk.muj.derius.entity.MPlayer;
import dk.muj.derius.events.PlayerDamageEvent;
import dk.muj.derius.skill.Skill;

public class Fall extends Ability
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static Fall i = new Fall();
	public static Fall get() { return i; }
	
	public Fall()
	{
		super.setDescription("Reduces fall damage");
		
		super.setName("Roll");
		
		super.setType(AbilityType.PASSIVE);
	}
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	@Override
	public int getId()
	{
		return MConf.get().getFallId();
	}

	@Override
	public String getLvlDescription(int lvl)
	{
		return Txt.parse("Fall %s blocks without taking damage", lvl/MConf.get().levelsPerBlock + 3);
	}

	@Override
	public Object onActivate(MPlayer p, Object other)
	{
		if ( ! (other instanceof PlayerDamageEvent)) return Optional.empty();
		PlayerDamageEvent event = (PlayerDamageEvent) other;
		
		if (event.getInnerEvent().getCause() != DamageCause.FALL) return Optional.empty();
		
		double damage = event.getInnerEvent().getFinalDamage();
		
		p.msg("<lime>Damage: <i>"+damage);
		
		damage -= p.getLvl(this.getSkill())/MConf.get().levelsPerBlock;
		
		p.msg("<lime>Damage: <i>"+damage);
		
		event.getInnerEvent().setDamage(damage);
		
		if (damage <= 0) event.setCancelled(true);
		
		return Optional.of(new Double(damage));
	}

	@Override
	public void onDeactivate(MPlayer p, Object other)
	{
		// LELNOPE
		
	}

	@Override
	public Skill getSkill()
	{
		return AcrobaticsSkill.get();
	}
	
}
