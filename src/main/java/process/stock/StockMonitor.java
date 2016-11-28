package process.stock;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by clarechen on 16/9/1.
 */
public class StockMonitor {
    private final static int consumerSize = 1000;

    private BlockingDeque<String> urlQueue = new LinkedBlockingDeque<>();
    private final static ExecutorService consumers = Executors.newSingleThreadExecutor();
    private final static ExecutorService producers = Executors.newFixedThreadPool(consumerSize);


    public void start(){
        producers.submit(new Producer(urlQueue));
        IntStream.of(consumerSize).forEach(id->consumers.submit(new Consumer(urlQueue)));
    }
}
