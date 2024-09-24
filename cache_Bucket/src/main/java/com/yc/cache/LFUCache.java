package com.yc.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
    LFU 缓存算法  最少使用算法
    K: 缓存中数据的键的类型
    V: 缓存中数据的值的类型
 */
public class LFUCache<K,V> {
    private final int capacity;  // 缓存的最大容量
    private final Map<K,V> map;  // 存储缓存键值对的映射
    private final Map<K,Integer> freqMap;  // 记录每个键的访问频率
    private final PriorityQueue<K> priorityQueue;  // 优先队列，用于根据访问频率排序键

    /*
        构造函数 构造函数，初始化 LFU 缓存。
        capacity 缓存的最大容量
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        // 按照访问频率升序排序的优先队列 Lambda表达式，比较两个键的访问频率 比较器
        this.priorityQueue = new PriorityQueue<>((k1,k2)-> freqMap.get(k1) - freqMap.get(k2));
    }

    /*
        根据键获取缓存中的值。 如果键不存在，则返回 null。
        每次访问键时，更新键的访问频率。
     */
    public V get(K key) {
        // 检查键是否存在于缓存中
        if (!map.containsKey(key)) {
            return null;
        }
        // 更新键的访问频率
        freqMap.put(key,freqMap.get(key) + 1);
        // 从优先队列中移除旧的键
        priorityQueue.remove(key);
        // 重新排序优先队列
        priorityQueue.offer(key);
        // 返回键对应的值
        return map.get(key);
    }

    /*
        将键值对添加到缓存中。 如果缓存已满，则移除访问频率最低的键值对。
     */
    public void put(K key, V value) {
        //如果缓存容量为 0，直接返回
        if (capacity<=0) return;

        // 如果缓存已满 移除访问频率最低的键值对
        if (map.size() >= capacity) {
            K leastFreqKey = priorityQueue.poll(); // 获取并移除使用频率最少的键
            map.remove(leastFreqKey);  // 从缓存中移除
            freqMap.remove(leastFreqKey); // 移除访问频率最低的键的频率
        }

        // 添加新键值对到缓存中
        map.put(key,value);
        freqMap.put(key,1);  // 新键的访问频率为 1
        priorityQueue.offer(key);  // 将新键添加到优先队列中
    }

}
