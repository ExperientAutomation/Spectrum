package SmokeTesting;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import CustomReporter.emailReport;


public class SpectrumConcurrency {
//	WebDriver driver = new FirefoxDriver();
	WebDriver driver = null;
	WebDriverWait wait = null;// 1 minute
//	Util util = new Util();

	@BeforeTest
	public void Login(){
		System.setProperty("webdriver.ie.driver","C:\\Program Files (x86)\\Java\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		wait = new WebDriverWait(driver, 60);
		//Launch the browser
		driver.get("https://devspectrum.experientevent.com/login");
		driver.manage().window().maximize();

		// Validate Spectrum Login page appears
		Boolean loginpage = driver.findElement(By.xpath("//*[@id='userName']")).isDisplayed();
		if (loginpage) {
			System.out.println("Passed - The Login page was loaded");
		}
		else {
			System.out.println("Failed - Login page was not loaded");
		}

		// Enter the user name and password and click login button
		driver.findElement(By.id("userName")).sendKeys("sreejakeyans+IEC@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Sreejak@1");
		driver.findElement(By.xpath("//button[.='Login']")).click();

		// Validate the Spectrum Home page

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter search text']")));
		System.out.println("Passed - Home page was loaded");

	}
	@Test
	public void destinations() {
		// Click on Destination tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@routerlink='/destination']")));
		driver.findElement(By.xpath("//a[@routerlink='/destination']")).click();

		//Validate Destination page appears
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		System.out.println("Passed - Destination page appeared");

		//Click Save of two destinations (to favorite)
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][1]//button[@title='Favorite']")));
		WebElement savebutton1 = driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[@title='Favorite']"));
		savebutton1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][2]//button[@title='Favorite']")));
		WebElement savebutton2 = driver.findElement(By.xpath("//div[@class='column card-item'][2]//button[@title='Favorite']"));
		savebutton2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Validate if the favorites were added successfully
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		if (savebutton1.getText().contentEquals("Saved") && savebutton2.getText().contentEquals("Saved")) {			
			System.out.println("Passed - The favorites were added successfully");			
		}else {
			System.out.println("Failed - The favorites were not added");
		}

		// Click Saved Destinations under My Filters
		//driver.findElement(By.xpath("//accordion/accordion-item[1]/a")).click();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//accordion-item[1]/div/ul/li/input-radio/button")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Validate that only favorite destinations appeared
		int numofdestinations = driver.findElements(By.xpath("//div[@class='column card-item']")).size();
		if (numofdestinations == 2){
			System.out.println("Passed - The Saved Destination filter works fine upon checking it");
		}else {
			System.out.println("Failed - The Saved Destination filter does not work upon checking it");
		}

