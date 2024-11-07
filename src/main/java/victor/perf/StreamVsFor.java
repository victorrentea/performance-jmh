//package victor.perf;
//
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.infra.Blackhole;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//import static java.util.concurrent.TimeUnit.MILLISECONDS;
//import static java.util.stream.Collectors.toList;
//
//@Fork(1)
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime) // or throughput calls/sec,...
//@OutputTimeUnit(TimeUnit.MICROSECONDS) // or nano,...
//
//// throw away runs prior to the measurements to allow JIT to optimize code
//@Warmup(iterations = 3, time = 100, timeUnit = MILLISECONDS)
// between runs, JMH also invokes GC
//
//// during which call in a loop the @Benchmarks bellow
//@Measurement(iterations = 5, time = 100, timeUnit = MILLISECONDS)
//public class StreamVsFor {
//
//  @Param({"100", "10000"})
//  int collection_size;
//
//  @Param({"LOW", "HEAVY"})
//  Cpu cpu;
//
//  public enum Cpu {LOW, HEAVY}
//
//  long cpuTokens;
//
//  private List<Integer> numbers;
//
//  @Setup
//  public void createNumbersList() {
//    numbers = IntStream.range(0, collection_size).boxed().collect(toList());
//    cpuTokens = cpu == Cpu.LOW ? 10 : 100;
//  }
//
//  @Benchmark
//  public void _0_oneTask() {
//    Blackhole.consumeCPU(cpuTokens); // calibrating a task
//  }
//
//  @Benchmark
//  public int _1_forIndex() { // pre-java 5
//    int sum = 0;
//    for (int i = 0; i < numbers.size(); i++) {
//      if (numbers.get(i) % 2 == 0) {
//        sum += cpuOnlyTask(numbers.get(i));
//      }
//    }
//    return sum;
//  }
//
//  @Benchmark
//  public int _2_for5() {
//    int sum = 0;
//    for (int n : numbers) {
//      if (n % 2 == 0) {
//        sum += cpuOnlyTask(n);
//      }
//    }
//    return sum;
//  }
//
//  @Benchmark
//  public long _3_stream() {
//    return numbers.stream()
//        .filter(n -> n % 2 == 0)
//        .mapToInt(n -> cpuOnlyTask(n))
//        .sum();
//  }
//
//  @Benchmark
//  public long _4_parallelStream() {
//    return numbers.parallelStream()
//        .filter(n -> n % 2 == 0)
//        .mapToInt(this::cpuOnlyTask)
//        .sum();
//  }
//
//  private int cpuOnlyTask(int n) {
//    Blackhole.consumeCPU(cpuTokens);
//    return n;
//  }
//
//}
