//992. K个不同整数的子数组
//
//给定一个正整数数组 A，如果 A 的某个子数组中不同整数的个数恰好为 K，则称 A 的这个连续、不一定独立的子数组为好子数组。
//
//（例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。）
//
//返回 A 中好子数组的数目。


package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubarraysWithKDifferentIntegers {
    //滑动窗口（单窗口）
    //给定窗口开始位置i，右移j以添加元素，直到满足K个不同字符，当不同字符数为K+1或者j移到头，i换成下一个位置
    public int solution1(int[] A, int K) {
        int ans = 0;

        if (K <= 0 || A == null || A.length == 0) {
            return ans;
        }

        //记录当前子数组出现的不同字符数
        Set<Integer> hashSet = new HashSet<>();

        //子数组为A[i,j]
        int i, j;
        i = j = 0;
        while (i < A.length) {
            if (j == A.length) {
                //已i开头的所有子数组已考察过
                //切换新窗口
                i++;
                j = i;
                //只要换窗口，就要清空Set
                hashSet.clear();
                continue;
            }

            hashSet.add(A[j]);
            if (hashSet.size() == K) {
                //A[i,j]中恰有K个不同字符
                ans++;
                //且还要继续扩大窗口
                j++;
            } else if (hashSet.size() < K) {
                //继续扩大当前窗口大小
                j++;
            } else {
                //子数组不同字符数超过K，换下一个窗口
                i++;
                j = i;
                //清空Set
                hashSet.clear();
            }
        }
        return ans;
    }

    //滑动窗口优化
    //solution1超时了，所以应该是不能一格一格地滑动
    //此方法中，维护两个窗口
    //window1区间: (left1, right), 需要满足window1恰好有K个不同整数。
    //window2区间: (left2, right), 需要满足window2恰好有K-1个不同整数。
    public int solution2(int[] A, int K) {
        Window window1 = new Window();
        Window window2 = new Window();
        int ans = 0, left1 = 0, left2 = 0;

        //两个窗口左端不同，但右端相同
        for (int right = 0; right < A.length; ++right) {
            int x = A[right];
            window1.add(x);
            window2.add(x);

            while (window1.different() > K) {
                window1.remove(A[left1++]);
            }
            while (window2.different() >= K) {
                window2.remove(A[left2++]);
            }

            //增加的子数组数
            ans += left2 - left1;
        }

        return ans;
    }

    public static void main(String[] args) {
        SubarraysWithKDifferentIntegers s = new SubarraysWithKDifferentIntegers();
        //下面情况是代码31~38存在的必要
        //s.solution1(new int[]{2, 1, 2, 1, 2}, 2);
        //s.solution1(new int[]{1, 2, 1, 2, 3}, 2);
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
