package process.house;

import tool.LoggerUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

/**
 * Created by clarechen on 16/9/11.
 */
public class Producer implements Runnable{
    private final static long sleepTime = 2*60*1000;

    private BlockingDeque<String> writeQueue;
    private List<String> codeList;
    private List<Date> timeList;
    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
    public Producer(BlockingDeque<String> queue){
        this.writeQueue = queue;
        try {
            timeList = Files.readAllLines(Paths.get(Producer.class.getResource("/times.txt").getPath())).stream()
                    .map(time -> {
                        try {
                            return timeFormat.parse(time);
                        } catch (ParseException e) {
                            LoggerUtils.runtimelogger.error(e.getMessage(),e);
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LoggerUtils.runtimelogger.error(e.getMessage(),e);
        }
    }
    public void run(){
        while(true) {
            try {
                timeList.forEach(time->{
                    Date currentDate = new Date();
                    long timeId = Math.abs(Long.valueOf(timeFormat.format(currentDate.getTime()))-Long.valueOf(timeFormat.format(time.getTime())));
                    LoggerUtils.runtimelogger.info("checkTime:now:{},set:{},now-set={}",timeFormat.format(currentDate),timeFormat.format(time),timeId);

                    if(timeId<(sleepTime/60/1000)){
                        Level3CodeList.getCodeList().forEach(id -> writeQueue.add(id));
                    }

                });
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                LoggerUtils.runtimelogger.error(e.getMessage(),e);
            }
        }
    }

}