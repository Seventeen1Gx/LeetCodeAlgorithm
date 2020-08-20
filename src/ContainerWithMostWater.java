// 11. 盛水最多的容器
//
// 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
// 在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
// 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//
// 说明：你不能倾斜容器，且 n 的值至少为 2。


package src;

public class ContainerWithMostWater {
    // 暴力求解，前面的每个数字和他身后的每个数字组合成容器的两壁
    public int solution1(int[] height) {
        int maxArea = 0, area;

        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                area = (j - i) * Math.min(height[i], height[j]);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }


    // 双指针，两边向中间遍历
    // 为什么要动低的指针？
    // 因为向中间移动，两针的距离在变小
    // 我们希望求最大面积
    // 如果高针移动，就算遇到更高的针，仍然由短针决定，面积不会变大
    // 故我们动短针，短针变大还有机会
    public int solutin2(int[] height) {
        int maxArea = 0, area;

        for (int i = 0, j = height.length - 1; i < j; ) {
            area = (j - i) * Math.min(height[i], height[j]);
            if (area > maxArea) {
                maxArea = area;
            }
            if (height[i] <= height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return maxArea;
    }
}
