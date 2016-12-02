package process.house;

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
    private final static String template="http://sh.lianjia.com@id";

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
                Thread.sleep(100);//由于链家限速,设置密度
            } catch (Exception e) {
                LoggerUtils.runtimelogger.error("faild process:"+code,e);
            }
        }
    }

    public void process(String code) throws IOException, XPatherException {
        String url = getUrl(code);
        System.out.println(url);
        TagNode root = cleaner.clean(new URL(url));
        String level2 = code.split("/")[2];
        for(Object one:root.evaluateXPath("//ul[@class='house-lst']//li"))parseOneHouse((TagNode) one,level2);
    }

    private void parseOneHouse(TagNode node,String level2Data) throws XPatherException {
        TagNode root = (TagNode)node.evaluateXPath("div[@class='info-panel']")[0];
        //1title
        String title = nodeOne(root,"/h2//a[@name='selectDetail']");
        //2位置
        String xiaoqu = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='where']//span",0,0);
        //3户型
        String type   = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='where']//span",1,0).replace("&nbsp;","");
        //4大小
        String size   = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='where']//span",2,0).replace("&nbsp;","").replace("平","");
        //5一级区域
        String level1 = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='other']//a",0,0);
        //6二级区域
        String level2 = level2Data;
        //7楼层
        String high = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='other']//div",0,4);
        //8朝向
        String face = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='other']//div",0,6);
        //9建造时间
        String buildTime = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-1']")[0],"//div[@class='other']//div",0,8);
        if(face!=null && !face.equals("") && !face.contains("朝")){buildTime=face;face="";}
        //10总价
        String price = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-3']")[0],"//span[@class='num']");
        //11每平价格
        String price2 = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-3']")[0],"//div[@class='price-pre']").replace("元/平","");
        //12看房人数
        String lookSize = nodeOne((TagNode)root.evaluateXPath("//div[@class='col-2']")[0],"//span[@class='num']");

        //13id
        String id = ((TagNode)root.evaluateXPath("/h2//a[@name='selectDetail']")[0]).getAttributeByName("href").substring(12).replace(".html","");


        LoggerUtils.monitorlogger1.info("{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}",
                title,xiaoqu,type,size,level1,level2,high,face,buildTime,price,price2,lookSize,id);
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
        try{return ((TagNode)temp[elementIndex]).getAllChildren().get(contextIndex).toString().trim();}catch(Exception e){return "";}
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
        consumer.process("/ershoufang/pudongxinqu/d1");
    }

}
