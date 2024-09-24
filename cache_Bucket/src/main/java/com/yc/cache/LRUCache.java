package com.yc.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/*
    LRU 缓存算法  最近最少使用算法
    K: 缓存中数据的键的类型
    V: 缓存中数据的值的类型
    使用泛型  实现缓存算法  提高代码的复用性和类型安全性
 */
public class LRUCache<K,V> {
    private final int capacity;  // 缓存的最大容量
    private final LinkedHashMap<K,V> linkedHashMap;  // 双向链表，用于记录键的访问顺序
    /*
        构造函数 构造函数，初始化 LRU 缓存。
        capacity 缓存的最大容量
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // 初始化 LinkedHashMap，并设置访问顺序为 true 以支持 LRU 策略 表示按访问顺序排序
        this.linkedHashMap = new LinkedHashMap<>(capacity, 0.75f, true) {
            // 当缓存的元素个数超过最大容量时，移除最久未使用的元素
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }
    /*
       将指定的键值对添加到缓存中。如果缓存已满，最久未使用的元素将被淘汰。
     */
    public void put(K key, V value) {
        linkedHashMap.put(key, value);
    }
    /*
        根据指定的键获取缓存中的值。如果键不存在，则返回 null。
     */
    public V get(K key) {
        return linkedHashMap.get(key);
    }

}
