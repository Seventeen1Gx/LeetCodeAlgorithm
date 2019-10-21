//75. 颜色分类
//
//给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
//
//此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
//
//注意:
//不能使用代码库中的排序函数来解决这道题。


package src;

public class SortColors {
    //计数排序
    public void solution1(int[] nums) {
        //遍历一次计数
        int a = 0, b = 0, c = 0;
        for (int i = 0; i < nums.length; i++) {
            switch (nums[i]) {
                case 0:
                    a++;
                    break;
                case 1:
                    b++;
                    break;
                case 2:
                    c++;
                    break;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (a > 0) {
                nums[i] = 0;
                a--;
            } else if (b > 0) {
                nums[i] = 1;
                b--;
            } else if (c > 0) {
                nums[i] = 2;
                c--;
            }
        }
    }

    //三指针法
    //维护[0:i)都是0，(j,len-1]都是2
    //k从头遍历到尾
    public void solution2(int[] nums) {
        int i = 0, j = nums.length - 1;
        int k = i;
        while (k <= j) {
            if (nums[k] == 0) {
                swap(nums, i, k);
                i++;
                k++;
            } else if (nums[k] == 1) {
                k++;
            } else if (nums[k] == 2) {
                swap(nums, k, j);
                j--;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        SortColors s = new SortColors();
        s.solution2(new int[]{2, 0, 2, 1, 1, 0});
    }
}
