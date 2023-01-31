//package victor.perf;
//
//import org.openjdk.jmh.annotations.*;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.concurrent.TimeUnit;
//
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
//@Measurement(iterations = 10, time = 200, timeUnit = TimeUnit.MILLISECONDS)
//@Fork(1)
//public class WebClientBenchmark {
//
//   @Benchmark
//   public WebClient webClient() {
//      return WebClient.create();
//   }
//}
