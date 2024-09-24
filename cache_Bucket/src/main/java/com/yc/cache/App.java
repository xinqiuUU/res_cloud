package com.yc.cache;

import com.yc.cache.FIFOCache;
import com.yc.cache.LFUCache;
import com.yc.cache.LRUCache;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.out.println( "FIFO缓存:" );
        FIFOCache<String, Integer> intCache = new FIFOCache<>(2);
        intCache.put("One", 1);
        intCache.put("Two", 2);
        intCache.put("Three", 3);
        System.out.println("One:"+intCache.get("One"));  // 输出 null，因为 "One" 已被移除
        System.out.println("Two:"+intCache.get("Two"));  // 输出 2
        System.out.println("Three:"+intCache.get("Three"));  // 输出 3

        System.out.println("-----------------------------------------------------");
        System.out.println( "LFU缓存:" );
        // 创建一个容量为 3 的 LFU 缓存
        LFUCache<String, Integer> cache = new LFUCache<>(3);
        // 插入一些键值对
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        // 访问键 "A" 和 "B"，增加它们的使用频率
        System.out.println("获取 A: " + cache.get("A")); // 输出 1
        System.out.println("获取 B: " + cache.get("B")); // 输出 2
        // 插入新键 "D"，此时缓存已满，应该移除频率最低的 "C"
        cache.put("D", 4);
        System.out.println("添加 D: " + 4 + "，键 C 已被移除");
        // 检查缓存中有哪些键
        System.out.println("获取 A: " + cache.get("A")); // 输出 1
        System.out.println("获取 B: " + cache.get("B")); // 输出 2
        System.out.println("获取 C: " + cache.get("C")); // 输出 null，因为 "C" 已被移除
        System.out.println("获取 D: " + cache.get("D")); // 输出 4

        System.out.println("-----------------------------------------------------");
        System.out.println("LRU缓存:");
        LRUCache<Integer, String> lruCache = new LRUCache<>(3);
        lruCache.put(1, "One");
        lruCache.put(2, "Two");
        lruCache.put(3, "Three");
        // 获取键为 2 的值，访问操作会将其移动到最近使用的位置
        System.out.println("获取 1: " + lruCache.get(1));  // 输出 "One"
        System.out.println("获取 2: " + lruCache.get(2));  // 输出 "Two"
        System.out.println("获取 3: " + lruCache.get(3));  // 输出 "Three"
        // 添加一个新元素，将导致键 1 被移除（因为它是最久未使用的）
        lruCache.put(4, "Four");
        System.out.println("添加 4: " + "Four" + "，键 1 已被移除");
        // 打印缓存内容，注意键 1 应该被移除
        System.out.println("获取 1: " + lruCache.get(1));  // 输出 null，因为 "One" 已被移除
        System.out.println("获取 2: " + lruCache.get(2));  // 输出 "Two"
        System.out.println("获取 3: " + lruCache.get(3));  // 输出 "Three"
        System.out.println("获取 4: " + lruCache.get(4));  // 输出 "Four"
    }
}
