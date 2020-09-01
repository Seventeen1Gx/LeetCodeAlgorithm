// 557. 反转字符串Ⅲ
//
// 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。


package src;

public class ReverseStringNo3 {
    public String reverseWords(String s) {
        if (s == null) {
            return null;
        }

        char[] charArray = s.toCharArray();
        int n = charArray.length;

        if (n <= 0) {
            return s;
        }

        int start, end;
        start = 0;
        if (charArray[0] == ' ') {
            State.inWord = false;
        } else {
            State.inWord = true;
            start = 0;
        }
        for (int i = 0; i <= n; i++) {
            if (State.inWord && (i == n || charArray[i] == ' ')) {
                State.inWord = false;
                end = i - 1;
                reverse(charArray, start, end);
            } else if (!State.inWord && charArray[i] != ' ') {
                State.inWord = true;
                start = i;
            }
        }
        return new String(charArray);
    }

    private void reverse(char[] s, int start, int end) {
        char c;
        while (start < end) {
            c = s[start];
            s[start] = s[end];
            s[end] = c;
            start++;
            end--;
        }
    }

    static class State {
        static boolean inWord;
    }
}
