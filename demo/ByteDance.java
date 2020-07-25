// 字节跳动笔试题

package demo;

import java.util.ArrayList;
import java.util.Scanner;

public class ByteDance {
    // 山形数组按序无重复输出
    // 思路：归并
    public ArrayList<Integer> solution1(int[] mountain) {
        ArrayList<Integer> ret = new ArrayList<>();

        int n = mountain.length;
        // 首尾指针
        int i = 0, j = n - 1;
        // 记录上一次返回的值
        int lastRet = -1;
        while (i < j) {
            // 保证不重复
            if (!ret.isEmpty()) {
                while (mountain[i] == lastRet)
                    i++;
                while (mountain[j] == lastRet)
                    j--;
            }

            // 本次要添加的值
            int res;

            // 当一个指针走到山顶，就不要再走了，走另一个
            // 假设山顶严格大于左右
            // 这里还要根据具体题目的条件改动
            // 比如山顶就在首尾，或者山顶是平的，则下面的代码不行
            if (i > 0 && mountain[i] > mountain[i - 1] && mountain[i] > mountain[i + 1]) {
                // 说明 i 到达山顶
                res = mountain[j--];
            } else if (j > 0 && mountain[j] > mountain[j - 1] && mountain[j] > mountain[j + 1]) {
                // 说明 j 到达山顶
                res = mountain[i++];
            } else {
                // 添加 i、j 中较小的那个
                if (mountain[i] < mountain[j]) {
                    res = mountain[i++];
                } else {
                    res = mountain[j--];
                }
            }

            ret.add(res);
            lastRet = res;
        }
        // 出循环时，i=j，都指向山顶
        ret.add(mountain[i]);
        return ret;
    }


    // ----------------------------------------------------------------------
    // URL 中提取 Host 部分，即域名或 IP 地址
    // 没有请返回 invalid
    // 思路：URL 的结构为 [协议://] [Host] [:端口号] [/请求资源]
    // Host 如果是 IP，则是 a.b.c.d 这样的结构，且 a、b、c、d 是 [0-255] 中的整数
    // Host 如果是域名，则是 [www.][包含大小写英文字母、数字、小数点的字符串].com[.cn]


    // ----------------------------------------------------------------------
    // 给定黑白图，即结点值只有 0 或 1
    // 返回给定结点所处区域的边缘结点
    // 注意：
    // 1. 相邻只考虑“上下左右”
    // 2. 图边缘的点也算选择区域的边缘
    // 示例：
    //  1 1 1 1
    //  1 0 0 1
    //  1 0 1 1
    //  1 1 1 1
    //  给定点是 graph[1][1]
    // 思路：
    // 相邻结点都是一样颜色的结点是非边缘结点
    //
    // 从给定结点开始广度优先搜索-BFS
    // 1. 给定结点入队，当队列不为空时开始循环
    // 2. 出队一个结点，表示访问它，如果它的邻居结点有不同颜色，则它是一个边缘结点
    // 3. 将该结点邻居中未访问的同色结点入队


    // ----------------------------------------------------------------------
    // 输入正整数 a,b
    // 返回 a/1+a/2+a/3+...+a/b，这里都是整除
    // 思路：
    // 假设上面式子每一项结果为 x1、x2、x3、x4、... 、xb
    // 容易知道 xi * i <= a
    //
    // 拿 a=10 举例，其约数有 1、2、5、10
    // 10/1+10/2+10/3+10/4+10/5+10/6+10/7+10/8+10/9+10/10
    //  10    5    3   2    2    1     1   1    1     1
    //  10    10   9   8    10
    // 拿 a=8 举例，其约数有 1、2、4、8
    // 8/1+8/2+8/3+8/4+8/5+8/6+8/7+8/8
    //  8   4   2   2   1   1   1   1
    //  8   8   6   8
    // 拿 a=13 举例，其约数为 1、13
    // 13  6  4  3  2  2  1  1  1  1  1  1  1
    // 13 12 12 12 10 12
    //
    // 第一个规律，出现 1 项 之后，后面都是 1
    // 而怎么判断第一个 1 项出现，只要分母大于 a/2 即可
    // 这也只能减少一半的工作量
    //
    // 第二个规律，xi 项是递减的
    //
    // 第三个规律，在非 1 和自身的约数处，必定突变

    // 拿 a=100 举例
    // xi:     1   2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 ... 33 34 ... 50 51 ...
    // a/xi:  100 50 33 25 20 16 14 12 11 10  9  8  7  7  6  6  5  5  5  5  4  4  4  4  4  3 ...  3  2 ...  2  1 ...
    // 前面算的的 a/xi 是后面 xi 的突变之处
    // [100,50) 是 100/100
    // [50,33) 是 100/50
    // [33,25) 是 100/33
    // [25,20) 是 100/25
    // [20,16) 是 100/20
    // [16,14) 是 100/16
    // [14,12) 是 100/14
    // [12,11) 是 100/12
    // 接下来的都是不重复，一个一个算即可
    public int solution(int a, int b) {
        int ret = 0;
        int divisor = 1;
        int left = a / (divisor + 1);
        int right = a / divisor;
        while (right - left != 1) {
            // (left,right] 项都是 factor
            int factor = a / right;
            if (b >= right) {
                ret += (right - left) * factor;
            } else if (b > left) {
                ret += (b - left) * factor;
            }
            divisor++;
            left = a / (divisor + 1);
            right = a / divisor;
        }
        // 剩下的一项一项计算
        for (int i = right <= b ? right : b; i >= 1; i--) {
            ret += a / i;
        }
        return ret;
    }

