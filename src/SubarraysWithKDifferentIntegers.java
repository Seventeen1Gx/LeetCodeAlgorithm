// 992. K个不同整数的子数组
//
// 给定一个正整数数组 A，如果 A 的某个子数组中不同整数的个数恰好为 K，则称 A 的这个连续、不一定独立的子数组为好子数组。
//
// （例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。）
//
// 返回 A 中好子数组的数目。


package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubarraysWithKDifferentIntegers {
    // 暴力法，试验所有子数组，但包含剪枝
    public int solution1(int[] A, int K) {
        int ans = 0;

        if (K <= 0 || A == null || A.length == 0) {
            return ans;
        }

        // 记录当前子数组出现的不同字符数
        Set<Integer> hashSet = new HashSet<>();

        // 子数组为A[i,j]
        for (int i = 0; i < A.length; i++) {
            // 新的i开始，就是新的hashSet
            hashSet.clear();
            for (int j = i; j < A.length; j++) {
                hashSet.add(A[j]);
                if (hashSet.size() == K) {
                    ans++;
                } else if (hashSet.size() > K) {
                    // a[i,j]已不满足要求，继续后移j得到的子数组也不满足要求，则剪枝
                    break;
                }
            }
        }
        return ans;
    }

    // 不能用求最长无重复子串的滑动思路来求
    // 因为该思路中，j右移到满足要求之后，再右移到不满足要求，就去右移i，直到满足要求，就又去移动j
    // 可见i、j在整个过程，只右移，不回退
    // 这是由于题目是要求"最长"而决定的
    // 对于本题，则会丢失一些中间出现的满足要求的子数组


    // 给定j，S_j表示使A[i,j]满足要求的所有i
    // 注意S_j中的i是"连续"的
    // 因为假设我们固定一个j，然后向左添加元素，直到不同元素数达到K，找到第一个i
    // 此时我们还能，向左添加元素，只要继续添加的元素跟已添加的重复即可
    // 一旦发现不重复，就找到了i的终点
    // 所以，S_j可用[left:right]表示，right是第一个满足要求的i，left是最后一个满足要求的i
    // 比如，S_j = [4:7]，即i可取值为4、5、6、7
    //
    // 注意S_j中left、right随着j的增长也在单调增长，因为右移j添加元素，则需要右移i删除元素


    // 滑动窗口优化
    // 此方法中，维护两个窗口
    // window1区间: [left1, right], 需要满足window1恰好有K个不同整数。
    // window2区间: [left2, right], 需要满足window2恰好有K-1个不同整数。
    public int solution2(int[] A, int K) {
        Window window1 = new Window();
        Window window2 = new Window();
        int ans = 0, left1 = 0, left2 = 0;

        // 两个窗口左端不同，但右端相同
        for (int right = 0; right < A.length; ++right) {
            // 右移右边界，从而向窗口中添加元素
            int x = A[right];
            window1.add(x);
            window2.add(x);

            // 一直右移right，直到窗口中不同元素数是K个，这时right是满足要求下最右的边界

            while (window1.different() > K) {
                // 当window1中不同元素超过k个，这时需要右移左边界，以删除元素，直到满足K个元素
                window1.remove(A[left1++]);
            }
            while (window2.different() >= K) {
                // 当window2中不同元素超过k个，这时需要右移左边界，以删除元素，直到满足K-1个元素
                window2.remove(A[left2++]);
            }

            // 增加的子数组数
            // 左边界i属于区间[left1, left2)
            // 右边界j此时给定为right
            // A[i,j]为满足要求的子数组
            // 所以有多少个i就添加多少个满足要求的子数组
            ans += left2 - left1;
        }

        return ans;
    }

    public static void main(String[] args) {
        SubarraysWithKDifferentIntegers s = new SubarraysWithKDifferentIntegers();
        // s.solution1(new int[]{2, 1, 2, 1, 2}, 2);
        s.solution2(new int[]{1, 2, 1, 2, 3}, 2);
    }
}

class Window {
    //记录出现的各个数字以及其出现次数
    Map<Integer, Integer> count;
    //记录不同字符数
    int nonzero;

    Window() {
        count = new HashMap();
        nonzero = 0;
    }

    void add(int x) {
        count.put(x, count.getOrDefault(x, 0) + 1);
        if (count.get(x) == 1) {
            nonzero++;
        }
    }

    void remove(int x) {
        count.put(x, count.get(x) - 1);
        if (count.get(x) == 0) {
            nonzero--;
        }
    }

    //获取当前窗口记录的不同字符数
    int different() {
        return nonzero;
    }
}
