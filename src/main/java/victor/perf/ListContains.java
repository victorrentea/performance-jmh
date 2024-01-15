package victor.perf;

import org.jooq.lambda.tuple.Tuple2;
import org.openjdk.jmh.annotations.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS) // arunc ce incalzesc
@Measurement(iterations = 15, time = 200, timeUnit = TimeUnit.MILLISECONDS) // averageuieste aceste 15 segmente
@Fork(1)
public class ListContains {

   private final Random rand = new Random();
   private List<Integer> list = new ArrayList<>();
   private List<Tuple2<Integer,String>> list2 = new ArrayList<>();

   @Setup
   public void createList() {
      list = IntStream.range(0, 1_000_000).boxed().collect(toList());
      list2 = IntStream.range(0, 1_000_000).mapToObj(i->new Tuple2<>(i, "a")).collect(toList());
   }
   @Benchmark
   public boolean contains1M() {
      int r = rand.nextInt(1_000_000);
      return list.contains(r);
   }
   @Benchmark
   public boolean contains1M_tuple() {
      int r = rand.nextInt(1_000_000);
      return list2.stream().anyMatch(t -> t.v1 == r);
   }
}
