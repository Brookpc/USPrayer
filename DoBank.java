
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;

public class DoBank implements Strategy {

	@Override
	public boolean activate() {
		if(Inventory.getCount(USPrayer.useBone) == 0)
			return true;
		return false;
	}

	@Override
	public void execute() {
		if (Game.getOpenInterfaceId() == 5292) {
			System.out.println("Getting Bones....");
			Bank.withdraw(USPrayer.useBone, 28, 150);
			Time.sleep(400);
			Menu.sendAction(200, 20905984, 50, 5384);
			Time.sleep(400);
			Time.sleep(new SleepCondition() {
				@Override
				public boolean isValid() {
					return Game.getOpenInterfaceId() == 5292;
				}
			}, 500);
		} else {
			SceneObject[] boothID = SceneObjects.getNearest(26972);
			if (boothID.length > 0 && boothID != null
					&& boothID[0].distanceTo() > 0
					&& Players.getMyPlayer().getAnimation() == -1) {
				boothID[0].interact(1);
				Time.sleep(new SleepCondition() {
					@Override
					public boolean isValid() {
						return Players.getMyPlayer().getAnimation() != -1;
					}
				}, 500);
			}
		}
	}

}
