package dk.muj.derius.parkour;

import java.util.HashMap;
import java.util.Map;

import com.massivecraft.massivecore.MassivePlugin;

public final class DeriusParkour extends MassivePlugin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static DeriusParkour i;
	public static DeriusParkour get() { return i; }
	private DeriusParkour() { i = this; }

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	//				ID,		Seconds sneaked
	final static Map<String, Short> sneakTime = new HashMap<>();
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void onEnable()
	{
		if ( ! this.preEnable()) return;
		
		EngineParkour.get().activate();
		ParkourSkill.get().register();
		JumpAbility.get().register();
		RunAbility.get().register();
		Fall.get().register();
		
		SneakTask.get().activate();
		
		this.postEnable();
	}
}
