package SmokeTesting;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import CustomReporter.emailScheduler;
import utils.latestFile;

public class batchSpectrumLogin {

public static void main(String[] args) throws Exception {
		
		while (true){
		String filePath = "N:\\QA\\R&DQA\\Selenium\\Scheduler Login\\ScheduledLogin.xlsx";
		File src=new File(filePath);
		FileInputStream fis=new FileInputStream(src);
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet sh1= wb.getSheetAt(0);
		int rowCount = sh1.getLastRowNum()-sh1.getFirstRowNum();
		
		emailScheduler sendReport = new  emailScheduler();
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		System.setProperty("webdriver.chrome.driver", "N://QA//R&DQA//Selenium//Drivers//chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("https://spectrum.experientevent.com/login");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		try {
			// Validate Spectrum Login page appears
			Boolean loginPage = driver.findElement(By.xpath("//*[@id='userName']")).isDisplayed();
			
			if (loginPage) {
				System.out.println("The Login page was loaded");
					
			// Enter the user name and password and click login button
			driver.findElement(By.id("userName")).sendKeys("sreejakeyans+AFH_sk@gmail.com");
			driver.findElement(By.id("password")).sendKeys("Sreejak@1");
			driver.findElement(By.xpath("//button[.='Login']")).click();
			driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			

			try {
				// Validate the Spectrum Home page
				Boolean searchButton = driver.findElement(By.xpath("//input[@placeholder='Enter search text']")).isDisplayed();
							
				if (searchButton){
					System.out.println("Login was Successful");
					sh1.createRow(rowCount+1).createCell(0).setCellValue("Login was successful");
					sh1.getRow(rowCount+1).createCell(1).setCellValue(timeStamp);
					driver.quit();
				}}
			 catch (Exception e) {
				System.out.println("Login failed");
				sh1.createRow(rowCount+1).createCell(0).setCellValue("Login Failed");
				sh1.getRow(rowCount+1).createCell(1).setCellValue(timeStamp);
				// Take screenshot and store as a file format
				File src1= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				try {
				 // now copy the  screenshot to desired location using copyFile //method
				FileUtils.copyFile(src1, new File("N:/QA/R&DQA/Selenium/Scheduler Login/Screenshots/"+System.currentTimeMillis()+".png"));
				}	 
				catch (IOException e1)
			    {
				System.out.println(e1.getMessage());
				}
				File screenshot = latestFile.lastFileModified("N:/QA/R&DQA/Selenium/Scheduler Login/Screenshots/");
			    sendReport.sendEmail(screenshot.getAbsolutePath());
				driver.quit();
			}}}
		catch (Exception e) {
			System.out.println("Login page was not loaded");
			sh1.createRow(rowCount+1).createCell(0).setCellValue("Login page was not loaded");
			sh1.getRow(rowCount+1).createCell(1).setCellValue(timeStamp);
			// Take screenshot and store as a file format
			File src1= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
			// now copy the  screenshot to desired location using copyFile //method
			FileUtils.copyFile(src1, new File("N:/QA/R&DQA/Selenium/Scheduler Login/Screenshots/"+System.currentTimeMillis()+".png"));
			} 
			catch (IOException e1)
			{
			 System.out.println(e1.getMessage());
			 }
			File screenshot = latestFile.lastFileModified("N:/QA/R&DQA/Selenium/Scheduler Login/Screenshots/");
			sendReport.sendEmail(screenshot.getAbsolutePath());
			driver.quit();
		}
		FileOutputStream fout=new FileOutputStream(new File(filePath));
		wb.write(fout);
		wb.close();
		fout.close();
		Thread.sleep(300000);
	}}}
