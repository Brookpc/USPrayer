
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

public class PrayAlter implements Strategy {

	@Override
	public boolean activate() {
		if(Inventory.getCount(USPrayer.useBone) >= 1)
			return true;
		return false;
	}

	@Override
	public void execute() {
		SceneObject[] altarID = SceneObjects.getNearest(409);

		if (altarID.length > 0 && altarID[0] != null
				&& Inventory.getCount(USPrayer.useBone) >= 1
				&& Players.getMyPlayer().getAnimation() == -1) {
			
			if ( altarID[0].distanceTo() > 3) {
			System.out.println("Walking to altar...");
			altarID[0].getLocation().walkTo();
			Time.sleep(2000);
			}
			//Use
			Menu.sendAction(447, USPrayer.useBone - 1, getInventorySlot(USPrayer.useBone), 3214, 409, 3);
			Time.sleep(400);
			//On Alter
			Menu.sendAction(62, altarID[0].getHash(), altarID[0].getLocalRegionX(), altarID[0].getLocalRegionY(),409,1);
			Time.sleep(400);
		}
	}
	
	private static int getInventorySlot(int item) {
		org.rev317.min.api.wrappers.Item[] items = Inventory.getItems(item);
		if (items != null) {
			return items[0].getSlot();
		}
		return 0;
	}

}
