package process.house;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import tool.LoggerUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static process.house.Constant.baseUrl;
import static process.house.Constant.url;

/**
 * Created by clarechen on 16/9/11.
 */
public class Level1CodeList {

    public static void main(String[] args) {
        getLevel1CodeList();
    }

    public static List<String> getLevel1CodeList(){
        return Level1CodeList.process(url);
    }

    private static List<String> process(String url) {
        List<String> result = new ArrayList<>();
        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode root = cleaner.clean(new URL(url),"utf-8");

            Object[] divArray = root.evaluateXPath("//div[@class='filter-box']//div[@class='option-list gio_district']//a");

            for (int i = 1; i < divArray.length - 1; i++) {
                TagNode node = ((TagNode) divArray[i]);
                String codeString = node.getAttributeByName("href");
                String code = baseUrl+codeString;
                result.add(code);
                LoggerUtils.runtimelogger.info("{}",code);
            }
            LoggerUtils.runtimelogger.info("all size:{}",result.size());
        } catch (Throwable e) {
            LoggerUtils.runtimelogger.error(e.getMessage(),e);
        }
        return result;
    }
}
