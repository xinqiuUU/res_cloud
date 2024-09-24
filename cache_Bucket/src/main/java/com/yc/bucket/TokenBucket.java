package com.yc.bucket;

/*
 * 令牌桶算法实现类。
 * 该算法用于流量控制，限制请求的处理速率。如果桶满了（超出容量），多余的请求会被丢弃。
 */
public class TokenBucket {
    private final int capacity; // 桶的容量，即最大能容纳多少个令牌
    private final int tokenRate; // 令牌生成速率，表示每秒生成的令牌数
    private int tokens; // 当前桶中的令牌数量
    private long lastTokenTime; // 上次生成令牌的时间戳

    // 构造函数初始化令牌桶，指定容量和令牌生成速率
    public TokenBucket(int capacity, int tokenRate) {
        this.capacity = capacity; // 设置桶的最大容量
        this.tokenRate = tokenRate; // 设置令牌的生成速率
        this.tokens = 0; // 初始令牌数为0
        this.lastTokenTime = System.currentTimeMillis(); // 记录当前时间为上次生成令牌的时间
    }

    // 检查请求是否被允许
    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis(); // 获取当前时间
        long elapsedTime = currentTime - lastTokenTime; // 计算上次生成令牌后经过的时间

        // 计算应生成的新令牌数量，单位是秒
        int newTokens = (int) (elapsedTime * tokenRate / 1000);

        // 更新桶中的令牌数量，但不能超过桶的容量
        tokens = Math.min(capacity, tokens + newTokens);

        // 更新上次生成令牌的时间为当前时间 只有生成了新的令牌才更新时间
        if (newTokens > 0) {
            lastTokenTime = currentTime;
        }

        // 检查桶中是否有足够的令牌来处理请求
        if (tokens > 0) {
            tokens--; // 有令牌，则消耗一个令牌
            return true; // 允许请求
        } else {
            return false; // 没有令牌，拒绝请求
        }
    }

    public static void main(String[] args) {
        TokenBucket bucket = new TokenBucket(10, 1); // 创建一个令牌桶，最大容量为10，令牌生成速率为每秒2个

        // 模拟15个请求的到来，间隔时间为300毫秒
        for (int i = 0; i < 15; i++) {
            // 打印当前请求是否被允许
            System.out.println("Request " + i + ": " + (bucket.allowRequest() ? "Allowed" : "Rejected"));
            try {
                Thread.sleep(500); // 模拟请求的间隔时间为500毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
