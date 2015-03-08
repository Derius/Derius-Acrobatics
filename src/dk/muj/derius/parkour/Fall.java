package dk.muj.derius.parkour;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import dk.muj.derius.api.ability.DeriusAbility;
import dk.muj.derius.api.player.DPlayer;
import dk.muj.derius.api.skill.Skill;

public class Fall extends DeriusAbility
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static Fall i = new Fall();
	public static Fall get() { return i; }
	
	public Fall()
	{
		this.setDesc("Reduces fall damage");
		
		this.setName("Roll");
		
		this.setType(AbilityType.PASSIVE);
		
		this.setCooldownMillis(-1);
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
	public Object onActivate(DPlayer dplayer, Object other)
	{
		if ( ! (other instanceof EntityDamageEvent)) return null;
		EntityDamageEvent event = (EntityDamageEvent) other;
		
		if (event.getCause() != DamageCause.FALL) return null;
		
		final double originalDamage = event.getFinalDamage();
		
		int level = dplayer.getLvl(this.getSkill());
		
		double damageReduce = originalDamage - ( (double) level / ParkourSkill.getDamageLessPerLevel());
		
		if (dplayer.isPlayer() && dplayer.getPlayer().isSneaking())
		{
			damageReduce *= ParkourSkill.getDamageLessSneakMutiplier();
		}
		
		// We use this method because standard bukkit is buggy.
		MUtil.setDamage(event, originalDamage-damageReduce);
		if (event.getDamage() <= 0) event.setCancelled(true);
		
		return event.getDamage();
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
