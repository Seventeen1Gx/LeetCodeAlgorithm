// 541. 反转字符串Ⅱ
//
// 给定一个字符串 s 和一个整数 k，你需要对从字符串开头算起的每隔 2k 个字符的前 k 个字符进行反转。
//
// 如果剩余字符少于 k 个，则将剩余字符全部反转。
// 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。


package src;

public class ReverseStringNo2 {
    public String reverseStr(String s, int k) {
        // 按顺序遍历
        // 每遍历 2k 就进行反转
        if (s == null) {
            return null;
        }

        char[] charArray = s.toCharArray();
        int n = charArray.length;
        for (int start = 0, end = Math.min(start + k - 1, n - 1);
             start < n; start = end + k + 1, end = Math.min(start + k - 1, n - 1)) {
            int i = start, j = end;
            while (i < j) {
                char c = charArray[i];
                charArray[i] = charArray[j];
                charArray[j] = c;
                i++;
                j--;
            }
        }
        return new String(charArray);
    }

    public static void main(String[] args) {
        new ReverseStringNo2().reverseStr("abcdefg", 1);
    }
}
