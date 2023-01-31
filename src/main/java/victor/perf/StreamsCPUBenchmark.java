package victor.perf;

import org.openjdk.jmh.annotations.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import rx.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class StreamsCPUBenchmark {


   @Param({"100", "10000"/*, "1000000"*/})
    int n_items;

   @Param({"light", "heavy"})
    String cpu_intensity;

   private List<Integer> numbers;

   @Setup
   public void createNumbersList() {
      numbers = IntStream.range(0, n_items).boxed().collect(toList());
   }


   // TODO copiaza-ti aici bucata de cod Java despre care CREZI ca poti sa o faci tu muritor de rand mai rapida. si JIT nu poate.
   //  KO colegului
   @Benchmark
   public int forIndex() {
      int sum = 0;
      for (int i = 0, numbersSize = numbers.size(); i < numbersSize; i++) {
         int n = numbers.get(i);
         if (n % 2 == 0) {
            sum += cpuOnlyTask(n);
         }
      }
      return sum;
   }
   @Benchmark
   public int for5() {
      int sum = 0;
      for (int n : numbers) {
         if (n % 2 == 0) {
            sum += cpuOnlyTask(n);
         }
      }
      return sum;
   }

   @Benchmark
   public long stream() {
      return numbers.stream()
          .filter(n -> n % 2 == 0)
          .mapToInt(this::cpuOnlyTask)
          .sum();
   }

   @Benchmark
   public long streamParallel() {
      return numbers.parallelStream()
          .filter(n -> n % 2 == 0)
          .mapToInt(this::cpuOnlyTask)
          .sum();
   }

   //   @Benchmark
   public Long fluxSingleThread() {
      return Flux.fromIterable(numbers)
          .filter(n -> n % 2 == 0)
          .map(this::cpuOnlyTask)
          .count()
          .block();
   }

   //   @Benchmark
   public Long fluxParallelThread() {
      return Flux.fromIterable(numbers)
          .parallel()
          .runOn(Schedulers.parallel())
          .map(this::cpuOnlyTask)
          .sequential()
          .count()
          .block();
   }

   //   @Benchmark
   public Integer observableSingleThread() {
      return Observable.from(numbers)
          .map(this::cpuOnlyTask)
          .count()
          .toSingle()
          .toBlocking()
          .value();
   }

   public int cpuOnlyTask(int n) {
//      System.out.println(Thread.currentThread().getName());
      switch (cpu_intensity) {
         case "light":
            return (int) sqrt(n);
         case "heavy":
            double sum = 0;
            for (int i = n * 1000; i < (n + 1) * 1000; i++) {
               sum += sqrt(i);
            }
            return (int) sum;
         default:
            throw new IllegalStateException("Unexpected value: " + cpu_intensity);
      }
   }


//   public static void main(String[] args) {
//      new StreamsCPUOnlyTest().m();
//   }
//   public void  m() {
//      n_items=10000;
//      createNumbersList();
//      cpu_intensity = "heavy";
//      System.out.println(Flux.fromIterable(numbers)
//          .parallel()
//          .runOn(Schedulers.parallel())
//          .map(this::cpuOnlyTask)
//          .sequential()
//          .count()
//          .block());
//   }
}
