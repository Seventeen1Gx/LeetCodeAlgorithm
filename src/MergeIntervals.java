//56. 合并区间
//
//给出一个区间的集合，请合并所有重叠的区间。
//
//思路
//两个区间的关系有：相交(包括包含)、不相交


package src;

import java.util.*;

public class MergeIntervals {
    //将区间看成图中的结点，结点表示的区间之间相交的则连线
    //最终每个连通图，分别可以合并成一个区间
    //因为是两两比较，所以本质还是暴力求解
    //所以提交的时候超时了
    Map<Interval, List<Interval>> graph;
    Map<Integer, List<Interval>> connectedGraph;
    Set<Interval> visited;

    public int[][] solution1(int[][] intervals) {
        List<Interval> intervalList = new ArrayList<>();

        //数据格式转换
        for (int i = 0; i < intervals.length; i++) {
            Interval interval = new Interval(intervals[i][0], intervals[i][1]);
            intervalList.add(interval);
        }

        //建立图
        graph = new HashMap<>();
        for (Interval interval : intervalList) {
            graph.put(interval, new ArrayList<>());
        }
        //两两判断
        for (Interval interval1 : intervalList) {
            for (Interval interval2 : intervalList) {
                if (interval1 != interval2 && overlap(interval1, interval2)) {
                    graph.get(interval1).add(interval2);
                    graph.get(interval2).add(interval1);
                }
            }
        }

        //从图中提取连通图(一次DFS能遍历到的结点属于同一个连通图)
        connectedGraph = new HashMap<>();
        visited = new HashSet<>();

        int connectedGraphNum = 0;
        for (Interval interval : intervalList) {
            if (!visited.contains(interval)) {
                dfs(interval, connectedGraphNum);
                connectedGraphNum++;
            }
        }

        //将每个连通图中的区间合并
        //新建二维数组时，在没有地方第二维大小时，最好定义时确定下来
        int[][] newIntervals = new int[connectedGraphNum][2];
        for (int i = 0; i < connectedGraphNum; i++) {
            int min = Integer.MAX_VALUE;
            for (Interval interval : connectedGraph.get(i))
                min = Math.min(min, interval.left);

            int max = Integer.MIN_VALUE;
            for (Interval interval : connectedGraph.get(i))
                max = Math.max(max, interval.right);

            newIntervals[i][0] = min;
            newIntervals[i][1] = max;
        }

        return newIntervals;
    }

    private void dfs(Interval start, int num) {
        Stack<Interval> stack = new Stack<>();
        stack.push(start);

        while (!stack.empty()) {
            //出栈
            Interval interval = stack.pop();
            //若该结点没被处理过
            if (!visited.contains(interval)) {
                visited.add(interval);
                //记录到第num个连通图
                if (!connectedGraph.containsKey(num))
                    connectedGraph.put(num, new ArrayList<>());
                connectedGraph.get(num).add(interval);
                //将出栈结点的孩子都入栈
                for (Interval child : graph.get(interval)) {
                    stack.push(child);
                }
            }
        }
    }

    //判断两个区间是否相交
    public boolean overlap(Interval a, Interval b) {
        return !(a.left > b.right || a.right < b.left);
    }

    //将区间按左边界从小到大排列，则合并的区间总是在连续的位置上
    //然后按顺序一个个插入到另一个数组中
    public int[][] solution2(int[][] intervals) {
        if (intervals.length == 0)
            return intervals;

        //按第一维大小排列
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<Interval> merge = new ArrayList<>();
        //先插入第一个区间
        merge.add(new Interval(intervals[0][0], intervals[0][1]));
        //从第二个区间开始
        for (int i = 1; i < intervals.length; i++) {
            //每次和merge列表最后一个区间比较
            //相交时，更新这最后一个区间，否则插入新的区间
            Interval interval = merge.get(merge.size() - 1);
            if (interval.right >= intervals[i][0])
                interval.right = Math.max(intervals[i][1], interval.right);
            else
                merge.add(new Interval(intervals[i][0], intervals[i][1]));
        }

        //转化成结果
        int[][] newIntervals = new int[merge.size()][2];
        for (int i = 0; i < newIntervals.length; i++) {
            newIntervals[i][0] = merge.get(i).left;
            newIntervals[i][1] = merge.get(i).right;
        }
        return newIntervals;
    }

    public static void main(String[] args) {
        int[][] intervals = new int[][]{
                {1, 3}, {2, 6}, {8, 10}, {15, 18}
        };

        MergeIntervals m = new MergeIntervals();
        m.solution2(intervals);
    }
}

class Interval {
    //表示区间[left, right]
    int left;
    int right;

    Interval(int left, int right) {
        this.left = left;
        this.right = right;
    }
}