package victor.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class SplitLoop {
   @Param({"100", "10000"})
   public int n_items;

   private List<Integer> numbers;

   @Setup
   public void createList() {
      numbers = IntStream.range(0, n_items).boxed().collect(toList());
   }

   @Benchmark
   public void one_loop(Blackhole blackhole) {
      for (int n : numbers) {
         blackhole.consume(n);
      }
   }
   @Benchmark
   public void two_loops(Blackhole blackhole) {
      for (int n : numbers) {
         blackhole.consume(n);
      }
      for (int n : numbers) {
         blackhole.consume(n);
      }
   }
   @Benchmark
   public void one_stream(Blackhole blackhole) {
      numbers.forEach(e->blackhole.consume(e));
   }

   @TearDown
   public void method() {
      System.out.println("Please sample the actual time to bring this list from the DB/API with p6spy, @Timed, t1-t0");
   }
}
