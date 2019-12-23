//89. 格雷编码
//
//格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
//
//给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。格雷编码序列必须以 0 开头


package src;

import java.util.ArrayList;
import java.util.List;

public class GrayCode {
    //动态规划
    //假如知道n=2的格雷编码
    //则n=3的格雷编码就是在n=2的基础上在最高位添加1或0
    //但如何安排顺序？
    //答案是镜像的
    //
    //     0 0
    //     0 1
    //     1 1
    //     1 0
    //
    //   0 0 0
    //   0 0 1
    //   0 1 1
    //   0 1 0
    // ---------  此线上下镜像
    //   1 1 0
    //   1 1 1
    //   1 0 1
    //   1 0 0
    public List<Integer> solution1(int n) {
        List<Integer> gray = new ArrayList<>();

        gray.add(0);
        for (int i = 0; i < n; i++) {
            //首位加0的，可以不做改变，即保留原有gray，在之后倒序添加首位加1的

            //左移i位，原编码加上add，就是首位添加1
            int add = 1 << i;
            //倒序遍历
            for (int j = gray.size() - 1; j >= 0; j--) {
                gray.add(gray.get(j) + add);
            }
        }
        return gray;
    }

    //直接模拟构造
    //n=3时
    //0 0 0 第零项初始化为 0。
    //0 0 1 第一项改变上一项最右边的位元
    //0 1 1 第二项改变上一项右起第一个为 1 的位元的左边位
    //0 1 0 第三项同第一项，改变上一项最右边的位元
    //1 1 0 第四项同第二项，改变最上一项右起第一个为 1 的位元的左边位
    //1 1 1 第五项同第一项，改变上一项最右边的位元
    //1 0 1 第六项同第二项，改变最上一项右起第一个为 1 的位元的左边位
    //1 0 0 第七项同第一项，改变上一项最右边的位元
    //
    //下面的代码来自他人题解！
    public List<Integer> solution2(int n) {
        List<Integer> gray = new ArrayList<>();
        gray.add(0); //初始化第零项
        for (int i = 1; i < 1 << n; i++) {
            //得到上一个的值
            int previous = gray.get(i - 1);
            //同第一项的情况
            if (i % 2 == 1) {
                previous ^= 1; //和 0000001 做异或，使得最右边一位取反
                gray.add(previous);
                //同第二项的情况
            } else {
                int temp = previous;
                //寻找右边起第一个为 1 的位元
                for (int j = 0; j < n; j++) {
                    if ((temp & 1) == 1) {
                        //和 00001000000 类似这样的数做异或，使得相应位取反
                        previous = previous ^ (1 << (j + 1));
                        gray.add(previous);
                        break;
                    }
                    temp = temp >> 1;
                }
            }
        }
        return gray;
    }


    //公式法
    //每个二进制转格雷编码：
    //首位保留，其后结果的每位都是当前对应二进制位和其前一位的位异或运算
    public List<Integer> solution3(int n) {
        List<Integer> gray = new ArrayList<>();
        for(int binary = 0;binary < 1 << n; binary++){
            gray.add(binary ^ binary >> 1);
        }
        return gray;
    }
}
