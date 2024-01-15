//package victor.perf;
//
//import lombok.Data;
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.infra.Blackhole;
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
//@Measurement(iterations = 10, time = 200, timeUnit = TimeUnit.MILLISECONDS)
//@Fork(1)
//public class IteratingMaps {
//  // https://stackoverflow.com/questions/56235254/optional-vs-if-else-if-performance-java-8
//
//
//  @Param({"10000", "1000000"/*, "1000000"*/})
//  int n_items;
//  private ArrayList<Integer> list;
//  private HashSet<Integer> set;
//
//  @Setup
//  public void createCollections() {
//    list = new ArrayList<>(IntStream.range(0,n_items).boxed().collect(Collectors.toList()));
//    set = new HashSet<>(IntStream.range(0,n_items).boxed().collect(Collectors.toList()));
//  }
//;
//  @Benchmark
//  public void iterateList(Blackhole blackhole) {
//    for (Integer i : list) {
//      blackhole.consume(i);
//    }
//  }
//  @Benchmark
//  public void iterateSet(Blackhole blackhole) {
//    for (Integer i : set) {
//      blackhole.consume(i);
//    }
//  }
//
//}
