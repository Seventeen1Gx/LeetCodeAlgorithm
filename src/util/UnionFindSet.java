package src.util;

import java.util.Arrays;

public class UnionFindSet {
    int[] arr;

    public UnionFindSet(int capacity) {
        arr = new int[capacity];
        // 开始时，每个结点各成一个集合
        // arr[i] 是负几，就说明这个集合中有几个元素
        Arrays.fill(arr, -1);
    }

    public int find(int num) {
        // 寻找代表结点
        while (arr[num] > 0) {
            num = arr[num];
        }
        return num;
    }

    public boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px == py) {
            return true;
        }
        // 将结点数更多的那个集合的领导作为合并后的领导
        if (arr[px] > arr[py]) {
            arr[py] += arr[px];
            arr[px] = py;
        } else {
            arr[px] += arr[py];
            arr[py] = px;
        }
        return false;
    }
}
