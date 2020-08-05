// 生产者消费者模型


package demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    public static class BufferExplicitLockDemo {
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        // 资源
        final Object[] items;
        int putptr, takeptr, count;

        public BufferExplicitLockDemo(int length) {
            items = new Object[length];
        }

        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length) {
                    notFull.await();
                }
                items[putptr++] = x;
                if (putptr == items.length) {
                    putptr = 0;
                }
                count++;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object get() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();
                }
                Object ret = items[takeptr++];
                if (takeptr == items.length) {
                    takeptr = 0;
                }
                count--;
                notFull.signal();
                return ret;
            } finally {
                lock.unlock();
            }
        }
    }

    public static class BufferImplicitLockDemo {
        Object[] items;
        int putptr, takeptr, count;

        public BufferImplicitLockDemo(int length) {
            items = new Object[length];
        }

        public synchronized void put(Object x) throws InterruptedException {
            while (count == items.length) {
                this.wait();
            }
            items[putptr++] = x;
            if (putptr == items.length) {
                putptr = 0;
            }
            count++;
            this.notifyAll();
        }

        public synchronized Object get() throws InterruptedException {
            while (count == 0) {
                this.wait();
            }
            Object ret = items[takeptr++];
            if (takeptr == items.length) {
                takeptr = 0;
            }
            count--;
            this.notifyAll();
            return ret;
        }
    }
}
