package com.baconnumber.profiler;

import java.util.HashMap;

public final class Profiler {
	private static HashMap<Integer, Long> mStartMap;
	
	private Profiler() {}
	
	public static void Start() {
		Start(0);
	}
	
	public static double End(String customString) {
		return End(0, customString);
	}
	
	public static void Start(int id) {
		if (mStartMap == null)
			mStartMap = new HashMap<>();
		
		long start = System.nanoTime();
		mStartMap.put(id, start);
	}
	
	public static double End(int id, String customString) {
		String tag = "[PROFILER]";
		String idStr = "(id=" + id + ")";
		double value = 0;
		
		try {
			long start = mStartMap.get(id).longValue();
			double timeElapsed = (float)(System.nanoTime() - start) / 1000000;
			value = timeElapsed;
			if (customString == null)
				customString = "";
			
			System.out.println(String.format("%s %10s %10.3f ms %3s (%s)", tag, idStr, timeElapsed, "", customString));
		} catch (NullPointerException e) {
			String msg = String.format("Profiler id %d not found!", id);
			System.err.println(String.format("%s %10s %10s %6s %s", tag, idStr, "ERROR", "", msg));
		}
		
		return value;
	}
}