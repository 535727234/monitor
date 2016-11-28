package process.house;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.IntStream;

/**
 * Created by clarechen on 16/9/1.
 */
public class HouseMonitor {
    private final static int consumerSize = 1;

    private BlockingDeque<String> urlQueue = new LinkedBlockingDeque<>();
    private final static ExecutorService consumers = Executors.newSingleThreadExecutor();
    private final static ExecutorService producers = Executors.newFixedThreadPool(consumerSize);


    public void start(){
        producers.submit(new Producer(urlQueue));
        IntStream.of(consumerSize).forEach(id->consumers.submit(new Consumer(urlQueue)));
    }




    //基金代码列表

    //基金经理列表






}
