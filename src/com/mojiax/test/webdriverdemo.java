package com.mojiax.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
public class webdriverdemo {

	public static void main(String[] args) {
		List<String > str  = readFileByLines("./src/proxy1.txt");
		WebDriver driver =null;
		for(String s:str){
			try {
				System.out.println(s);
			
			String PROXY = s;

			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setHttpProxy(PROXY)
			     .setFtpProxy(PROXY)
			     .setSslProxy(PROXY);
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.PROXY, proxy);
			driver = new FirefoxDriver(cap);
			

        // And now use this to visit Google
        driver.navigate().to("http://www.mojiax.com/knowledge/detail?id=2098");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name        
        WebElement element = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/a"));
        element.click();
        // Enter something to search for
        //element.sendKeys("墨加");

        // Now submit the form. WebDriver will find the form for us from the element
       // element.submit();
        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie loadedCookie : allCookies) {
            System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
        }

        // Check the title of the page
//        System.out.println("Page title is: " + driver.getTitle());
        
        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
//        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().startsWith("墨加");
//            }
//        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());
        s=s+"\r\n";
        FileUtil.appendMethodB("./use.txt",s);
        Thread.currentThread().sleep(5000L);
        //Close the browser
        driver.quit(); 
			} catch (Exception e) {
				 driver.quit(); 
			}
		}
	}
	public static List<String> readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

}