    public int solution1(int a, int b) {
        // 暴力求解
        int ret = 0;
        for (int i = 1; i <= b; i++) {
            if (i > a)
                break;
            ret += a / i;
        }
        return ret;
    }

    // ----------------------------------------------------------------------
    // [1,2,3] 的子数组有 [1] [2] [3] [1,2] [2,3] [1,2,3]
    // 一个数组的所有子数组的元素之和都不为零，则称为这个数组是非零数组
    // 给定一个数组，返回他有多少个非零子数组
    // 思路：子数组的子数组还是子数组
    // 分治法，分为左边，右边，跨越左右
    public int solution(int[] arr) {
        return recursion(arr, 0, arr.length - 1);
    }

    // arr[i,j] 的非零子数组个数
    public int recursion(int[] arr, int i, int j) {
        if (i > j)
            return 0;
        if (i == j)
            return arr[i] == 0 ? 0 : 1;

        // 分成左边，后边和跨越左右
        int left = recursion(arr, i, i);
        int right = recursion(arr, i + 1, j);
        int leftRight = 0;
        int sum = arr[i];
        for (int k = i + 1; k <= j; k++) {
            sum += arr[k];
            if (sum != 0)
                leftRight++;
        }
        return left + right + leftRight;
    }


    // ----------------------------------------------------------------------
    // 一个整型矩阵，有正有负，可以从最左列任意位置开始，然后每次可以移动到当前位置右上、右边、右下三个相邻格子
    // 且一旦路径上数字之和为负，就不准再移动。
    // 且拥有一次使格子上数字变为相反数的技能。
    // 求在这个过程中，路径之和最大的值

    // 记录矩阵及其长宽
    private int[][] matrix;
    private int m;
    private int n;
    // 记录过程中的最大路径和
    private int maxSum = 0;

    public int solution() {
        // 最左边任一位置开始
        for (int i = 0; i < m; i++) {
            backtracing(i, 0, 0, true);
        }
        return maxSum;
    }

    // 读入输入
    private void input() {
        Scanner scanner = new Scanner(System.in);
        m = scanner.nextInt();
        n = scanner.nextInt();
        matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[m][n] = scanner.nextInt();
            }
        }
    }

    // 当前走到位置 i,j，但还未作记录
    // 口袋里的金币数 为 sum
    // hasSkill 表示是否还能发动技能
    private void backtracing(int i, int j, int sum, boolean hasSkill) {
        // 检测当前位置是否合法
        if (sum < 0 || i < 0 || i >= m || j < 0 || j >= n)
            return;

        // 不发动技能去下一个位置
        sum += matrix[i][j];
        maxSum = sum > maxSum ? sum : maxSum;

        backtracing(i - 1, j + 1, sum, true);
        backtracing(i, j + 1, sum, true);
        backtracing(i + 1, j + 1, sum, true);

        // 发动技能去下一个位置，但一般这个技能肯定是让负的变正的才对结果有帮助
        // 所以加了一个条件进行剪枝
        if (matrix[i][j] < 0 && hasSkill) {
            // 回溯
            sum -= matrix[i][j];
            // 发动技能
            sum += -matrix[i][j];
            maxSum = sum > maxSum ? sum : maxSum;

            backtracing(i - 1, j + 1, sum, false);
            backtracing(i, j + 1, sum, false);
            backtracing(i + 1, j + 1, sum, false);
        }
    }
}


// ----------------------------------------------------------------------
// 给定一系列输入数字，构造一个有序链表，链表中每个输入元素都出现两次
class Node {
    int x;
    Node next;
    Node(int x) {
        this.x = x;
    }
}

class LinkedList {
    // 虚头结点
    private Node dummyHead = new Node(-1);

    public void solution() {
        LinkedList list = new LinkedList();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            list.insert(scanner.nextInt());
        }
        list.print();
    }

    private void insert(int val) {
        Node p = dummyHead;
        while (p != null) {
            if (p.next == null || p.next.x > val) {
                // p 结点之后连续插入两个
                Node node1 = new Node(val);
                Node node2 = new Node(val);

                node1.next = p.next;
                p.next = node1;

                node2.next = p.next;
                p.next = node2;

                return;
            } else if (p.next.x == val) {
                return;
            }
            p = p.next;
        }
    }

    private void print() {
        Node p = dummyHead.next;
        while (p != null) {
            System.out.print(p.x + " ");
            p = p.next;
        }
    }
}