		//Click Saved Destinations again to unselect
		driver.findElement(By.xpath("//accordion/accordion-item[1]/div/ul/li/input-radio/button")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		// Validate all the destinations are displayed
		int numofdestinations1 = driver.findElements(By.xpath("//div[@class='column card-item']")).size();
		if (numofdestinations1 == 30) {
			System.out.println("Passed - The Saved Destination filter works fine upon unchecking it");
		}else {
			System.out.println("Failed - The Saved Destination filter does not work upon unchecking it");
		}

		// Click on Saved buttons to unsave them
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][1]//button[@title='Favorite']")));
		savebutton1 = driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[@title='Favorite']"));
		savebutton1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][2]//button[@title='Favorite']")));
		savebutton2 = driver.findElement(By.xpath("//div[@class='column card-item'][2]//button[@title='Favorite']"));
		savebutton2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		// Validate if the favorites were removed successfully
		if (savebutton1.getText().equals("Save") && savebutton2.getText().equals("Save")) {			
			System.out.println("Passed - The favorites were removed successfully");			
		}else {
			System.out.println("Failed - The favorites were not removed");
		}	

		// Click on compare button and check it changes to Remove
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//destination-list/div/div[2]/div/div[1]/div/div[1]/destination-comparison-queue/button")));
		WebElement comparebutton1 = driver.findElement(By.xpath("//destination-list/div/div[2]/div/div[1]/div/div[1]/destination-comparison-queue/button"));
		comparebutton1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//destination-list/div/div[2]/div/div[2]/div/div[1]/destination-comparison-queue/button")));
		WebElement comparebutton2 = driver.findElement(By.xpath("//destination-list/div/div[2]/div/div[2]/div/div[1]/destination-comparison-queue/button"));
		comparebutton2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		if (comparebutton1.getText().equals("Remove") && comparebutton2.getText().equals("Remove")) {			
			System.out.println("Passed - Clicking on the Compare buttons changes it to Remove");			
		}else {
			System.out.println("Failed - Compare buttons does not work.");
		}

		//Click on the Number displayed at the Destination tab heading and validate the count
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='badge success has-tip']")));
		WebElement compareNum = driver.findElement(By.xpath("//span[@class='badge success has-tip']"));
		if (compareNum.getText().contains("2")) {
			System.out.println("Passed - The compare number count works fine");
		}else {
			System.out.println("Failed - The compare number count does not work");
		}

		compareNum.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Click on the Compare Destinations button on the expanded screen
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='small button success expanded']")));
		driver.findElement(By.xpath("//button[@class='small button success expanded']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Validate the Compare Destinations page appeared
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//destination-comparison/div/div/div[3]/div[1]/div[1]")));
		System.out.println("Passed - Compare Destinations page appeared");

		//Click on Destinations tab to return to all destinations 
		driver.findElement(By.xpath("//a[@routerlink='/destination']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		
		//Click on the Compare buttons to Remove them
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//destination-list/div/div[2]/div/div[1]/div/div[1]/destination-comparison-queue/button")));
		comparebutton1 = driver.findElement(By.xpath("//destination-list/div/div[2]/div/div[1]/div/div[1]/destination-comparison-queue/button"));
		comparebutton1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//destination-list/div/div[2]/div/div[2]/div/div[1]/destination-comparison-queue/button")));
		comparebutton2 = driver.findElement(By.xpath("//destination-list/div/div[2]/div/div[2]/div/div[1]/destination-comparison-queue/button"));
		comparebutton2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		
		//Click More Info of any of the destination
		
		//try{
			//Thread.sleep(30000);
			//WebElement infoButtn = util.getElementByFluentWait(driver, By.xpath("//div[@class='column card-item'][1]//button[contains(text(),'More Info')]"));
			//infoButtn.click();
		//}catch(Exception ex){
			//ex.printStackTrace();
		//}
		//driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[contains(text(),'More Info')]")).click();
		driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[@class='card-image']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//destination-overview")));
		System.out.println("Passed - Destination Detail page appeared");

		//Click Venue tab
		driver.findElement(By.xpath("//a[@routerlink='./venue']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/venue-list")));
		System.out.println("Passed - Venue page appeared");

		//Click Map tab
		driver.findElement(By.xpath("//a[@routerlink='./map']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//map-facilities/div/bing-map")));
		System.out.println("Passed - Map page appeared");
	}

	@Test(priority=1)
	public void venues() {
		
		//Click Venues tab
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@routerlink='/venue']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		System.out.println("Passed - All the Venues were loaded");
		
		//Click Save of two venues
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][1]//button[@title='Save']")));
		WebElement savevenue1 = driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[@title='Save']"));
		savevenue1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][2]//button[@title='Save']")));
		WebElement savevenue2 = driver.findElement(By.xpath("//div[@class='column card-item'][2]//button[@title='Save']"));
		savevenue2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		// Validate if the Venue favorites were added successfully
		if (savevenue1.getText().equals("Saved") && savevenue2.getText().equals("Saved")) {			
			System.out.println("Passed - The Venue favorites were added successfully");			
		}else {
			System.out.println("Failed - The Venue favorites were not added");
		}	
		
		// Click Saved Venues under My Filters
		driver.findElement(By.xpath("//accordion/accordion-item[1]/div/ul/li/input-radio/button")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Validate that only saved venues appeared
		int numofvenues = driver.findElements(By.xpath("//div[@class='column card-item']")).size();
		if (numofvenues == 2){
				System.out.println("Passed - The Saved Venues filter works fine upon checking it");
			}else {
				System.out.println("Failed - The Saved Venues filter does not work upon checking it");
			}

		//Click Saved Venues radio button again to unselect
		driver.findElement(By.xpath("//accordion/accordion-item[1]/div/ul/li/input-radio/button")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		// Validate all the venues are displayed
		int numofvenues1 = driver.findElements(By.xpath("//div[@class='column card-item']")).size();
		if (numofvenues1 == 24) {
				System.out.println("Passed - The Saved Venues filter works fine upon unchecking it");
			}else {
				System.out.println("Failed - The Saved Venues filter does not work upon unchecking it");
				}
		
		// Click on Saved buttons to unsave them
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][1]//button[@title='Save']")));
		savevenue1 = driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[@title='Save']"));
		savevenue1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column card-item'][2]//button[@title='Save']")));
		savevenue2 = driver.findElement(By.xpath("//div[@class='column card-item'][2]//button[@title='Save']"));
		savevenue2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		// Validate if the save venues were removed successfully
		if (savevenue1.getText().equals("Save") && savevenue2.getText().equals("Save")) {			
				System.out.println("Passed - The saved venues were removed successfully");			
			}else {
				System.out.println("Failed - The saved venues were not removed");
			}	
		
		// Click on compare button and check it changes to Remove
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//venue-list/div/div[2]/div/div[1]/div[1]/div/venue-comparison-queue/button")));
		WebElement comparevenue1 = driver.findElement(By.xpath("//venue-list/div/div[2]/div/div[1]/div[1]/div/venue-comparison-queue/button"));
		comparevenue1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//venue-list/div/div[2]/div/div[2]/div[1]/div/venue-comparison-queue/button")));
		WebElement comparevenue2 = driver.findElement(By.xpath("//venue-list/div/div[2]/div/div[2]/div[1]/div/venue-comparison-queue/button"));
		comparevenue2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));

		if (comparevenue1.getText().equals("Remove") && comparevenue2.getText().equals("Remove")) {			
				System.out.println("Passed - Clicking on the Venue Compare buttons changes it to Remove");			
			}else {
				System.out.println("Failed - Venue Compare buttons does not work.");
			}

		//Click on the Number displayed at the Venues tab heading and validate the count
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='badge success has-tip']")));
		WebElement compareVenueNum = driver.findElement(By.xpath("//span[@class='badge success has-tip']"));
		if (compareVenueNum.getText().contains("2")) {
				System.out.println("Passed - The Venue compare number count works fine");
			}else {
				System.out.println("Failed - The Venue compare number count does not work");
				}

		compareVenueNum.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Click on the Compare Venues button on the expanded screen
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='small button success expanded']")));
		driver.findElement(By.xpath("//button[@class='small button success expanded']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Validate the Compare Venues page appeared
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//venue-comparison/div[2]/div[1]/sticky/div/div/div/div[2]")));
		System.out.println("Passed - Compare Venues page appeared");

		//Click on Venues tab to return to all Venues 
		driver.findElement(By.xpath("//a[@routerlink='/venue']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		
		//Click on the Compare buttons to remove them
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//venue-list/div/div[2]/div/div[1]/div[1]/div/venue-comparison-queue/button")));
		comparevenue1 = driver.findElement(By.xpath("//venue-list/div/div[2]/div/div[1]/div[1]/div/venue-comparison-queue/button"));
		comparevenue1.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//venue-list/div/div[2]/div/div[2]/div[1]/div/venue-comparison-queue/button")));
		comparevenue2 = driver.findElement(By.xpath("//venue-list/div/div[2]/div/div[2]/div[1]/div/venue-comparison-queue/button"));
		comparevenue2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		
		//Click More Info of any of the destination
		driver.findElement(By.xpath("//div[@class='column card-item'][1]//button[@class='card-image']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//venue-default-overview")));
		System.out.println("Passed - Venues Detail page appeared");
		
		//Click on Nearby Hotels tab
		driver.findElement(By.xpath("//a[@routerlink='./hotel']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='column card-item']")));
		System.out.println("Passed - Nearby Hotels page appeared");
		
		//Click Map tab
		driver.findElement(By.xpath("//a[@routerlink='./map']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//map-facilities/div/bing-map")));
		System.out.println("Passed - Map page appeared");		
	}


	@Test(priority=2)
	public void logOut(){		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//button[@title='Logout']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userName']")));
		System.out.println("Passed - Logged out of Spectrum successfully.");
		driver.quit();
	}
	@AfterTest
	public void sendReport(){
		emailReport sendreport = new  emailReport();
		sendreport.sendEmail();
	}
	
}
