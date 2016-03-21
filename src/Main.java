import com.baconnumber.service.BaconNumberServiceImpl;


public class Main {
	public static void main(String[] args) {
		System.out.println("Start program");
		
		if (args.length == 1) {
			String actor = args[0];
			
			BaconNumberServiceImpl bns = new BaconNumberServiceImpl();
			bns.findConnectionToKevinBacon(actor);
		}
		
		System.out.println("\nEnd program");
	}
}
