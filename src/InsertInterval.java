//57. 插入区间
//
//给出一个无重叠的 ，按照区间起始端点排序的区间列表。
//
//在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。


package src;

public class InsertInterval {
    public int[][] solution(int[][] intervals, int[] newInterval) {
        //区间数
        int n = intervals.length;

        if (n == 0) {
            int[][] newIntervals = new int[1][];
            newIntervals[0] = newInterval;
            return newIntervals;
        }

        //分别判断newInterval左右边界落于上述哪个部分
        int num1 = getNum(intervals, newInterval[0]);
        int num2 = getNum(intervals, newInterval[1]);

        //讨论每种情况，从而判断结果--注意，编号之间的转换不要出错
        if (num1 == num2 && num1 > 0) {
            //落于同一区间内，不改变原有结构
            return intervals;
        } else if (num1 == num2 && num1 < 0) {
            //落于相同的两区间之间，增加一个区间
            //在intervals[-num1-1]之前，intervals[-num1-2]之后增加
            return getNewIntervals(intervals, -num1 - 2, -num1 - 1, newInterval, n + 1);
        } else if (num1 > 0 && num2 > 0) {
            //落于不同的区间内，则[num1-1, num2-1]号区间合并为一个区间
            newInterval[0] = intervals[num1 - 1][0];
            newInterval[1] = intervals[num2 - 1][1];
            return getNewIntervals(intervals, num1 - 2, num2, newInterval, n - (num2 - num1));
        } else if (num1 < 0 && num2 < 0) {
            //落于不同的区间之间，则[-num1-1, -num2-2]号区间合并为一个区间
            return getNewIntervals(intervals, -num1 - 2, -num2 - 1, newInterval, n - (-num2 - 1 + num1));
        } else if (num1 > 0 && num2 < 0) {
            //左边界落于区间内，右边界落于区间外
            //[num1-1, -num2-2]号区间合并为一个区间
            newInterval[0] = intervals[num1 - 1][0];
            return getNewIntervals(intervals, num1 - 2, -num2 - 1, newInterval, n - (-num2 - num1 - 1));
        } else if (num1 < 0 && num2 > 0) {
            //左边界落于区间外，右边界落于区间内
            //[-num1-1, num2-1]号区间合并为一个区间
            newInterval[1] = intervals[num2 - 1][1];
            return getNewIntervals(intervals, -num1 - 2, num2, newInterval, n - (num2 + num1));
        }
        return null;
    }

    //intervals中的区间，将数轴分为2n+1个连续部分
    //从左往右区间内从1开始编号，区间外也从1开始编号，但加上一个负号作区分
    //如[1,3],[5,8]将数轴分为
    //-1:(-∞,1); -2:(3,5); -3:(8,-∞)
    //1:[1,3]; 2:[5,8]
    //这里返回boundary所处部分的编号
    public int getNum(int[][] intervals, int boundary) {
        int num = 0;
        //遍历每个区间，判断是否在区间内
        for (int i = 0; i < intervals.length && num == 0; i++) {
            if (boundary >= intervals[i][0] && boundary <= intervals[i][1]) {
                num = i + 1;
            }
        }
        //是否在[区间i]和[区间i-1]之间
        for (int i = 0; i <= intervals.length && num == 0; i++) {
            if (i == 0 && boundary < intervals[i][0]) {
                //在第一个区间之前
                num = -1;
            } else if (i == intervals.length && boundary > intervals[i - 1][1]) {
                //在最后一个区间之后
                num = -(i + 1);
            } else if (i - 1 >= 0 && boundary > intervals[i - 1][1] && boundary < intervals[i][0]) {
                //在两个区间之间
                num = -(i + 1);
            }
        }
        return num;
    }

    //构造新的区间集合
    //视为三部分组成：intervals[0]到intervals[left]部分，newInterval，intervals[right]到intervals[len-1]
    public int[][] getNewIntervals(int[][] intervals, int left, int right, int[] newInterval, int newLen) {
        int[][] newIntervals = new int[newLen][];

        int j = 0;

        //[0, left]
        for (int i = 0; i <= left; i++, j++)
            newIntervals[j] = intervals[i];

        newIntervals[j] = newInterval;
        j++;

        //[right, n)
        for (int i = right; i < intervals.length; i++, j++) {
            newIntervals[j] = intervals[i];
        }

        return newIntervals;
    }
}
