package process;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by clarechen on 16/9/1.
 */
public class Main implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new RequestProcesser().start();
        System.out.println("system started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
