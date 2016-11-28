package process.house;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Consumer Tester.
 *
 * @author <Authors clarechen>
 * @version 1.0
 * @since <pre>九月 16, 2016</pre>
 */
public class ConsumerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: run()
     */
    @Test
    public void testRun() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: process(String code)
     */
    @Test
    public void testProcess() throws Exception {
        Consumer consumer = new Consumer(null);
        Level3CodeList.getCodeList().stream().forEach(id -> {
            try {
                System.out.println(id);
                consumer.process(id);
                Thread.sleep(100);//由于链家限速,设置密度
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        ReentrantLock mainLock = new ReentrantLock();
//TODO: Test goes here...
    }


    /**
     * Method: parseOneHouse(TagNode node)
     */
    @Test
    public void testParseOneHouse() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("parseOneHouse", TagNode.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: processQuoTation(TagNode node)
     */
    @Test
    public void testProcessQuoTation() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("processQuoTation", TagNode.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getUrl(String id)
     */
    @Test
    public void testGetUrl() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("getUrl", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: nodeOne(TagNode node, String xPathExpression)
     */
    @Test
    public void testNodeOneForNodeXPathExpression() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("nodeOne", TagNode.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: nodeOne(TagNode node, String xPathExpression, int elementIndex, int contextIndex)
     */
    @Test
    public void testNodeOneForNodeXPathExpressionElementIndexContextIndex() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("nodeOne", TagNode.class, String.class, int.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: nodeAll(TagNode node, String xPathExpression)
     */
    @Test
    public void testNodeAll() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("nodeAll", TagNode.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
