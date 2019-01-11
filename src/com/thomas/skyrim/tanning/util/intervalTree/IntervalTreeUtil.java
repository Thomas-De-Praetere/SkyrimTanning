package com.thomas.skyrim.tanning.util.intervalTree;


import com.google.common.collect.Multimap;
import com.thomas.skyrim.tanning.util.Pair;

import java.util.*;

/**
 *
 */
class IntervalTreeUtil {
    static <V> TreeMap<Double, Set<V>> order(Multimap<DoubleRange, V> map) {
        TreeMap<Double, Set<V>> result = new TreeMap<>();

        PriorityQueue<Pair<DoubleRange, Set<V>>> startSorted = createStartSorted(map);

        PriorityQueue<Pair<Double, Set<V>>> endSorted = new PriorityQueue<>(Comparator.comparingDouble(Pair::getFirst));

        while (!(startSorted.isEmpty() && endSorted.isEmpty())) {
            if (isStartFirst(startSorted, endSorted)) {
                Pair<DoubleRange, Set<V>> poll = startSorted.poll();
                DoubleRange range = poll.getFirst();
                Set<V> values = poll.getSecond();

                endSorted.add(Pair.of(range.getEnd(), values));

                addToResult(result, range.getStart(), values);
            } else {
                if (!endSorted.isEmpty()) {
                    Pair<Double, Set<V>> poll = endSorted.poll();
                    Double end = poll.getFirst();
                    Set<V> values = poll.getSecond();
                    removeFromResult(result, end, values);
                }
            }
        }

        return result;
    }

    private static <V> void addToResult(TreeMap<Double, Set<V>> result, double start, Set<V> values) {
        addKeyToResult(result, start);
        result.get(start).addAll(values);
    }

    private static <V> void removeFromResult(TreeMap<Double, Set<V>> result, double end, Set<V> values) {
        addKeyToResult(result, end);
        result.get(end).removeAll(values);
    }

    private static <V> void addKeyToResult(TreeMap<Double, Set<V>> result, double key) {
        if (!result.containsKey(key)) {
            Map.Entry<Double, Set<V>> entry = result.floorEntry(key);
            Set<V> presentValues = new HashSet<>();
            if (entry != null) presentValues = entry.getValue();
            result.put(key, new HashSet<>(presentValues));
        }
    }

    private static <V> boolean isStartFirst(PriorityQueue<Pair<DoubleRange, Set<V>>> startSorted, PriorityQueue<Pair<Double, Set<V>>> endSorted) {
        if (startSorted.isEmpty()) return false;
        if (endSorted.isEmpty()) return true;
        return startSorted.peek().getFirst().getStart() < endSorted.peek().getFirst();
    }

    private static <V> PriorityQueue<Pair<DoubleRange, Set<V>>> createStartSorted(Multimap<DoubleRange, V> map) {
        PriorityQueue<Pair<DoubleRange, Set<V>>> startSorted = new PriorityQueue<>(Comparator.comparingDouble(p -> p.getFirst().getStart()));
        for (DoubleRange range : map.keySet()) {
            startSorted.add(Pair.of(range, new HashSet<>(map.get(range))));
        }
        return startSorted;
    }
}
