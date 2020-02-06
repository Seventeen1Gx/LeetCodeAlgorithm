package demo;

public class InnerClassDemo {
    public static void main(String[] args) {
        // 只有在非外部类中，创建内部类对象才讲究
        // 成员内部类需要外部类对象
        Outer.memberInner memberInner = new Outer().new memberInner();

        // 静态内部类不需要对象
        Outer.staticInner staticInner = new Outer.staticInner();
    }
}

class Outer {
    {
        // 在外部类中，创建内部类不讲究，直接 new

        // 持有内部类的对象，访问内部类成员，即使私有也能访问
        System.out.println(new memberInner().num);

        // 创建一个内部类对象
        System.out.println(new staticInner());
    }

    // 成员内部类，依存于外部类对象
    class memberInner {
        private int num = 1;
    }

    // 静态内部类，依存于外部类
    static class staticInner {

    }

    // 局部内部类：只存在于局部，相当于一个局部变量，不能有权限、静态等修饰符

    // 匿名内部类：随用随定义
}
