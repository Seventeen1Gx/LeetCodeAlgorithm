// 146. LRU 缓存机制
//
// 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
//
// 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
// 写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。
// 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。


package src;

public class LRUCache {

    Node[] container;

    int size;

    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        container = new Node[capacity];
        size = 0;
    }

    public int get(int key) {
        for (int i = 0; i < size; i++) {
            if (container[i].key == key) {
                Node node = container[i];
                // 将刚访问的这个 node 移到数组末尾
                update(i);
                container[size - 1] = node;
                return node.value;
            }
        }
        return -1;
    }

    public void put(int key, int value) {
        for (int i = 0; i < size; i++) {
            if (container[i].key == key) {
                Node node = container[i];
                update(i);
                container[size - 1] = node;
                node.value = value;
                return;
            }
        }
        if (size == capacity) {
            update(0);
            container[size - 1] = new Node(key, value);
        } else {
            container[size++] = new Node(key, value);
        }

    }

    private void update(int pos) {
        if (pos == size - 1) {
            return;
        }
        System.arraycopy(container, pos + 1, container, pos, size - pos - 1);
    }

    public static class Node {
        int key;
        int value;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        LRUCache demo = new LRUCache(2);
        System.out.println(demo.get(2));
        demo.put(2, 6);
        System.out.println(demo.get(1));
        demo.put(1, 5);
        demo.put(1, 2);
        System.out.println(demo.get(1));
        System.out.println(demo.get(2));
    }
}
