package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

public class AdventurePage {
    RemoteWebDriver driver;

    @FindBy(id = "duration-select")
    private WebElement durationDropDown;

    @FindBy(id = "category-select")
    private WebElement categoryDropDown;

    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    private WebElement clearDuration;

    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    private WebElement clearCategory;

    @FindBy(id = "search-adventures")
    WebElement searchAdventure;

    @FindBy(xpath = "//div[@class='col-6 col-lg-3 mb-4']")
    WebElement selectAdventure;

    @FindBy(id = "data")
    private WebElement adventureDataContainer;

    public AdventurePage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    // Filter the adventures by Duration.
    public void setFilterValue(String filterValue) {
        // We got the drop down from the AUT
        // Then we are searching the dropdown using the Select class methods.
        Select searchDropDownBox = new Select(durationDropDown);
        searchDropDownBox.selectByVisibleText(filterValue);
    }

    // Filter the adventures by category.
    public void setCategoryValue(String categoryValue) {
        Select searchDropDownBox = new Select(categoryDropDown);
        searchDropDownBox.selectByVisibleText(categoryValue);
    }

    public void searchAdventure(String adventureName) throws InterruptedException {
        Thread.sleep(3000);
        searchAdventure.sendKeys(adventureName);

    }

    public void clickAdventure() throws InterruptedException {
        Thread.sleep(3000);
        selectAdventure.click();

    }

    public void clearCategory() {
        this.clearCategory.click();
    }

    public void clearDuration() {
        this.clearDuration.click();
    }

    // Count the number of adventures after filteration
    public int getResultCount() {

        // Finding and getting all the search results and returning the count of adventures.
        List<WebElement> adventures = adventureDataContainer.findElements(By.xpath("./*"));
        return adventures.size();
    }

    // Select the desired adventure
    public void selectAdventure(String adventureName) {

        if (getResultCount() != 0) {
            WebElement adventure = adventureDataContainer
                    .findElement(By.xpath("//h5[text()='" + adventureName + "']"));
            adventure.click();
        } else {
            System.out.println("There are no adventures for the chosen filters");
        }
    }

}
