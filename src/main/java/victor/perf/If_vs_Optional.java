//package victor.perf;
//
//import lombok.Data;
//import org.openjdk.jmh.annotations.*;
//import reactor.core.publisher.Flux;
//import reactor.core.scheduler.Schedulers;
//import rx.Observable;
//
//import java.util.Objects;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Stream;
//
//import static java.lang.Math.sqrt;
//
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//@Fork(1)
//public class If_vs_Optional {
//  // https://stackoverflow.com/questions/56235254/optional-vs-if-else-if-performance-java-8
//
//
//  private <T> Optional<T> wrap(T o) {
//    return Optional.ofNullable(o);
//  }
//
//  @Benchmark
//  public String optionalChain() {
//    Message message = new Message();
//    return Stream.of(message.getA(), message.getB(), message.getC(), message.getD())
//        .filter(Objects::nonNull)
//        .findFirst()
//        .orElse(null);
//
//  }
//
//  @Benchmark
//  public Object ifChain() {
//    Message message = new Message();
//    if (message.getA() != null)
//      return message.getA();
//    else if (message.getB() != null)
//      return message.getB();
//    else if (message.getC() != null)
//      return message.getC();
//    else if (message.getD() != null)
//      return message.getD();
//    else return null;
//  }
//
//  @Data
//  public static class Message {
//    String a, b, c, d, e, f, g;
//  }
//}
