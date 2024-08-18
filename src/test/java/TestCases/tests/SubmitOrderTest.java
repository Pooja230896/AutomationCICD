package TestCases.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import TestCases.TestComponents.BaseTest;
import TestCases.pageobjects.CartPage;
import TestCases.pageobjects.CheckoutPage;
import TestCases.pageobjects.ConfirmationPage;
import TestCases.pageobjects.LandingPage;
import TestCases.pageobjects.OrderPage;
import TestCases.pageobjects.ProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest{
	 String productName = "ZARA COAT 3";
	@Test(dataProvider="getData",groups={"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
		      
               ProductCatalogue productCatalogue =landingpage.LoginApplication(input.get("email"),input.get("password"));
               List<WebElement> products=productCatalogue.getProductList();
               productCatalogue.addProductToCart(input.get("product"));
               CartPage cartPage=productCatalogue.goToCartPage();
               
               Boolean match=cartPage.VerifyProductDisplay(input.get("product"));
               Assert.assertTrue(match);
               CheckoutPage checkoutPage=cartPage.goToCheckOut();
               checkoutPage.selectCountry("india");
               ConfirmationPage confirmationPage=checkoutPage.submitOrder();
               
               String confirmMessage = confirmationPage.getConfirmationMessage();
               Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));             
          
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
        ProductCatalogue productCatalogue =landingpage.LoginApplication("Practice1@gmail.com", "Practice1@");
        OrderPage ordersPage=productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		//HashMap<String,String> map= new HashMap<String,String>();
		//map.put("email", "Practice1@gmail.com");
		//map.put("password", "Practice1@");
		//map.put("product","ZARA COAT 3");
		
		//HashMap<String,String> map1= new HashMap<String,String>();
		//map1.put("email", "Practice2@gmail.com");
		//map1.put("password", "Practice2@");
		//map1.put("product","ADIDAS ORIGINAL");
		//return new Object[][] {{map},{map1}};
		List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\TestCases\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
	}
	//@DataProvider
	//public Object[][] getData()
	//{
		//return new Object[][] {{"Practice1@gmail.com","Practice1@","ZARA COAT 3"},{"Practice2@gmail.com","Practice2@","ADIDAS ORIGINAL"}};
	//}
	
	



}
