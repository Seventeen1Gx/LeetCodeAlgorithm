// 744. 寻找比目标字母大的最小字母
//
// 给你一个排序后的字符列表 letters ，列表中只包含小写英文字母。另给出一个目标字母 target，请你寻找在这一有序列表里比目标字母大的最小字母。
//
// 在比较时，字母是依序循环出现的。举个例子：
//
// 如果目标字母 target = 'z' 并且字符列表为 letters = ['a', 'b']，则答案返回 'a'


package src;

public class FindSmallestLetterGreaterThanTarget {
    // 寻找 target+1
    // 如果存在，就是它
    // 如果不存在，那就是它插入位置上的那个字母
    // 当插入位置是 len 时，返回第一个字母
    public char solution_1(char[] letters, char target) {
        int l = 0, r = letters.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (letters[mid] == target + 1)
                return (char) (target + 1);
            else if (letters[mid] > target + 1)
                r = mid - 1;
            else
                l = mid + 1;
        }
        if (l == letters.length)
            return letters[0];
        return letters[l];
    }

    // 根据模板修改
    public char solution_2(char[] letters, char target) {
        // [l:r] 中遍历
        int l = 0, r = letters.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;

            /*

            if (letters[mid] == target)
                // 说明在右边
                l = mid + 1;
            else if (letters[mid] > target)
                // 说明在左边
                // 同时 mid 也有可能是结果
                // 有这样的更新，循环条件就不能有等号，否则会无限循环
                r = mid;
            else if (letters[mid] < target)
                // 说明在右边
                l = mid + 1;

            */

            // 上面两种情况可以合并
            if (letters[mid] <= target)
                l = mid + 1;
            else
                r = mid;
        }
        return 0;
    }
}
