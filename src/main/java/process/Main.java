package process;

import process.house.HouseMonitor;
import process.stock.StockMonitor;
import tool.LoggerUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by clarechen on 16/9/1.
 */
public class Main implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new HouseMonitor().start();
        new StockMonitor().start();
        LoggerUtils.runtimelogger.info("system started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
