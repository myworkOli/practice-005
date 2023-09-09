package steps;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class PageFood {

    public WebDriver driver;

    public PageFood(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * определяем локаторы полей
     */

    @FindBy(xpath = "//button[text()=\"Добавить\"]")
    private WebElement addBtn;
    @FindBy(xpath = "//*[@class=\"modal-content\"]")
    private WebElement productAddingForm;
    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement addName;
    @FindBy(xpath = "//select[@id=\"type\"]")
    private WebElement typeList;
    @FindBy(xpath = "//*[@id=\"exotic\"]")
    public WebElement typeExotic;
    @FindBy(xpath = "//button[@id=\"save\"]")
    private WebElement saveBtn;
    @FindBy(xpath = "//*[@class= 'table']/thead/tr")
    private WebElement tableRow;
    @FindBy(xpath = "//*[@class= 'table']/tbody/tr")
    private List<WebElement> tablRow;
    @FindBy(tagName = "tr")
    private List<WebElement> table ;

    public void inputAddName(CharSequence... keysToSend) {
        addName.sendKeys(keysToSend);
    }

    public void clickAddBtn() {
        addBtn.click();
    }

    public void clickAddName() {
        addName.click();
    }

    public void clickSaveBtn() {
        saveBtn.click();
    }

    public void clearAddName() {
        addName.clear();
    }

    public void selecType(String name) {
        Select addType = new Select(Hooks.driver.findElement(By.xpath("//select[@id=\"type\"]")));
        addType.selectByVisibleText(name);
    }

    public String getTextproductAddingForm() {
        Hooks.wait.until(elementToBeClickable(productAddingForm));
        String text = productAddingForm.getText();
        return text;
    }

    public void saveScreenshotAddingForm(String name) {
        File scr = productAddingForm.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(scr, new File("src\\test\\screenshot\\" + name + ".png"));
        } catch (IOException e) {
            throw new RuntimeException("screenshot не создан");
        }
    }
    public String getTextTableRow() {
        String text = tableRow.getText();
        ;
        return text;
    }
    public String getTextSelectForm() {
        String text = typeList.getText();
        return text;
    }
    public void checkCheckbox() {
        if (!typeExotic.isSelected()) {
            typeExotic.click();
            Assertions.assertTrue(typeExotic.isSelected(), "чекбокс не сменил статус на вкл.");
            typeExotic.click();
            Assertions.assertFalse(typeExotic.isSelected(), "чекбокс не сменил статус на выкл.");
        } else {
            typeExotic.click();
            Assertions.assertFalse(typeExotic.isSelected(), "чекбокс не сменил статус на выкл.");
            typeExotic.click();
            Assertions.assertTrue(typeExotic.isSelected(), "чекбокс не сменил статус на вкл.");
        }
    }
    public void addFood(Map<String, String> dataTable) {
        Select addType = new Select(Hooks.driver.findElement(By.xpath("//select[@id=\"type\"]")));
        addBtn.click();
        addName.sendKeys(dataTable.get("Наименование"));
        if (dataTable.get("Тип").contains("FRUIT")) {
            addType.selectByVisibleText("Фрукт");
        } else if (dataTable.get("Тип").contains("VEGETABLE")) {
            addType.selectByVisibleText("Овощ");
        } else {
            addType.selectByVisibleText(dataTable.get("Тип"));
        }
        System.out.println(dataTable.get("Экзотический").contains("false") + "dataTable.get(\"Экзотический\").contains(\"false\")");
        System.out.println((typeExotic.isSelected()) + "(addTypeExotic.isSelected())");
        if (dataTable.get("Экзотический").contains("true")) {
            if (!typeExotic.isSelected()) {
                typeExotic.click();
            }

        } else {
            if (typeExotic.isSelected()) {
                typeExotic.click();
            }
        }
        saveBtn.click();
        //дождаться добавления новой строки в таблицу
        Hooks.wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@class= 'table']/tbody"), dataTable.get("Наименование")));
    }
    public String getTextRow(int n){

        String end_string = table.get(n).getText();
        return end_string;
    }

    public int getNumberRow(){
        int n= tablRow.size();
        return n;
    }
}
