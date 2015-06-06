/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package mspi;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Nassim
 */
public class Pool {

    private final int poolSize;
    private final int maxPoolSize;
    private final int keepAlive;
    private final TimeUnit unit;
    public ThreadPoolExecutor pool;
    private final BlockingQueue<Runnable> queue;

    public Pool(int pSize, int maxSize, int kAlive) {
        poolSize = pSize;
        maxPoolSize = maxSize;
        keepAlive = kAlive;
        unit = TimeUnit.SECONDS;
        queue = new LinkedBlockingQueue<>();
        pool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAlive, unit, queue);
    }
}
