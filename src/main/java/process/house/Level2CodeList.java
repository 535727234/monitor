package process.house;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import tool.LoggerUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by clarechen on 16/9/11.
 */
public class Level2CodeList {

    public static void main(String[] args) {
        getLevel2CodeList();
    }

    public static List<String> getLevel2CodeList(){
        return Level1CodeList.getLevel1CodeList().stream().flatMap(url->Level2CodeList.process(url).stream()).collect(Collectors.toList());
    }

    private static List<String> process(String url) {
        List<String> result = new ArrayList<>();
        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode root = cleaner.clean(new URL(url),"utf-8");

            Object[] divArray = root.evaluateXPath("//div[@class='option-list sub-option-list gio_plate']//a");

            for (int i = 1; i < divArray.length - 1; i++) {
                TagNode node = ((TagNode) divArray[i]);
                String codeString = node.getAttributeByName("href");
                result.add(codeString);
                LoggerUtils.runtimelogger.info("{}",codeString);
            }
            LoggerUtils.runtimelogger.info("all size:{}",result.size());
        } catch (Throwable e) {
            LoggerUtils.runtimelogger.error(e.getMessage(),e);
        }
        return result;
    }
}
