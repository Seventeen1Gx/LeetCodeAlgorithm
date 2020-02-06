package src;

/**
 * 125. 验证回文串
 *
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ValidPalindrome {
    public boolean solution(String s) {
        if ("".equals(s)) {
            return true;
        }

        int i = 0, j = s.length() - 1;
        while (i < j) {
            char c1 = s.charAt(i);
            while (i < j && !Character.isDigit(c1) && !Character.isAlphabetic(c1)) {
                i++;
                c1 = s.charAt(i);
            }

            char c2 = s.charAt(j);
            while (i < j && !Character.isDigit(c2) && !Character.isAlphabetic(c2)) {
                j--;
                c2 = s.charAt(j);
            }

            if (Character.isAlphabetic(c1)) {
                c1 = Character.toLowerCase(c1);
            }
            if (Character.isAlphabetic(c2)) {
                c2 = Character.toLowerCase(c2);
            }

            if (c1 != c2) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        ValidPalindrome v = new ValidPalindrome();
        v.solution("A man, a plan, a canal: Panama");
    }
}
