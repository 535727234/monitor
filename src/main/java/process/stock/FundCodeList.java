package process.stock;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import tool.LoggerUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clarechen on 16/9/11.
 */
public class FundCodeList {
    private static String url = "http://fund.eastmoney.com/allfund.html";

    public static void main(String[] args) {
        System.out.println(getFundCodeList().size());
    }

    public static List<String> getFundCodeList(){
        return FundCodeList.process(url);
    }

    private static List<String> process(String url) {
        List<String> result = new ArrayList<>();
        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode root = cleaner.clean(new URL(url),"gb2312");

            Object[] divArray = root.evaluateXPath("//div[@id='code_content']//li/div");

            for (int i = 0; i < divArray.length - 1; i++) {
                Object[] trArray = ((TagNode) divArray[i]).evaluateXPath("//a");
                String codeString = ((TagNode) trArray[0]).getText().toString().trim();
                String code = codeString.substring(1,7);
                result.add(code);
                LoggerUtils.runtimelogger.info("{},{}", code,codeString.substring(8));
            }
            LoggerUtils.runtimelogger.info("all size:{}",result.size());
        } catch (Throwable e) {
            LoggerUtils.runtimelogger.error(e.getMessage(),e);
        }
        return result;
    }
}
