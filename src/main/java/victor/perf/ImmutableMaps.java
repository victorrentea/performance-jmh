//package victor.perf;
//
//import com.google.common.collect.ImmutableMap;
//import org.openjdk.jmh.annotations.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//import static com.google.common.collect.ImmutableMap.toImmutableMap;
//import static java.util.stream.Collectors.toList;
//import static java.util.stream.Collectors.toMap;
//
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@Warmup(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
//@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
//@Fork(1)
//public class ImmutableMaps {
//
//    @Param({"10000","100000", "1000000"})
//  int collection_size;
//
//   private List<Integer> numbers;
//
//  @Setup
//  public void createNumbersList() {
//    numbers = IntStream.range(0, collection_size).boxed().collect(toList());
//  }
//
//   @Benchmark
//  public ImmutableMap<Integer, String> immutableMap() {
//     return numbers.stream().collect(toImmutableMap(i -> i, i -> String.valueOf(i)));
//  }
//   @Benchmark
//  public Map<Integer, String> hashMap() {
//     return numbers.stream().collect(toMap(i -> i, i -> String.valueOf(i)));
//  }
//}
