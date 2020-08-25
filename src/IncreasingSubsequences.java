// 491. 递增子序列
//
// 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是 2。


package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncreasingSubsequences {

    // subsequences[i] 表示以 nums[i] 结尾的递增子序列
    List<List<List<Integer>>> subsequences = new ArrayList<>();

    public List<List<Integer>> solution(int[] nums) {
        if (nums == null) {
            return new ArrayList<>();
        }

        int n = nums.length;

        if (n <= 1) {
            return new ArrayList<>();
        }

        // 求 sequences[0]
        subsequences.add(new ArrayList<>());

        // 求 sequences[i]
        for (int i = 1; i < n; i++) {
            // 初始化
            subsequences.add(new ArrayList<>());

            for (int j = 0; j < i; j++) {
                if (nums[i] >= nums[j]) {
                    // nums[j]、nums[i] 组成长度为 2 的子序列
                    List<Integer> newSequence = new ArrayList<>();
                    newSequence.add(nums[j]);
                    newSequence.add(nums[i]);
                    subsequences.get(i).add(newSequence);

                    // 以 nums[j] 为结尾的子序列添加 nums[i] 形成新的子序列
                    for (List<Integer> temp : subsequences.get(j)) {
                        newSequence = new ArrayList<>(temp);
                        newSequence.add(nums[i]);
                        subsequences.get(i).add(newSequence);
                    }
                }
            }
        }

        // 统计
        // 注意这里需要去重
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (List<Integer> temp : subsequences.get(i)) {
                if (!ret.contains(temp)) {
                    ret.add(temp);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        // 超时了
        new IncreasingSubsequences().solution(new int[]{1,2,3,4,5,6,7,8,9});
    }

    List<Integer> temp = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<>();
    Set<Integer> set = new HashSet<>();
    int n;

    public class Solution_One {
        // 官方题解一
        // 枚举所有的子序列 → 枚举 [0, 2^(n-1)]，其二进制形式对应着一种子序列
        // 去重采用串哈希算法：将串当成 max(ai)+1 进制的数

        public List<List<Integer>> findSubsequences(int[] nums) {
            n = nums.length;
            for (int i = 0; i < (1 << n); i++) {
                findSubSequences(i, nums);
                int hashValue = getHash(263, (int) 1e9 + 7);
                if (check() && !set.contains(hashValue)) {
                    ans.add(new ArrayList<>(temp));
                    set.add(hashValue);
                }
            }
            return ans;
        }

        public void findSubSequences(int mask, int[] nums) {
            // 根据 mask 获取子序列

            temp.clear();
            for (int i = 0; i < n; i++) {
                if ((mask & 1) != 0) {
                    // mask 末位不为 0
                    // 代表要取 nums[i]
                    temp.add(nums[i]);
                }
                // 右移一位
                mask >>= 1;
            }
        }
    }

    public int getHash(int base, int mod) {
        int hashVal = 0;
        for (int x : temp) {
            hashVal = hashVal * base % mod + (x + 101);
            hashVal %= mod;
        }
        return hashVal;
    }

    public boolean check() {
        for (int i = 1; i < temp.size(); i++) {
            if (temp.get(i) < temp.get(i - 1)) {
                return false;
            }
        }
        return temp.size() >= 2;
    }

    public class Solution_Two {
        // 递归枚举所有的子序列
        // 每个位置取或不取两种可能
        public void dfs(int cur, int[] nums) {
            if (cur == nums.length) {
                // 判断是否合法，如果合法判断是否重复，将满足条件的加入答案
                int hashVal = getHash(263, (int) 1e9 + 7);
                if (check() && !set.contains(hashVal)) {
                    ans.add(new ArrayList<>(temp));
                    set.add(hashVal);
                }
                return;
            }
            // 选择当前元素
            temp.add(nums[cur]);
            dfs(cur + 1, nums);
            // 回溯
            temp.remove(temp.size() - 1);
            // 不选择当前元素
            dfs(cur + 1, nums);
        }
    }
}
