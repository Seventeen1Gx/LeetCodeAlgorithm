//58. 最后一个单词的长度
//
//给定一个仅包含大小写字母和空格 ' ' 的字符串，返回其最后一个单词的长度。
//
//如果不存在最后一个单词，请返回 0 。
//
//说明：一个单词是指由字母组成，但不包含任何空格的字符串。


package src;

public class LengthOfLastWord {
    public int solution(String s) {
        //非法情况
        if (s == null)
            return 0;

        //去除末尾的空格，找到最后一个单词的结尾
        int end = s.length() - 1;
        if (end < 0)
            return 0;
        while (end >= 0 && s.charAt(end) == ' ')
            end--;

        int len = 0;
        while (end >= 0 && s.charAt(end) != ' ') {
            end--;
            len++;
        }
        return len;
    }
}
