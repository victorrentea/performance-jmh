package victor.perf;

import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Fork(1)
@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 100, timeUnit = MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = MILLISECONDS)
public class Reflection {
  Person person  = new Person("John");

  @Benchmark
  public String getter() {
    return person.getName();
  }

  @Benchmark
  public String field() { // pre-java 5
    return person.name;
  }

  @Benchmark
  public String fieldReflectionDoneBad() throws NoSuchFieldException, IllegalAccessException { // pre-java 5
    return (String) Person.class.getField("name").get(person);
  }


  Field field;
  @Setup
  public void getField() throws NoSuchFieldException {
    field = Person.class.getDeclaredField("name");
  }

  @Benchmark
  public String fieldReflection() throws IllegalAccessException { // pre-java 5
    return (String) field.get(person);
  }

  Method getter;
  @Setup
  public void getMethod() throws NoSuchMethodException {
    getter = Person.class.getDeclaredMethod("getName");
  }

  @Benchmark
  public String methodReflectiveCall() throws IllegalAccessException, InvocationTargetException { // pre-java 5
    return (String) getter.invoke(person);
  }

  static class Person {
    public String name;

    public Person(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

}
