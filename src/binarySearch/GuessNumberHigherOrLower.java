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
    // [1,n] 范围内猜数
    public int solution(int n) {
        int low = 1, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;

            // mid>target 时，[mid+1,high] 被排除
            // mid=tartget 时，[mid+1,higt] 被排除
            // mid<target 时，[low,mid] 被排除

            if (guess(mid) == 1)
                // 猜小了
                low = mid + 1;
            else
                high = mid;
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
        if (pick == num)
            return 0;
        else if (pick > num)
            return -1;
        else
            return 1;
    }
}


