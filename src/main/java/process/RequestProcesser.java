package process;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import tool.LoggerUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by clarechen on 16/9/1.
 */
public class RequestProcesser {

    private static String url = "http://114.80.170.73:60011/master-status";

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    class Metric{
        public String key;
        public int value;
    }

    public void start(){
        scheduledExecutorService.scheduleWithFixedDelay(()->crawlHbasePPS(),0,1, TimeUnit.MINUTES);
    }

    private void crawlHbasePPS() {
        try {
            HtmlCleaner cleaner = new HtmlCleaner();

            TagNode root = cleaner.clean(new URL(url));
            //会得到Region个数+2个Object,第0个是table的头,最后一个是总数
            Object[] trArray = root.evaluateXPath("//section[h2='Region Servers']//div[@class='tab-pane active']//tr");

            for (int i = 1; i < trArray.length - 1; i++) {
                TagNode trNode = trArray[i] instanceof TagNode ? ((TagNode) trArray[i]) : null;
                Object[] tdArray = trNode.evaluateXPath("//td");
                Object serverNode = tdArray[0];
                Object ppsNode = tdArray[2];
                String serverData = ((TagNode) serverNode).getText().toString().trim();
                String ppsData = ((TagNode) ppsNode).getText().toString().trim();
                LoggerUtils.info("server={}\tpps={}", serverData, ppsData);
            }
        }catch (Throwable e){}
    }
}
