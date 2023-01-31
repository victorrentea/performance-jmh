package victor.perf;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

public class StreamsNaiveBenchmark {

  public static void main(String[] args) {
    List<Integer> numbers = IntStream.range(0, 10_000).boxed().collect(toList());

    long t0 = currentTimeMillis();

    int sum = 0;
    for (int n : numbers) {
      if (n % 2 == 0) {
        sum += sqrt(n);
      }
    }

//    sum = numbers.stream().filter(n -> n % 2 == 0).mapToInt(n -> (int) sqrt(n)).sum();

    long t1 = currentTimeMillis();

    System.out.println("Got sum = " + sum);
    System.out.println("Took " + (t1 - t0));
  }
}
