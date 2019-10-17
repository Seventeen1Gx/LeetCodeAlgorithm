//66. 加1
//
//给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
//
//最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
//
//你可以假设除了整数 0 之外，这个整数不会以零开头。


package src;

public class PlusOne {
    //只要注意结果位数多1的情况即可
    public int[] solution(int[] digits) {
        if (digits == null)
            return null;

        int n = digits.length;

        if (n == 0)
            return null;

        //结尾数+1
        digits[n - 1] += 1;
        //前一位的进位
        int carry;
        if (digits[n - 1] > 9) {
            //只可能时digits[n-1]=10
            digits[n - 1] = 0;
            carry = 1;
        } else {
            carry = 0;
        }
        for (int i = n - 2; i >= 0; i--) {
            digits[i] += carry;
            if (digits[i] > 9) {
                digits[i] = 0;
                carry = 1;
            } else {
                carry = 0;
            }
        }

        if (carry == 1) {
            //数组默认全为0
            int[] newDigits = new int[n + 1];
            newDigits[0] = 1;
            return newDigits;
        }
        return digits;
    }
}
