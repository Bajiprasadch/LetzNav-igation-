package slot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObjectModel {

	public PageObjectModel(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// We are implementing Page Object Model (POM). It is a design pattern to create
	// Object Repository for web UI elements.
	// Under this model, for each web page in the application, there should be
	// corresponding page class.
	// This Page class will find the WebElements of that web page and also contains
	// Page methods which perform operations on those WebElements.

	@FindBy(name = "username")
	public WebElement UN;

	@FindBy(name = "password")
	public WebElement PWD;

	@FindBy(xpath = "//button[@class='btn btn-primary btn-block']")
	public WebElement Submit;

	@FindBy(xpath = "//a[@href='/admin/system-settings']")
	public WebElement SS;

	// Roles Section under SS
	@FindBy(xpath = "//span[contains(text(),'Roles')]")
	public WebElement Roles;

	// Add Role
	@FindBy(xpath = "(//*[contains(.,'Add Role')])[12]")
	public WebElement AddRole;

	@FindBy(id = "role_name")
	public WebElement RoleName;

	@FindBy(id = "role_desc")
	public WebElement RoleDesc;

	@FindBy(xpath = "//button[@class='btn btn-primary']")
	public WebElement AddButton_Role;

	// Users section under SS
	@FindBy(xpath = "//span[contains(text(),'Users')]")
	public WebElement Users;

	// Add User
	@FindBy(xpath = "(//*[contains(.,'Add User')])[12]")
	public WebElement AddUser;

	// Check box for Active User
	@FindBy(xpath = "//div[@class='mat-checkbox-inner-container']")
	public WebElement User_Active_CheckBox;

	// User_Name = Full Name of User
	@FindBy(name = "full_name")
	public WebElement User_Name;

	// User_Email = Email ID of User
	@FindBy(name = "email")
	public WebElement User_Email;

}
