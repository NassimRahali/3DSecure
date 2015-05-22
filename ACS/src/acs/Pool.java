/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Thibault
 */
public class Pool
{
    private int poolSize;
    private int maxPoolSize;
    private int keepAlive;
    private TimeUnit unit;
    public ThreadPoolExecutor pool;
    private BlockingQueue<Runnable> queue;
    
    public Pool(int pSize, int maxSize, int kAlive)
    {
        poolSize = pSize;
        maxPoolSize = maxSize;
        keepAlive = kAlive;
        unit = TimeUnit.SECONDS;
        queue = new LinkedBlockingQueue<>();
        pool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAlive, unit, queue);
    }
}
