//11. 盛水最多的容器
//
//给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//
//说明：你不能倾斜容器，且 n 的值至少为 2。


public class ContainerWithMostWater {
    //暴力求解，前面的每个数字和他身后的每个数字组合成容器的两壁
    public int solution1(int[] height) {
        int maxArea = 0, area;

        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                area = (j - i) * Math.min(height[i], height[j]);
                if (area > maxArea)
                    maxArea = area;
            }
        }

        return maxArea;
    }

    //想一想有没有子结构的性质，如果我知道height[0:i]的最大水容量，则height[0:i+1]是否可以快速得算出来？
    //结果看了题解，是没有动态规划方法的
    //采用的是一种双指针法，开始时，指针指向数组首尾两端，每次计算所指两线之间的最大水容量
    //然后指向低的向指向高的移动一格，再计算，重复过程，一直到两针相遇
    //使用maxArea保存该过程中遇到的最大的水容量
    //可以这样理解：无论移动哪个指针，首先两者距离是变小的，而面积由二者的最小值确定
    //假设H[i]<H[j]，area=(j-i)*H[j], 若移动j，则area=(j-i-1)*min(H[i],H[j-1])
    //即H[i]<=H[j]时，area=(j-i-1)*H[i]小于上一个area；H[i]>H[j]时，也小于area
    //所以，移动高的指针，只会减小面积，所以要移动小指针
    //但是上面只是说明移动高指针行不通，但还没证明移动小指针就说得通了
    public int solutin2(int[] height) {
        int maxArea = 0, area;

        for (int i = 0, j = height.length - 1; i < j; ) {
            area = (j - i) * Math.min(height[i], height[j]);
            if (area > maxArea)
                maxArea = area;
            if (height[i] <= height[j])
                i++;
            else
                j--;
        }
        return maxArea;
    }
}
