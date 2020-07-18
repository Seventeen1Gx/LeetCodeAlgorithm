// 字节跳动笔试题

package demo;

import java.util.ArrayList;

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

    // URL 中提取 Host 部分，即域名或 IP 地址
    // 没有请返回 invalid
    // 思路：URL 的结构为 [协议://] [Host] [:端口号] [/请求资源]
    // Host 如果是 IP，则是 a.b.c.d 这样的结构，且 a、b、c、d 是 [0-255] 中的整数
    // Host 如果是域名，则是 [www.][包含大小写英文字母、数字、小数点的字符串].com[.cn]


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
}
