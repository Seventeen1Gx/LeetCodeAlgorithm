// 679. 24 点游戏
// 你有 4 张写有 1 到 9 数字的牌。你需要判断是否能通过 *，/，+，-，(，) 的运算得到 24。


package src;

import java.util.function.BinaryOperator;

public class Game24 {
    // 深搜，试验所有可能

    BinaryOperator<Double> add = (x, y) -> x + y;
    BinaryOperator<Double> sub = (x, y) -> x - y;
    BinaryOperator<Double> mul = (x, y) -> x * y;
    BinaryOperator<Double> div = (x, y) -> x / y;

    public boolean solution(int[] nums) {
        four(nums[0], nums[1], nums[2], nums[3]);
        return ret;
    }

    boolean ret = false;

    private void four(double a, double b, double c, double d) {
        // 4 选 2
        operate(a, b, c, d);
        operate(a, c, b, d);
        operate(a, d, b, c);
        operate(b, c, a, d);
        operate(b, d, a, c);
        operate(c, d, a, b);
    }

    private void three(double a, double b, double c) {
        if (ret) {
            return;
        }
        // 3 选 2
        operate(a, b, c);
        operate(a, c, b);
        operate(b, c, a);
    }

    private void two(double a, double b) {
        if (ret) {
            return;
        }

        double result = operate(a, b, add);
        ret |= Math.abs(result - 24) < 1e-6;

        result = operate(a, b, mul);
        ret |= Math.abs(result - 24) < 1e-6;

        result = operate(a, b, sub);
        ret |= Math.abs(result - 24) < 1e-6;

        result = operate(b, a, sub);
        ret |= Math.abs(result - 24) < 1e-6;

        if (a != 0) {
            result = operate(b, a, div);
            ret |= Math.abs(result - 24) < 1e-6;
        }

        if (b != 0) {
            result = operate(a, b, div);
            ret |= Math.abs(result - 24) < 1e-6;
        }
    }

    private double operate(double a, double b, BinaryOperator<Double> operator) {
        return operator.apply(a, b);
    }

    private void operate(double a, double b, double c, double d) {
        // a, b 是被选择的两个数
        three(operate(a, b, add), c, d);
        three(operate(a, b, sub), c, d);
        three(operate(b, a, sub), c, d);
        three(operate(a, b, mul), c, d);
        if (a != 0) {
            three(operate(b, a, div), c, d);
        }
        if (b != 0) {
            three(operate(a, b, div), c, d);
        }
    }

    private void operate(double a, double b, double c) {
        // a, b 是被选择的两个数
        two(operate(a, b, add), c);
        two(operate(a, b, sub), c);
        two(operate(b, a, sub), c);
        two(operate(a, b, mul), c);
        if (a != 0) {
            two(operate(b, a, div), c);
        }
        if (b != 0) {
            two(operate(a, b, div), c);
        }
    }

    public static void main(String[] args) {
        new Game24().solution(new int[]{4, 1, 8, 7});
    }
}
