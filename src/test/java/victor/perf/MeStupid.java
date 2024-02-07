package victor.perf;

import static java.lang.System.currentTimeMillis;

public class MeStupid {
  public static void main(String[] args) {
    long t0 = currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      System.out.println("I'm stupid");
    }
    long t1 = currentTimeMillis();
    System.out.println("Took " + (t1 - t0) + "ms"); //  6,7ms
  }
}
