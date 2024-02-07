package victor.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class StreamVsFor {

  @Param({"100", "10000"})
  int collection_size;

  @Param({"LOW", "HEAVY"})
  Cpu cpu;

  public enum Cpu {LOW, HEAVY}

  long cpuTokens;

  private List<Integer> numbers;

  @Setup
  public void createNumbersList() {
    numbers = IntStream.range(0, collection_size).boxed().collect(toList());
    cpuTokens = cpu == Cpu.LOW ? 10 : 100;
  }

  @Benchmark
  public void _0_taskForOneItem() {
    Blackhole.consumeCPU(cpuTokens);
  }


  @Benchmark
  public int _1_forIndex() {
    int sum = 0;
    for (int i = 0; i < numbers.size(); i++) {
      if (numbers.get(i) % 2 == 0) {
        sum += cpuOnlyTask(numbers.get(i));
      }
    }
    return sum;
  }

  @Benchmark
  public int _2_for5() {
    int sum = 0;
    for (int n : numbers) {
      if (n % 2 == 0) {
        sum += cpuOnlyTask(n);
      }
    }
    return sum;
  }

  @Benchmark
  public long _3_stream() {
    return numbers.stream()
        .filter(n -> n % 2 == 0)
        .mapToInt(n -> cpuOnlyTask(n))
        .sum();
  }

  @Benchmark
  public long _4_parallelStream() {
    return numbers.parallelStream()
        .filter(n -> n % 2 == 0)
        .mapToInt(this::cpuOnlyTask)
        .sum();
  }

  private int cpuOnlyTask(int n) {
    Blackhole.consumeCPU(cpuTokens);
    return n;
  }

}
