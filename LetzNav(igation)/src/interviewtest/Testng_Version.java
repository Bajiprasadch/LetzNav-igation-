package interviewtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.*; 

public class Testng_Version {
	 
	//Declaring WebDriver 
	WebDriver driver;
	WebDriverWait myWait;
	// Initializing an object for POM 
	PageObjectModel1 page = new PageObjectModel1(driver);

	//Opening Web page of LetzNav 
	@BeforeClass
	public void openBrowser() throws InterruptedException
	{
		// Initializing WebDriver 	
		driver = new ChromeDriver();
		//storing Web site URL as a String
		String BaseURL="https://letz-interview.herokuapp.com/login";
		// Open Web Application 
		driver.get(BaseURL);
		// Maximize Web Application 
		driver.manage().window().maximize();
		
		//Verifying page title........
				// Storing Web Application HomePage Title as expTitle 
				String expTitle="letzNav Admin"; 
				// Getting Web Application HomePage Title as actualTitle  
				String actualTitle=driver.getTitle();
				// Comparing expected and actual result of HomePage Title   
				if(actualTitle.equals(expTitle)){
					System.out.println("HomePage opened successfully"); // It prints the Output
				}
		Thread.sleep(5000);
	}

	//Enter userName and Password and click on the Login button.... 
	@Test(priority=0)
	public void login() throws Exception
	{
		// Declaring .xlsx path 
		String path=System.getProperty("user.dir")+"/letznavsheet.xlsx";
		File src = new File(path);
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		// OPening first sheet of xlsx 
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		// Storing of username and password in string 
		String username = sheet.getRow(1).getCell(0).getStringCellValue();
		String password = sheet.getRow(1).getCell(1).getStringCellValue();
		workbook.close(); // Closes workbook 
		PageObjectModel1 page = new PageObjectModel1(driver); // Initializing an object for POM  
		
		page.UN.sendKeys(username);		// Find username and fill userName 
		page.PWD.sendKeys(password);    // Find PWD and fill it 
		WebElement Login_Button = page.Submit;  
		
		if(Login_Button.isEnabled()){
			page.Submit.click();	 // Verify Login button is enabled 
		}else{
			System.out.println("Login button not enabled");  
		}
		Thread.sleep(5000);
		Assert.assertEquals(driver.getTitle(), "letzNav Admin"); // Verify Login Success 

	}

	//Verifying system settings section is displaying
	@Test(priority=1)
	public void systemSettings() throws InterruptedException
	{
		PageObjectModel1 page = new PageObjectModel1(driver); // Initializing an object for POM  
		
		page.SS.click(); // Click on System Settings Link on left down side 
		
		Thread.sleep(2000);
		String actualResult = driver.findElement(By.xpath("//span[contains(text(),'Roles')]")).getText(); // get text for Roles
		Assert.assertEquals(actualResult, "Roles"); // Comparing of expected and actual result for Roles  
		
	}

	//Verifying Add role is already present or create a new Role
	@Test(priority=2)
	public void addRole() throws InterruptedException, IOException
	{		
		// Provide RoleName and Description via xlsx
		String path=System.getProperty("user.dir")+"/letznavsheet.xlsx";
		File src = new File(path);
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(1);
		
		String rolename = sheet.getRow(1).getCell(0).getStringCellValue();
		String roledescription = sheet.getRow(1).getCell(1).getStringCellValue();
		workbook.close();		
		
		PageObjectModel1 page = new PageObjectModel1(driver);
		Thread.sleep(2000);
		page.Roles.click();
		
		Thread.sleep(5000);
		
		
		ArrayList<WebElement> rn = (ArrayList<WebElement>)driver.findElements(By.xpath("(//table/tbody/tr)/td[1]"));
				
		boolean status = false;
		
		for(int i=0; i<rn.size(); i++)
		{			
			if(rn.get(i).getText().contains("Test Interview"))
			{
				status=true;
				break;
			}
		}
		
		if (status==false)
		{
			page.AddRole.click();
			page.RoleName.sendKeys(rolename);
			page.RoleDesc.sendKeys(roledescription);
			
			Thread.sleep(4000);
			WebElement ele = driver.findElement(By.xpath("//div[@class='mat-select-trigger']")); 
			ele.click();
			
			Thread.sleep(4000);
			WebElement view = driver.findElement(By.xpath("//md-option[@id='md-option-127']")); 
			view.click(); 
			Thread.sleep(4000);
			page.AddButton_Role.click();
			Thread.sleep(4000);			
			
			String ActualResult=driver.findElement(By.xpath("//table/tbody/tr[11]/td[1]")).getText();
			Assert.assertEquals(ActualResult, "Test Interview");		
			
			System.out.println("Role Name is added successfully");
		}else{		
		Assert.assertTrue(status);
		System.out.println("Role Name is already exist");
		}		
	}

	//Verifying Add User is already present or else create a new User
	@Test(priority=3)
	public void addUser() throws InterruptedException, IOException
	{
		String path=System.getProperty("user.dir")+"/letznavsheet.xlsx";
		File src = new File(path);
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(2);
		
		String name = sheet.getRow(1).getCell(0).getStringCellValue();
		String email = sheet.getRow(1).getCell(1).getStringCellValue();
		workbook.close();			
		
		PageObjectModel1 page = new PageObjectModel1(driver);
		Thread.sleep(2000);
		page.Users.click();
		Thread.sleep(4000);
	
		ArrayList<WebElement> users = (ArrayList<WebElement>)driver.findElements(By.xpath("(//table/tbody/tr)/td[1]"));
		boolean status = false;
		
		for(int i=0; i<users.size(); i++)
		{			
			if(users.get(i).getText().contains("BajiPrasadCH"))
			{
				status=true;
				break;				
			}
		}
		
		if (status==false)
		{
			page.AddUser.click();
			Thread.sleep(4000);
			page.User_Active_CheckBox.click();
			Thread.sleep(4000);
			page.User_Name.sendKeys(name);
			page.User_Email.sendKeys(email);
			 
			Thread.sleep(6000);
			
			Actions dropdown = new Actions(driver);
			WebElement w1= driver.findElement(By.xpath("//md-select/div/span"));
			dropdown.moveToElement(w1).doubleClick().build().perform();

			Thread.sleep(6000);
			
			WebElement w2= driver.findElement(By.xpath("//md-option[11]"));
			dropdown.moveToElement(w2).doubleClick().build().perform();
			
			
			Thread.sleep(6000);
			driver.findElement(By.xpath("//div[@class='mat-radio-label-content']")).click(); 
			Thread.sleep(6000);
			driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
			
			Thread.sleep(10000);
			driver.navigate().refresh();
			Thread.sleep(10000);
			
			String actualResult = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]")).getText();
			Assert.assertEquals(actualResult,"BajiPrasadCH");
			System.out.println("User is added successfully");
		 
		}else{
		
		Assert.assertTrue(status);
		System.out.println("User Name is already exist");
		}
	}

	// Close the Web Application 
	@AfterClass
	public void close()
	{
		driver.close();
	}

}
