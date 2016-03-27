import com.baconnumber.profiler.Profiler;
import com.baconnumber.service.BaconNumberService;
import com.baconnumber.service.BaconNumberServiceGenericImpl;
import com.baconnumber.service.BaconNumberServiceImpl;


public class Main {
	public static void main(String[] args) {
		System.out.println("Start program");
		
		if (args.length == 1) {
			String actor = args[0];
			Profiler.Start(3);
			
			BaconNumberService bns = new BaconNumberServiceImpl();
			
			Profiler.Start(1);
			bns.loadData("data");
			Profiler.End(1, null);
			
			Profiler.Start(2);
			bns.findConnectionToKevinBacon(actor);
			Profiler.End(2, null);
			
			Profiler.End(3, null);
		}
		
		System.out.println("\nEnd program");
	}
}
