package dk.muj.derius.parkour;

import java.util.HashMap;
import java.util.Map;

import com.massivecraft.massivecore.MassivePlugin;

public class DeriusAcrobatics extends MassivePlugin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static DeriusAcrobatics i;
	public static DeriusAcrobatics get() { return i; }
	public DeriusAcrobatics() { i = this; }

	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
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
		
		SneakTask.get().activate();
		
		ParkourEngine.get().activate();
		ParkourSkill.get().register();
		JumpAbility.get().register();
		Fall.get().register();
		
		this.postEnable();
	}
}
