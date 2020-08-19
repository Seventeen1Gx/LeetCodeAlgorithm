// 989. 数组形式的整数加法
//
// 对于非负整数 X 而言，X 的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果 X = 1231，那么其数组形式为 [1,2,3,1]。
//
// 给定非负整数 X 的数组形式 A，返回整数 X+K 的数组形式。


package src.add;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddToArrayFormOfInteger {
    public List<Integer> solution(int[] A, int K) {
        List<Integer> ansList = new ArrayList<>();

        int i = A.length - 1;
        int num1, num2, carry = 0, res;
        while (i >= 0 || K > 0) {
            num1 = num2 = 0;
            if (i >= 0) {
                num1 = A[i];
                i--;
            }
            if (K > 0) {
                num2 = K % 10;
                K /= 10;
            }
            res = num1 + num2 + carry;
            carry = res / 10;
            res %= 10;

            ansList.add(res);
        }

        if (carry == 1) {
            ansList.add(1);
        }

        // 逆置
        Collections.reverse(ansList);

        return ansList;
    }

    public static void main(String[] args) {
        AddToArrayFormOfInteger a = new AddToArrayFormOfInteger();
        a.solution(new int[]{1, 2, 0, 0}, 34);
    }
}
