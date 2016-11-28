package process.stock;

import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import tool.LoggerUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

/**
 * Created by clarechen on 16/9/11.
 */
public class Consumer implements Runnable{
    private final static String template="http://fund.eastmoney.com/@id.html";

    private HtmlCleaner cleaner = new HtmlCleaner();
    private BlockingDeque<String> readQueue;

    public Consumer(BlockingDeque<String> queue){
        this.readQueue = queue;
    }
    public void run() {
        String code="";
        while(true){
            try {
                code = readQueue.take();
                LoggerUtils.runtimelogger.info("start process code:{}",code);
                process(code);
                LoggerUtils.runtimelogger.info("finish process code:{}",code);
            } catch (Exception e) {
                LoggerUtils.runtimelogger.error("faild process:"+code,e);
            }
        }
    }
    public void process(String code) throws IOException, XPatherException {
        //1code是基金代码
        String url = getUrl(code);
        System.out.println(url);
        TagNode root = cleaner.clean(new URL(url));

        TagNode fundInfoItem = (TagNode)root.evaluateXPath("//div[@class='fundInfoItem']")[0];
        //2时间
        String time = nodeOne(fundInfoItem,"//span[@id='gz_gztime']").replace("(","").replace(")","");
        //3净值估计
        String guessValue =nodeOne(fundInfoItem,"//dl[@class='dataItem01']//dl[@class='floatleft']");
        //4估计净值涨跌
        String fudu1 = nodeOne(fundInfoItem,"//dl[@class='dataItem01']//dl[@class='floatleft fundZdf']",0,2);
        //5单位净值
        String unitValue = nodeOne(fundInfoItem,"//dl[@class='dataItem02']//dd[@class='dataNums']//span");
        //6净值涨跌
        String fudu2 = nodeOne(fundInfoItem,"//dl[@class='dataItem02']//dd[@class='dataNums']//span",1,0);
        //7累计净值
        String leijiValue = nodeOne(fundInfoItem,"//dl[@class='dataItem03']//dd[@class='dataNums']//span");
        //1月,1年,3月,3年,6月,成立以来
        //8
        String meta1 = nodeOne(((TagNode)fundInfoItem.evaluateXPath("//dl[@class='dataItem01']//dd")[1]),"//span",1,0);
        //9
        String meta2 = nodeOne(((TagNode)fundInfoItem.evaluateXPath("//dl[@class='dataItem01']//dd")[2]),"//span",1,0);
        //10
        String meta3 = nodeOne(((TagNode)fundInfoItem.evaluateXPath("//dl[@class='dataItem02']//dd")[1]),"//span",1,0);
        //11
        String meta4 = nodeOne(((TagNode)fundInfoItem.evaluateXPath("//dl[@class='dataItem02']//dd")[2]),"//span",1,0);
        //12
        String meta5 = nodeOne(((TagNode)fundInfoItem.evaluateXPath("//dl[@class='dataItem03']//dd")[1]),"//span",1,0);
        //13
        String meta6 = nodeOne(((TagNode)fundInfoItem.evaluateXPath("//dl[@class='dataItem03']//dd")[2]),"//span",1,0);
        //14
        String guimo = nodeOne(fundInfoItem,"//td",1,1).substring(1);
        //15
        String jingli = nodeOne(fundInfoItem,"//td//a",2,0);

        //n个股票
        TagNode quotationItem = (TagNode) root.evaluateXPath("//div[@id='quotationItem_DataTable']//table[@class='ui-table-hover']")[0];
        //16
        String stockList = processQuoTation(quotationItem);

        LoggerUtils.monitorlogger2.info("{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}",
                code,time,guessValue,fudu1,unitValue,fudu2,leijiValue,meta1,meta2,meta3,meta4,meta5,meta6,guimo,jingli,stockList);
    }

    //获取所有投资的股票
    private String processQuoTation(TagNode node) throws XPatherException {
        List<String> resultList = new ArrayList<>();
        Object[] items = node.evaluateXPath("//tr");
        for(int i=1;i<items.length;i++){
            Object[] tdArray = ((TagNode)items[i]).evaluateXPath("//td");
            String name = ((TagNode)tdArray[0]).getText().toString().trim();
            String number = ((TagNode)tdArray[1]).getText().toString().trim();
            String updown = ((TagNode)tdArray[2]).getText().toString().trim();
            resultList.add(name+":"+number+":"+updown);
        }
        return StringUtils.join(resultList,";");
    }

    private String getUrl(String id){
        return template.replace("@id",String.valueOf(id));
    }

    //下面的函数是通用函数

    private String nodeOne(TagNode node,String xPathExpression) throws XPatherException {
        return ((TagNode)(node).evaluateXPath(xPathExpression)[0]).getText().toString().trim();
    }

    private String nodeOne(TagNode node,String xPathExpression,int elementIndex,int contextIndex) throws XPatherException {
        Object[] temp = (node).evaluateXPath(xPathExpression);
        return ((TagNode)temp[elementIndex]).getAllChildren().get(contextIndex).toString().trim();
    }
    private List<String> nodeAll(TagNode node,String xPathExpression) throws XPatherException {
        List<String> resultList = new ArrayList<>();
        Object[] nodes = node.evaluateXPath(xPathExpression);
        for (int i = 0; i < nodes.length; i++) {
            String content = ((TagNode)nodes[i]).getText().toString().trim();
            resultList.add(content);
        }
        return resultList;
    }



    public static void main(String[] args) throws IOException, XPatherException {
        Consumer consumer = new Consumer(null);
        consumer.process("160212");
    }

}
