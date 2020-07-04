// 2. 两数相加
//
// 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
// 思路：
// 按照加法运算的思路，结果上的一位，等于数1对应位加数2对应位，加前一位的进位


package src;

import java.util.ArrayList;
import java.util.List;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class AddTowNum {
    public ListNode solution(ListNode l1, ListNode l2) {
        //结果链表的头结点
        ListNode headNode = new ListNode(-1);
        //结果链表的游标，新结点接在p所指结点之后
        ListNode p = headNode;


        int num1, num2, carry = 0, result;
        while (l1 != null || l2 != null) {
            num1 = num2 = 0;
            if (l1 != null) {
                num1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                num2 = l2.val;
                l2 = l2.next;
            }

            //计算当前位的结果值与产生的进位，这里将 result>9 与 result<9 进行了代码统一
            result = num1 + num2 + carry;
            carry = result / 10;
            result = result % 10;
            //结果值存在新结点中，然后接在结果链表之后
            p.next = new ListNode(result);
            p = p.next;
        }

        //注意最后的进位，若不为0，加一个值为1的结点
        if (carry != 0) {
            p.next = new ListNode(1);
        }

        return headNode.next;
    }
}

class Solution {
    public int[][] merge(int[][] intervals) {
        // 按左端点从小到大排序
        sort(intervals);

        List<List<Integer>> resultList = new ArrayList<>();

        // 从第一个区间的右端点，向后找，直到找到第一个大于它的左端点
        // 以此类推
        int i = 0;
        while (i < intervals.length) {
            int j = i+1;
            while (j < intervals.length && intervals[i][1] >= intervals[j][0]) {
                j++;
            }
            List<Integer> interval = new ArrayList<>();
            interval.add(intervals[i][0]);
            interval.add(Math.max(intervals[i][1], intervals[j-1][1]));
            resultList.add(interval);

            i=j;
        }

        int[][] result = new int[resultList.size()][2];
        for (i=0; i<resultList.size(); i++) {
            result[i][0] = resultList.get(i).get(0);
            result[i][1] = resultList.get(i).get(1);
        }
        return result;
    }

    public void sort(int[][] intervals) {
        // 一行看成一个元素
        for (int i=0; i<intervals.length; i++) {
            boolean flag = true;
            for (int j=0; j<intervals.length-1-i; j++) {
                if (intervals[j][0] > intervals[j+1][0]) {
                    int[] temp = intervals[j];
                    intervals[j] = intervals[j+1];
                    intervals[j+1] = temp;
                    flag = false;
                }
            }
            if (flag)
                break;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] m = {
                {1,4},
                {0,2},
                {3,5}
        };
        s.merge(m);
    }

}
