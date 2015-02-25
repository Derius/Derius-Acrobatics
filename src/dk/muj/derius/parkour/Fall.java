package dk.muj.derius.parkour;

import java.util.Optional;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.api.DPlayer;
import dk.muj.derius.api.Skill;
import dk.muj.derius.entity.ability.DeriusAbility;
import dk.muj.derius.events.player.PlayerDamageEvent;

public class Fall extends DeriusAbility
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static Fall i = new Fall();
	public static Fall get() { return i; }
	
	public Fall()
	{
		super.setDesc("Reduces fall damage");
		
		super.setName("Roll");
		
		super.setType(AbilityType.PASSIVE);
	}
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	@Override
	public String getId()
	{
		return "derius:parkour:fall";
	}

	@Override
	public String getLvlDescriptionMsg(int lvl)
	{
		return Txt.parse("<i>Fall <h>%s <i>blocks without taking damage", lvl/ParkourSkill.getDamageLessPerLevel() + 3);
	}

	@Override
	public Object onActivate(DPlayer p, Object other)
	{
		if ( ! (other instanceof PlayerDamageEvent)) return Optional.empty();
		PlayerDamageEvent event = (PlayerDamageEvent) other;
		
		if (event.getInnerEvent().getCause() != DamageCause.FALL) return Optional.empty();
		
		double damage = event.getInnerEvent().getFinalDamage();
		
		p.msg("<lime>Damage: <i>"+damage);
		
		damage -= p.getLvl(this.getSkill())/ParkourSkill.getDamageLessPerLevel();
		
		p.msg("<lime>Damage: <i>"+damage);
		
		event.getInnerEvent().setDamage(damage);
		
		if (damage <= 0) event.setCancelled(true);
		
		return Optional.of(new Double(damage));
	}

	@Override
	public void onDeactivate(DPlayer p, Object other)
	{
		// LELNOPE
		
	}

	@Override
	public Skill getSkill()
	{
		return ParkourSkill.get();
	}
	
}
