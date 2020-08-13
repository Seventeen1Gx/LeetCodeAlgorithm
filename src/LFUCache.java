// 460. LFU缓存
//
// 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。它应该支持以下操作：get 和 put。
//
// get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
// put(key, value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。
// 当缓存达到其容量时，则应该在插入新项之前，使最不经常使用的项无效。
// 在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除最久未使用的键。
//
// 「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。


package src;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    int capacity;

    // 存储键值，为了快速 get
    Map<Integer, Integer> map;

    // 存储键出现频率
    Map<Integer, Node> freq;

    static int timer;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        freq = new HashMap<>(capacity);
    }

    public int get(int key) {
        if (capacity == 0) {
            return -1;
        }

        int ret = -1;
        if (map.containsKey(key)) {
            ret = map.get(key);
        }
        // 不存在也要记录访问频率
        if (freq.containsKey(key)) {
            Node node = freq.get(key);
            node.freq++;
            node.time = timer++;
            freq.put(key, node);
        } else {
            freq.put(key, new Node(1, timer++));
        }
        return ret;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }

        if (map.containsKey(key)) {
            // 更新访问频率
            Node node = freq.get(key);
            node.freq++;
            node.time = timer++;
            freq.put(key, node);
        } else {
            if (map.size() == capacity) {
                // 移除 map 中最不常用的
                int minKey = -1;
                int min = Integer.MAX_VALUE;
                int minTime = Integer.MAX_VALUE;
                for (Integer x : map.keySet()) {
                    Node node = freq.get(x);
                    if (node.freq < min || (node.freq == min && node.time < minTime)) {
                        min = node.freq;
                        minTime = node.time;
                        minKey = x;
                    }
                }
                map.remove(minKey);
                freq.remove(minKey);
            }
            freq.put(key, new Node(1, timer++));
        }
        // 放进去
        map.put(key, value);
    }

    class Node {
        int freq;
        // 最新访问时间
        int time;

        Node(int freq, int time) {
            this.freq = freq;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);
        cache.put(2, 1);
        cache.put(3, 2);
        cache.get(3);
        cache.get(2);
        cache.put(4, 3);
        cache.get(2);
        cache.get(3);
        cache.get(4);
    }
}
