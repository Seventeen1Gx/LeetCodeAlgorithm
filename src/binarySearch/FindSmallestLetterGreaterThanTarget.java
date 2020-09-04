// 744. 寻找比目标字母大的最小字母
//
// 给你一个排序后的字符列表 letters ，列表中只包含小写英文字母。另给出一个目标字母 target，请你寻找在这一有序列表里比目标字母大的最小字母。
//
// 在比较时，字母是依序循环出现的。举个例子：
//
// 如果目标字母 target = 'z' 并且字符列表为 letters = ['a', 'b']，则答案返回 'a'


package src.binarySearch;

public class FindSmallestLetterGreaterThanTarget {
    public char solution(char[] letters, char target) {
        int n = letters.length;
        // 特判
        if (letters[n - 1] <= target) {
            return letters[0];
        }

        // [0, n-1] 分成两部分，分界点是我们的答案
        // [0, i] [i, n-1]
        // 第一个区间的字母都小于等于 target
        // 第二个区间的字母都大于 target

        int l = 0, r = letters.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;

            if (letters[mid] >  target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return letters[r];
    }
}
