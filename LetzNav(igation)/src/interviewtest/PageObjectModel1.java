package interviewtest;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObjectModel1 {
	
	
	public PageObjectModel1(WebDriver driver){
		PageFactory.initElements(driver, this);
		
	}

	
	@FindBy(name="username")
	public WebElement UN;
	
	@FindBy(name="password")
	public WebElement PWD;
	
	@FindBy(xpath="//button[@class='btn btn-primary btn-block']")
	public WebElement Submit;
	
	
	//SS= systemsettings
	@FindBy(xpath="//a[@href='/admin/system-settings']")
	public WebElement SS;
	
	
	@FindBy(xpath="//span[contains(text(),'Roles')]")
	public WebElement Roles;
	
	
	@FindBy(xpath="(//*[contains(.,'Add Role')])[12]")
	public WebElement AddRole;
	
	
	@FindBy(id="role_name")
	public WebElement RoleName;
	
	
	@FindBy(id="role_desc")
	public WebElement RoleDesc;
	
	
	@FindBy(xpath="//button[@class='btn btn-primary']")
	public WebElement AddButton_Role;
	
	
	@FindBy(xpath="//span[contains(text(),'Users')]")
	public WebElement Users;
	
	
	@FindBy(xpath="(//*[contains(.,'Add User')])[12]")
	public WebElement AddUser;
	
	
	@FindBy(xpath="//div[@class='mat-checkbox-inner-container']")
	public WebElement User_Active_CheckBox;
	
	@FindBy(name="full_name")
	public WebElement User_Name;
	
	@FindBy(name="email")
	public WebElement User_Email;
	
	
	
	
	
	
}
