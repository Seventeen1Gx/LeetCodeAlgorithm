// 1300. 转变数组后最接近目标值的数组和
//
// 给你一个整数数组 arr 和一个目标值 target ，请你返回一个整数 value ，使得将数组中所有大于 value 的值变成 value 后，数组的和最接近  target （最接近表示两者之差的绝对值最小）。
//
// 如果有多种使得和最接近 target 的方案，请你返回这些整数中的最小值。
//
// 请注意，答案不一定是 arr 中的数字。


package src.binarySearch;

public class SumOfMutatedArrayClosestToTarget {
    public int solution(int[] arr, int target) {
        // [0,max(arr)] 中寻找一个整数

        int low = 0, high = arr[0];
        for (int num : arr) {
            high = Math.max(high, num);
        }
        while (low < high) {
            int mid = low + (high - low) / 2;
            int sum = sum(arr, mid);


            // mid 在递减时，sum 也减小
            // 使得 sum>target 的最小 mid
            // sum>target，[mid+1,high] 可以抛弃
            // sum=target，直接提前返回
            // sum<target，[low,mid] 可以抛弃
            if (sum > target)
                high = mid;
            else if (sum < target)
                low = mid + 1;
            else
                return mid;
        }

        // 因为是最接近，所以还是要看一下隔壁的
        if (target - sum(arr, low - 1) <= sum(arr, low) - target) {
            return low - 1;
        } else {
            return low;
        }
    }

    private int sum(int[] arr, int value) {
        int sum = 0;
        for (int num : arr) {
            sum += Math.min(num, value);
        }
        return sum;
    }

    public static void main(String[] args) {
        new SumOfMutatedArrayClosestToTarget().solution(new int[]{4, 9, 3}, 10);
    }
}
