package TestCases.tests;

import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import TestCases.TestComponents.BaseTest;
import TestCases.pageobjects.CartPage;
import TestCases.pageobjects.CheckoutPage;
import TestCases.pageobjects.ConfirmationPage;
import TestCases.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{

	@Test(groups={"ErrorHandling"},retryAnalyzer=Retry)
	public void LoginErrorValidation() throws IOException, InterruptedException
	{
		
		       String productName = "ZARA COAT 3";
               landingpage.LoginApplication("Practice1@gmail.com", "Practice1@");
               Assert.assertEquals("Incorrect email or password", landingpage.getErrorMessage());
          
	}
	
	@Test
	public void ProductErrorvalidation() throws IOException, InterruptedException
	{
		
		       String productName = "ZARA COAT 3";
               ProductCatalogue productCatalogue =landingpage.LoginApplication("Practice2@gmail.com", "Practice2@");
               List<WebElement> products=productCatalogue.getProductList();
               productCatalogue.addProductToCart(productName);
               CartPage cartPage=productCatalogue.goToCartPage();
               
               Boolean match=cartPage.VerifyProductDisplay("ZARA COAT 33");
               Assert.assertFalse(match);
               
	}

}
