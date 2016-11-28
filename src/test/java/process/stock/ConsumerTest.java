package process.stock;

import org.htmlcleaner.XPatherException;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.IOException;

/**
 * Consumer Tester.
 *
 * @author <Authors clarechen>
 * @version 1.0
 * @since <pre>九月 14, 2016</pre>
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
        FundCodeList.getFundCodeList().stream().forEach(id -> {
            try {
                consumer.process(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        consumer.process("001201");
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
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
     * Method: nodeOne(TagNode node, String xPathExpression, int index)
     */
    @Test
    public void testNodeOneForNodeXPathExpressionIndex() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Consumer.getClass().getMethod("nodeOne", TagNode.class, String.class, int.class); 
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
