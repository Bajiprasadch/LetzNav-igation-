package slot;

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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Testng_Version {

	WebDriver driver;
	WebDriverWait myWait;
	PageObjectModel page;
	String rolename;
	String roledescription;
	String username;
	String password;
	String name;
	String email;

	private void readValuesFromWorkbook() throws IOException {
		String path = System.getProperty("user.dir") + "/letznavsheet.xlsx";
		File src = new File(path);
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		XSSFSheet sheet = workbook.getSheetAt(1);
		rolename = sheet.getRow(1).getCell(0).getStringCellValue();
		roledescription = sheet.getRow(1).getCell(1).getStringCellValue();

		XSSFSheet sheet1 = workbook.getSheetAt(0);
		username = sheet1.getRow(1).getCell(0).getStringCellValue();
		password = sheet1.getRow(1).getCell(1).getStringCellValue();

		XSSFSheet sheet2 = workbook.getSheetAt(2);
		name = sheet2.getRow(1).getCell(0).getStringCellValue();
		email = sheet2.getRow(1).getCell(1).getStringCellValue();

		workbook.close();
	}

	@BeforeTest
	public void openBrowser() throws InterruptedException, IOException {
		/*final File file = new File("C:\\Users\\sbadani\\Downloads\\newCrome\\chromedriver_win32\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());*/

		driver = new ChromeDriver();
		page = new PageObjectModel(driver);
		String BaseURL = "https://letz-interview.herokuapp.com/login";

		// Open Web Application
		driver.get(BaseURL);
		driver.manage().window().maximize();

		readValuesFromWorkbook();

		// Verifying page title........
		String expTitle = "letzNav Admin";
		String actualTitle = driver.getTitle();
		if (actualTitle.equals(expTitle)) {
			System.out.println("HomePage opened successfully");
		}
	}

	// Enter userName and Password and click on the Login button....
	@Test(priority = 0)
	public void login() throws Exception {
		page.UN.sendKeys(username);
		page.PWD.sendKeys(password);
		WebElement Login_Button = page.Submit;

		if (Login_Button.isEnabled()) {
			page.Submit.click();
		} else {
			System.out.println("Login button not enabled");
		}
		Thread.sleep(2000);
		Assert.assertEquals(driver.getTitle(), "letzNav Admin");

	}

	// Verifying system settings section is displaying
	@Test(priority = 1)
	public void systemSettings() throws InterruptedException {
		page.SS.click();

		Thread.sleep(2000);
		String actualResult = driver.findElement(By.xpath("//span[contains(text(),'Roles')]")).getText();
		Assert.assertEquals(actualResult, "Roles");
	}

	// Verifying Add role is already present or create a new Role
	@Test(priority = 2)
	public void addRole() throws InterruptedException, IOException {
		page.Roles.click();
		Thread.sleep(2000);

		// Stores All Roles names in a WebElement
		ArrayList<WebElement> rn = (ArrayList<WebElement>) driver.findElements(By.xpath("(//table/tbody/tr)/td[1]"));

		boolean status = false; // Assumes that Role is not present then go to 'For loop' to validate it

		for (int i = 0; i < rn.size(); i++) {
			if (rn.get(i).getText().contains("Test Interview")) {
				status = true;
				break;
			}
		}

		if (status == false) {
			page.AddRole.click();
			page.RoleName.sendKeys(rolename);
			page.RoleDesc.sendKeys(roledescription);

			Thread.sleep(2000);
			// Find Drop down list box of Permissions
			WebElement ele = driver.findElement(By.xpath("//div[@class='mat-select-trigger']"));
			ele.click(); // Clicks Drop down list box

			Thread.sleep(2000);
			// Find a given permission from the drop down list box
			WebElement view = driver.findElement(By.xpath("//md-option[@id='md-option-127']"));
			view.click(); // Clicks on above find permission value
			Thread.sleep(2000);
			page.AddButton_Role.click();
			Thread.sleep(3000);
			String ActualResult = driver.findElement(By.xpath("//table/tbody/tr[11]/td[1]")).getText();
			Assert.assertEquals(ActualResult, "Test Interview");

			System.out.println("Role Name is added successfully");
		} else {
			Assert.assertTrue(status);
			System.out.println("Role Name already exists");
		}
	}

	// Verifying Add User is already present or else create a new User
	@Test(priority = 3)
	public void addUser() throws InterruptedException, IOException {
		page.Users.click();
		Thread.sleep(2000);

		ArrayList<WebElement> users = (ArrayList<WebElement>) driver.findElements(By.xpath("(//table/tbody/tr)/td[1]"));
		boolean status = false; // Assume that User is not present then go to 'For loop' to validate it

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getText().contains("BajiPrasadCH")) {
				status = true;
				break;
			}
		}
		if (status == false) {
			page.AddUser.click();
			Thread.sleep(2000);
			page.User_Active_CheckBox.click();
			Thread.sleep(2000);
			page.User_Name.sendKeys(name);
			page.User_Email.sendKeys(email);

			Thread.sleep(2000);

			Actions dropdown = new Actions(driver);
			WebElement w1 = driver.findElement(By.xpath("//md-select/div/span"));
			dropdown.moveToElement(w1).doubleClick().build().perform();

			Thread.sleep(4000);

			WebElement w2 = driver.findElement(By.xpath("//md-option[11]"));
			dropdown.moveToElement(w2).doubleClick().build().perform();

			Thread.sleep(4000);
			driver.findElement(By.xpath("//div[@class='mat-radio-label-content']")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

			Thread.sleep(8000);
			driver.navigate().refresh();
			Thread.sleep(10000);

			String actualResult = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]")).getText();
			Assert.assertEquals(actualResult, "BajiPrasadCH");
			System.out.println("User is added successfully");

		} else {
			Assert.assertTrue(status);
			System.out.println("User Name is already exist");
		}
	}

	// Close the Web Application
	@AfterClass
	public void close() {
		driver.close();
	}

}