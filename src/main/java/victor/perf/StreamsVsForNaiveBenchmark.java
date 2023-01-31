package victor.perf;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;

public class StreamsVsForNaiveBenchmark {

  public static void main(String[] args) {
    StreamsCPUOnlyTest obj = new StreamsCPUOnlyTest();
    obj.n_items = 10_000;
    obj.cpu_intensity="light";
    obj.createNumbersList();

    long t0 = currentTimeMillis();

    long x = obj.stream();
//    long x = obj.forClassic();

    long t1 = currentTimeMillis();

    System.out.println("Took " +(t1-t0));
  }
}
