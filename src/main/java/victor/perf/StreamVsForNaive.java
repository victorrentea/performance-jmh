package victor.perf;

import java.util.List;
import java.util.stream.IntStream;

public class StreamVsForNaive {
  public static void main(String[] args) {
    // create the collection before the bench
    List<Integer> numbers = IntStream.range(0, 10000).boxed().toList();

    long start = System.currentTimeMillis();
    long sum = 0;
    for (int element : numbers) {
      sum += element;
    }
    System.out.println("For: " + (System.currentTimeMillis() - start) + "ms");
    System.out.println("Sum1: " + sum);

    start = System.currentTimeMillis();
    sum = numbers.stream().mapToInt(i -> i).sum();
    System.out.println("Stream: " + (System.currentTimeMillis() - start) + "ms");
    System.out.println("Sum2: " + sum);
  }
}
// issues:
// - no of iterations
// - GC
// - order of experiments
// - lack of warmup
// - no repeated runs