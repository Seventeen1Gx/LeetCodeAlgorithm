// 696. 计算二进制子串
//
// 给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
//
// 重复出现的子串要计算它们出现的次数。


package src;

public class CountBinarySubstrings {
    // 一次遍历
    // 统计连续 0 和连续 1 的出现次数
    public int solution(String s) {
        int ret = 0;
        int[] count = new int[2];
        int index = 0;
        int i = 0;

        while (i < s.length()) {
            // 统计连续出现的字符数
            count[index] = 0;

            // 当前被统计字符
            char countChar = s.charAt(i);
            while (i < s.length() && s.charAt(i) == countChar) {
                count[index]++;
                i++;
            }
            ret += Math.min(count[0], count[1]);

            index = (index + 1) % 2;

        }
        return ret;
    }
}
