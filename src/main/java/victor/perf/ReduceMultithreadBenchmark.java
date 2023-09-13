package victor.perf;

import org.openjdk.jmh.annotations.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import rx.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class ReduceMultithreadBenchmark {
   private List<Integer> numbers;

   @Setup
   public void createNumbersList() {
      numbers = IntStream.range(0, 1_000_000).boxed().collect(toList());
   }


   // 300
   @Benchmark
   public int sumParallel() {
      return numbers.parallelStream().mapToInt(i->i).sum();
   }

   // 600
   @Benchmark
   public int sumSerial() {
      return numbers.stream().mapToInt(i->i).sum();
   }
   @Benchmark
   public int reduceParallel() {
      return numbers.parallelStream().reduce(0, Integer::sum);
   }

   // 2600
   @Benchmark
   public int reduceSerial() {
      return numbers.stream().reduce(0, Integer::sum);
   }

   // 7000
   @Benchmark
   public int atomicSerial() {
      AtomicInteger atomicInteger = new AtomicInteger();
      numbers.forEach(atomicInteger::addAndGet);
      return atomicInteger.get();
   }

   // 52000
   @Benchmark
   public int atomicParallel() {
      AtomicInteger atomicInteger = new AtomicInteger();
      numbers.parallelStream().forEach(atomicInteger::addAndGet);
      return atomicInteger.get();
   }
}
