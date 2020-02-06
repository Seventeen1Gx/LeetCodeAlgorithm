package src;

/**
 * 168. Excel表列名称
 *
 * 给定一个正整数，返回它在 Excel 表中相对应的列名称。
 *
 * 例如，
 *     1 -> A
 *     2 -> B
 *     3 -> C
 *     ...
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB
 *     ...
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ExcelSheetColumnTitle {
    /**
     * 十进制转二十六进制，除基取余法，但基数中没有 0，需要特别处理
     */
    public String solution(int n) {
        StringBuilder sb = new StringBuilder();

        while (n != 0) {
            int res = n % 26;
            if (res == 0) {
                // 借位，因为没有余数 0 对应的字母
                res = 26;
                n -= 26;
            }
            sb.append((char) ('A' - 1 + res));
            n /= 26;
        }
        return sb.reverse().toString();
    }
}
