//package victor.perf;
//
//import org.openjdk.jmh.annotations.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//import static java.lang.Math.sqrt;
//import static java.util.stream.Collectors.toList;
//
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@Warmup(iterations = 5, time = 400, timeUnit = TimeUnit.MILLISECONDS)
//@Measurement(iterations = 15, time = 400, timeUnit = TimeUnit.MILLISECONDS)
//@Fork(1)
//public class SplitLoopTest {
//
//   private static final Logger log = LoggerFactory.getLogger(SplitLoopTest.class);
//
//   @Param({"100", "10000"})
//   public int n_items;
//
//   @Param({/*"light",*/ "heavy"})
//   public String cpu_intensity;
//
//   @Param({/*"true",*/ "false"}) // uncomment here to see logging dramatically outweighing the 2nd for overhead
//   public boolean logging;
//
//   private List<Integer> numbers;
//
//   @Setup
//   public void createNumbersList() {
//      numbers = IntStream.range(0, n_items).boxed().collect(toList());
//   }
//
//   @Benchmark
//   public int oneLoop_2things() {
//      int sum2 = 0;
//      int sum3 = 0;
//      for (int n : numbers) {
//         if (n % 2 == 0) {
//            sum2 += cpuOnlyTask(n);
//         }
//         if (n % 3 == 0) {
//            sum3 += cpuOnlyTask(n);
//         }
//         if (logging) {
//            log.info("Element: " + n);
//         }
//      }
//      return sum2 + 17 * sum3;
//   }
//
//   @Benchmark
//   public int twoLoops() {
//      int sum2 = 0;
//      for (int n : numbers) {
//         if (n % 2 == 0) {
//            sum2 += cpuOnlyTask(n);
//         }
//      }
//      int sum3 = 0;
//      for (int n : numbers) {
//         if (n % 3 == 0) {
//            sum3 += cpuOnlyTask(n);
//         }
//         if (logging) {
//            log.info("Element: " + n);
//         }
//      }
//      return sum2 + 17 * sum3;
//   }
//
//   @Benchmark
//   public int twoStreams() {
//      int sum2 = numbers.stream().mapToInt(n -> n).filter(n -> n % 2 == 0).map(this::cpuOnlyTask).sum();
//      int sum3 = numbers.stream().mapToInt(n -> n)
//          .filter(n -> n % 3 == 0)
//          .peek(n -> {
//             if (logging) {
//                log.info("Element: " + n);
//             }
//          })
//          .map(this::cpuOnlyTask)
//          .sum();
//      return sum2 + 17 * sum3;
//   }
//
//
//   public int cpuOnlyTask(int n) {
//      switch (cpu_intensity) {
//         case "light":
//            return (int) sqrt(n);
//         case "heavy":
//            double sum = 0;
//            for (int i = n * 1000; i < (n + 1) * 1000; i++) {
//               sum += sqrt(i);
//            }
//            return (int) sum;
//         default:
//            throw new IllegalStateException("Unexpected value: " + cpu_intensity);
//      }
//   }
//
//
//}
