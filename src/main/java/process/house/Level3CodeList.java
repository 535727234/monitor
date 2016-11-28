package process.house;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import tool.LoggerUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static process.house.Constant.baseUrl;

/**
 * Created by clarechen on 16/9/11.
 */
public class Level3CodeList {

    public static void main(String[] args){
//        process(url);
        getCodeList().stream().forEach(one->LoggerUtils.runtimelogger.info(one));
    }

    public static List<String> getCodeList(){
        return Level2CodeList.getLevel2CodeList().stream().flatMap(url-> Level3CodeList.process(url).stream()).collect(Collectors.toList());
    }

    private static List<String> process(String code) {
        List<String> result = new ArrayList<>();
        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode root = cleaner.clean(new URL(baseUrl+code),"utf-8");

            Object[] divArray = root.evaluateXPath("//div[@class='page-box house-lst-page-box']//a");

            int pageSize=1;
            if(divArray.length>2) {
                TagNode node = ((TagNode) divArray[divArray.length - 2]);
                pageSize = Integer.valueOf(node.getText().toString().trim());
            }
            for(int i=1;i<=pageSize;i++){
                result.add(code+"d"+i);
            }
            LoggerUtils.runtimelogger.info("all size:{}",result.size());
        } catch (Throwable e) {
            LoggerUtils.runtimelogger.error("process error:"+code,e);
        }
        return result;
    }
}
