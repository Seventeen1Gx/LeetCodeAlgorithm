// 1002. 查找常用字符
//
// 给定仅有小写字母组成的字符串数组 A，返回列表中的每个字符串中都显示的全部字符（包括重复字符）组成的列表。
// 例如，如果一个字符在每个字符串中出现 3 次，但不是 4 次，则需要在最终答案中包含该字符 3 次。
//
// 你可以按任意顺序返回答案。


package src;

import java.util.ArrayList;
import java.util.List;

public class FindCommonCharacters {
    public List<String> commonChars(String[] A) {
        int n = A.length;
        // count[i][j] 表示字母 'a'+i 在 A[j] 中出现的次数
        int[][] count = new int[26][n];

        for (int i = 0; i < n; i++) {
            for (char c : A[i].toCharArray()) {
                count[c - 'a'][i]++;
            }
        }

        List<String> ret = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (count[i][j] < min) {
                    min = count[i][j];
                    if (min == 0) {
                        break;
                    }
                }
            }
            for (int j = 0; j < min; j++) {
                ret.add((char) ('a' + i) + "");
            }
        }

        return ret;
    }
}
