package com.thomas.skyrim.tanning.util.intervalTree;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a tree that is optimised to find a node in an interval.
 */
public class IntervalTree<K, V> {
    /**
     *
     */
    private TreeMap<Double, IntervalTree<K, V>> map;

    private Set<V> entries;

    private K key;

    public IntervalTree(List<K> evaluationOrder, Map<V, Map<K, DoubleRange>> valueToCuboid) {
        this(evaluationOrder, valueToCuboid, 0);
    }

    private IntervalTree(List<K> evaluationOrder, Map<V, Map<K, DoubleRange>> valueToCuboid, int index) {
        entries = new HashSet<>(valueToCuboid.keySet());
        this.map = new TreeMap<>();
        if (index < evaluationOrder.size() && entries.size() > 1) {
            key = evaluationOrder.get(index);
            Multimap<DoubleRange, V> rangeForValue = getRangeForValue(valueToCuboid, key);
            TreeMap<Double, Set<V>> order = IntervalTreeUtil.order(rangeForValue);
            for (Double key : order.keySet()) {
                map.put(
                        key,
                        new IntervalTree<>(
                                evaluationOrder,
                                order.get(key).stream().collect(Collectors.toMap(
                                        v -> v,
                                        valueToCuboid::get
                                )),
                                index + 1
                        )
                );
            }
        }
    }

    private Multimap<DoubleRange, V> getRangeForValue(Map<V, Map<K, DoubleRange>> map, K freq) {
        Multimap<DoubleRange, V> result = HashMultimap.create();
        for (V value : map.keySet()) {
            DoubleRange range = map.get(value).get(freq);
            result.put(range, value);
        }
        return result;
    }

    public Set<V> get(Map<K, Double> point) {
        if (isEmpty()) {
            return entries;
        }
        Double value = point.get(key);
        Map.Entry<Double, IntervalTree<K, V>> floorEntry = map.floorEntry(value);
        if (floorEntry == null || floorEntry.getValue().entries.isEmpty()) {
            return findClosest(point);
        }
        return floorEntry.getValue().get(point);
    }

    //an empty interval is always flanked by two non empty intervals.
    private Set<V> findClosest(Map<K, Double> point) {
        if (isFloor(point)) {
            //Problem is, the floor is the start of an interval
            Double floorKey = map.floorKey(point.get(key));
            IntervalTree<K, V> tree = map.lowerEntry(floorKey).getValue();
            return tree.get(point);
        } else {
            IntervalTree<K, V> tree = map.ceilingEntry(point.get(key)).getValue();
            return tree.get(point);
        }
    }

    private boolean isFloor(Map<K, Double> point) {
        Double floor = map.floorKey(point.get(key));
        Double ceil = map.ceilingKey(point.get(key));
        if (floor == null) return false;
        if (ceil == null) return true;
        return floor <= ceil;
    }

    public boolean isEmpty() {
        return map.isEmpty() || key == null;
    }
}

