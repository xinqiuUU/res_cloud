package com.yc.bucket;

/**
 * 漏斗算法实现类。
 * 该算法用于流量控制，限制请求的处理速率。如果漏斗满了（超出容量），多余的请求会被丢弃。
 */
public class LeakyBucket {
    private final int capacity; // 漏斗容量 ，即最大可以同时处理的请求数
    private final int leakyRate; // 漏出速率，表示每秒钟可以处理的请求数
    private int water; // 当前漏斗中的水量，代表未处理的请求数
    private long lastLeakTime; // 上一次漏水（处理请求）的时间

    /**
     * 构造函数，初始化漏斗的容量和漏出速率。
     * capacity 漏斗的最大容量（能接受的最大请求数）
     * leakyRate 每秒处理的请求数（漏水的速率）
     */
    public LeakyBucket(int capacity, int leakyRate) {
        this.capacity = capacity; // 初始化漏斗容量
        this.leakyRate = leakyRate;// 初始化每秒处理的请求数
        this.water = 0; // 初始化漏斗中的水量为0
        this.lastLeakTime = System.currentTimeMillis(); // 初始化上次漏水的时间为当前时间
    }

    /**
     * 尝试向漏斗中添加请求。
     * 如果请求可以被处理（漏斗未满），则返回 true；否则返回 false。
     */
    public synchronized boolean tryRequest() {
        long now = System.currentTimeMillis(); // 获取当前时间
        // 计算自上次漏水以来的时间差，以判断漏掉了多少请求（水量）
        long elapsedTime  = now - lastLeakTime;
        //根据时间差计算应漏掉的水量（即处理掉的请求数）
        int leaked  = (int) (elapsedTime * leakyRate / 1000);//速率是每秒处理的请求数
        // 更新漏斗中的水量，确保水量不能小于 0
        water = Math.max(0, water - leaked );
        // 更新上次漏水时间 只有滴了一次水才更新时间
        if (leaked > 0) {
            lastLeakTime = now;
        }

        // 如果漏斗未满（水量小于容量），则允许请求通过，更新水量并返回 true
        if (water < capacity) {
            water++;
            return true;
        } else {
            // 漏斗已满，请求被丢弃，返回 false
            return false;
        }
    }

    /**
     * 模拟多个请求，验证漏斗算法的效果。
     * 每隔 500 毫秒发送一次请求，判断是否能处理。
     */
    public static void main(String[] args) {
        // 初始化漏斗容量为10，处理速率为1个请求/秒
        LeakyBucket bucket = new LeakyBucket(5, 1);

        // 模拟15个请求的发送
        for (int i = 0; i < 20; i++) {
            // 输出每个请求是被允许还是被拒绝
            System.out.println("Request " + i + ": " + (bucket.tryRequest() ? "Allowed" : "Rejected"));

            try {
                // 模拟请求发送间隔为500毫秒
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
