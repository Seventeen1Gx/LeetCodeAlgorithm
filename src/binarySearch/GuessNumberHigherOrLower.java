// 374. 猜数字大小
//
// 我们正在玩一个猜数字游戏。 游戏规则如下：
// 我从 1 到 n 选择一个数字。 你需要猜我选择了哪个数字。
// 每次你猜错了，我会告诉你这个数字是大了还是小了。
// 你调用一个预先定义好的接口 guess(int num)，它会返回 3 个可能的结果（-1，1 或 0）：
// -1 : 我的数字比较小
//  1 : 我的数字比较大
//  0 : 恭喜！你猜对了！


package src.binarySearch;

public class GuessNumberHigherOrLower extends GuessGame {
    public int solution(int n) {
        // [1,n] 范围内猜数
        // [1, i] [i+1, n]，i+1 是目标
        // 如果猜测的数 mid 在第一个区间，偏小，guess(mid) == 1
        // 如果猜测的数 mid 在第二个区间，偏大或相等，guess(mid) <=0
        int low = 1, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (guess(mid) == 1) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        new GuessNumberHigherOrLower().solution(10);
    }
}

class GuessGame {
    int num = 6;

    int guess(int pick) {
        return Integer.compare(num, pick);
    }
}


