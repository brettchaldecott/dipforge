/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsSeleniumEngine {
     
    private static ThreadLocal<WebRoboticsSeleniumEngine> context = new ThreadLocal<WebRoboticsSeleniumEngine>();
    
    // class constants
    ChromeOptions chromeOptions = new ChromeOptions();
    {
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
    }
        
    // private member variables
    private List<WebDriver> drivers = new ArrayList<WebDriver>();
    
    private WebRoboticsSeleniumEngine() {
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        
    }
    
    private void fin() {
        for (WebDriver driver : drivers) {
            driver.quit();
        }
    }
    
    public WebDriver openUrl(String url) {
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            // ignore
        }
        drivers.add(driver);
        return driver;
    }
    
    public static WebRoboticsSeleniumEngine getContext() {
        return context.get();
    }
    
    public static void initContext() {
        context.set(new WebRoboticsSeleniumEngine());
    }
    
    public static void finContext() {
        context.get().fin();
    }
    
}
