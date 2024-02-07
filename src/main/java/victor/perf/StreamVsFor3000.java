package victor.perf;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.groupingBy;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(MILLISECONDS)
@Warmup(iterations = 10, time = 100, timeUnit = MILLISECONDS)
@Measurement(iterations = 15, time = 50, timeUnit = MILLISECONDS)
@Fork(1)
public class StreamVsFor3000 {
//[100]Slot ->[6] Level ->[5] Award (got from a HashMap.size=200k)  = 3000 elements
// rps=30/sec

//  Map<AwardValue, List<Award>> streams(List<AcademyUserSlot> slotsContainingItem, int gameSpaceId) {
//    return slotsContainingItem._3_stream()
//        .flatMap(academyUserSlot -> getStreamOfLevelsToReward(gameSpaceId, academyUserSlot))
//        .flatMap(academyLevel -> getAwardList(academyLevel)._3_stream())
//        .collect(groupingBy(Award::getAwardValue));
//  }
  Map<Slot, List<Level>> levelCache = new HashMap<>();
  Map<Level, List<Award>> awardCache = new HashMap<>();
  List<Slot> slots = new ArrayList<>();

  @Param({"100"/*, "200"*/})
  int collection_size;
  @Setup
  public void setup() {
    AwardValue[] av = IntStream.range(0, 5)
        .mapToObj(i -> new AwardValue())
        .toArray(AwardValue[]::new);

    int ii = 0;
    for (int i = 0; i < collection_size; i++) {
      Slot slot = new Slot();
      slots.add(slot);
      List<Level> levels = new ArrayList<>();
      for (int j = 0; j < 6; j++) {
        Level level = new Level();
        levels.add(level);
        List<Award> awards = new ArrayList<>();
        for (int k = 0; k < 5; k++) {
          Award award = new Award(av[(ii++) % av.length]);
          awards.add(award);
        }
        awardCache.put(level, awards);
      }
      levelCache.put(slot, levels);
    }
  }

  static class AwardValue {}
  static class Award {
    private final AwardValue awardValue;
    Award(AwardValue awardValue) {
      this.awardValue = awardValue;
    }
    AwardValue getAwardValue() {
      return awardValue;
    }
  }
  static class Slot {}
  static class Level {}

  @Benchmark
  // return a map with 5 keys->List.size=600
  public Map<AwardValue, List<Award>> streams() {
    return slots.stream()
        .flatMap(slot -> levelCache.get(slot).stream())
        .flatMap(level -> awardCache.get(level).stream())
        .collect(groupingBy(Award::getAwardValue));
  }

  @Benchmark
  public Map<AwardValue, List<Award>> fors() {
    Map<AwardValue, List<Award>> collect = new HashMap<>();
    for (Slot slot : slots) {
      for (Level level : levelCache.get(slot)) {
        for (Award award : awardCache.get(level)) {
          collect.computeIfAbsent(award.getAwardValue(), k -> new java.util.ArrayList<>()).add(award);
        }
      }
    }
    return collect;
  }


}