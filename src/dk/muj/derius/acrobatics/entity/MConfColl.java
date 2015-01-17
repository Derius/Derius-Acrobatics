package dk.muj.derius.acrobatics.entity;

import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;

import dk.muj.derius.acrobatics.Const;
import dk.muj.derius.acrobatics.DeriusAcrobatics;

public class MConfColl extends Coll<MConf>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MConfColl i = new MConfColl();
	public static MConfColl get() { return i; }
	private MConfColl()
	{
		super(Const.COLLECTION_MCONF, MConf.class, MStore.getDb(), DeriusAcrobatics.get());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	// This initializes the saving task of the class.
	@Override
	public void init()
	{
		super.init();
		MConf.i = this.get("MiningSkill", true);
	}
}