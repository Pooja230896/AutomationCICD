package TestCases.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import TestCases.TestComponents.BaseTest;
import TestCases.pageobjects.CartPage;
import TestCases.pageobjects.CheckoutPage;
import TestCases.pageobjects.ConfirmationPage;
import TestCases.pageobjects.LandingPage;
import TestCases.pageobjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest{
	
	public LandingPage landingpage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException
	{
		landingpage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username,String password)
	{
		productCatalogue=landingpage.LoginApplication(username,password);

	}

	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException
	{
		List<WebElement> products=productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName)
	{
		 CartPage cartPage=productCatalogue.goToCartPage();
         Boolean match=cartPage.VerifyProductDisplay(productName);
         Assert.assertTrue(match);
         CheckoutPage checkoutPage=cartPage.goToCheckOut();
         checkoutPage.selectCountry("india");
         confirmationPage=checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string)
	{
		 String confirmMessage = confirmationPage.getConfirmationMessage();
         Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
         driver.close();
    
	}
	
	@Then("{string} message is displayed")
	public void something_message_is_displayed(String string) throws Throwable
	{
        Assert.assertEquals(string, landingpage.getErrorMessage());
        driver.close();
	}

}
