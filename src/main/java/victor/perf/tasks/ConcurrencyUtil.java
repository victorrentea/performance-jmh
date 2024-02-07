package victor.perf.tasks;

public class ConcurrencyUtil {


	public static int measureCall(Runnable r) {
		long t0 = System.currentTimeMillis();
		r.run();
		long t1 = System.currentTimeMillis();
		return (int) (t1-t0);
	}


}
