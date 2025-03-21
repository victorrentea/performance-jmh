//package victor.perf;
//
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.infra.Blackhole;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//import static java.util.concurrent.TimeUnit.*;
//import static java.util.stream.Collectors.toList;
//
//@Fork(1)
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime) // uncomment for more percentiles
//@OutputTimeUnit(TimeUnit.NANOSECONDS) // or nano,...
//
//// throw away runs prior to the measurements to allow JIT to optimize code
//@Warmup(iterations = 3, time = 20, timeUnit = MILLISECONDS)
//
//// during which call in a loop the @Benchmarks bellow
//@Measurement(iterations = 5, time = 20, timeUnit = MILLISECONDS)
//public class MDC {
//
//  @Setup
//  public void setup() {
//    org.slf4j.MDC.clear();
//  }
//  @Benchmark
//  public void put() { // pre-java 5
//    org.slf4j.MDC.put("key", "value");
//  }
//  @Benchmark
//  public void remove() { // pre-java 5
//    org.slf4j.MDC.remove("key");
//  }
//
//
//}
