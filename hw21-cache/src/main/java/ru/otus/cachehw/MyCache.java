package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    // Надо реализовать эти методы

    private final WeakHashMap<K, V> weakHashMap;
    private final List<HwListener> listenerList;

    public MyCache() {
        weakHashMap = new WeakHashMap<>();
        listenerList = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        weakHashMap.put(key, value);
        listenerList.forEach(listener -> listener.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        var value = weakHashMap.get(key);
        if (Objects.nonNull(value)) {
            weakHashMap.remove(key);
            listenerList.forEach(listener -> listener.notify(key, value, "remove"));
        }
    }

    @Override
    public V get(K key) {
        return weakHashMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }
}
