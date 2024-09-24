package com.yc.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
    FIFO 先进先出  缓存算法  队列实现
    K: 缓存中数据的键的类型
    V: 缓存中数据的值的类型
    使用泛型  实现缓存算法  提高代码的复用性和类型安全性
 */
public class FIFOCache<K,V> {
    private final int capacity;  // 缓存的最大容量
    private final Queue<K> cache;  // 存储缓存键的队列 使用LinkedList实现
    private final Map<K,V> map;  // 存储缓存键值对的映射

    /*
        构造函数 构造函数，初始化 FIFO 缓存。
        capacity 缓存的最大容量
     */
    public  FIFOCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedList<>();
        this.map = new HashMap<>();
    }

    /*
        根据键获取缓存中的值。 如果键不存在，则返回 null。
     */
    public V get(K key) {
        return map.get(key);
    }
    /*
        将键值对添加到缓存中。 如果缓存已满，则移除最早添加的键值对。
     */
    public void put(K key, V value) {
        //如果缓存已满 移除最早添加的键值对
        if (cache.size() >= capacity) {
            K oldestKey = cache.poll(); // 移除最早添加的键
            map.remove(oldestKey); // 移除最早添加的键值对
        }
        cache.offer(key); // 添加新键到队列末尾
        map.put(key, value); // 添加新键值对到映射中
    }
}
