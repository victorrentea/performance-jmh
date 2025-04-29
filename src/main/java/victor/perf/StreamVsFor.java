package victor.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

@Fork(1)
@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, /* Mode.SampleTime */}) // uncomment for more percentiles
@OutputTimeUnit(TimeUnit.MICROSECONDS) // or nano,...

// throw away runs prior to the measurements to allow JIT to optimize code
@Warmup(iterations = 3, time = 100, timeUnit = MILLISECONDS)

// JMH runs GC between iterations to avoid pauses during experiments

// during which call in a loop the @Benchmarks bellow
@Measurement(iterations = 5, time = 100, timeUnit = MILLISECONDS)
public class StreamVsFor {
  @Param({"100", "10000"})
  private int list_size;

  public enum Cpu {LOW, HEAVY}
  @Param({"LOW", "HEAVY"})
  public Cpu cpu;

  private long cpuTokens;

  private List<Integer> numbers;

  @Setup
  public void createNumbersList() {
    numbers = IntStream.range(0, list_size).boxed().collect(toList());
    cpuTokens = cpu == Cpu.LOW ? 7 : 100;
  }

  @Benchmark
  public int a__for_i() { // pre-java 5
    int sum = 0;
    for (int i = 0; i < numbers.size(); i++) {
      if (numbers.get(i) % 2 == 0) {
        sum += cpuOnlyTask(numbers.get(i));
      }
    }
    return sum;
  }

  @Benchmark
  public int b__for5() {
    int sum = 0;
    for (int n : numbers) {
      if (n % 2 == 0) {
        sum += cpuOnlyTask(n);
      }
    }
    return sum;
  }

  @Benchmark
  public long c__stream() {
    return numbers.stream()
        .filter(n -> n % 2 == 0)
        .mapToInt(n -> cpuOnlyTask(n))
        .sum();
  }

  @Benchmark
  public long d__parallelStream() {
    return numbers.parallelStream()
        .filter(n -> n % 2 == 0)
        .mapToInt(this::cpuOnlyTask)
        .sum();
  }

  private int cpuOnlyTask(int n) {
    Blackhole.consumeCPU(cpuTokens);
    return n;
  }

  @Benchmark
  public void __work_for_one_element() {
    Blackhole.consumeCPU(cpuTokens); // calibrating work/element
  }

}
