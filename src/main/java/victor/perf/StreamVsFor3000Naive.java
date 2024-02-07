package victor.perf;

import static java.lang.System.currentTimeMillis;

public class StreamVsFor3000Naive {
  public static void main(String[] args) {
    System.out.println("Naive benchmark...");
    StreamVsFor3000 sut = new StreamVsFor3000();
    sut.collection_size = 100;
    sut.setup();
    measureFors(sut);
    measureStreams(sut);
  }

  private static void measureFors(StreamVsFor3000 sut) {
    long t0 = currentTimeMillis();
    sut.fors();
    long t1 = currentTimeMillis();
    System.out.println("fors took " + (t1 - t0) + " ms");
  }

  private static void measureStreams(StreamVsFor3000 sut) {
    long t0 = currentTimeMillis();
    sut.streams();
    long t1 = currentTimeMillis();
    System.out.println("streams took " + (t1 - t0) + " ms");
  }
}
