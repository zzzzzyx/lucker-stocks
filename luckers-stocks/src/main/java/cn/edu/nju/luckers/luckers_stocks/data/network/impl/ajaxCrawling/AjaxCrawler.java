package cn.edu.nju.luckers.luckers_stocks.data.network.impl.ajaxCrawling;
//package data.network.impl.ajaxCrawling;
//
//import java.util.List;
//
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
//import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlElement;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//

//public class AjaxCrawler {
//
//	public static void main(String[] args) {
//		
//		WebClient webClient = new WebClient(BrowserVersion.EDGE);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.setJavaScriptTimeout(5000);
//        try {
//            HtmlPage page = webClient.getPage("http://quotes.money.163.com/old/#query=leadIndustry&DataType=industryPlate&sort=PERCENT&order=desc&count=25&page=0");
//            webClient.waitForBackgroundJavaScript(1000*3);  
//
//            String s = page.asXml();
//            System.out.println(s.split("150")[0]);
//        } catch (FailingHttpStatusCodeException e) {
//            e.printStackTrace();
//        } catch (Throwable e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        webClient.close();
//	}
//}
