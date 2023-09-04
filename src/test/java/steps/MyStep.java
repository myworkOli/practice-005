package steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static steps.Hooks.dataSource;

public class MyStep {

    private int n0, n1;
    int rowsStart, rowsEnd;


    @И("Кликнуть на кнопку  \"Добавить\"")
    public void кликнуть_на_кнопку_добавление() {
        WebElement addBtn = Hooks.driver.findElement(By.xpath("//button[text()=\"Добавить\"]"));
        addBtn.click();

    }

    @И("Проверить наличие в форме элементов: \"Наименование\",\"Тип\", \"Экзотический\"")
    public void проверить_наличие_в_форме_элементов() {

        WebElement productAddingForm = Hooks.driver.findElement(By.xpath("//*[@class=\"modal-content\"]"));
        Hooks.wait.until(elementToBeClickable(productAddingForm));
        String productAddingFormText = productAddingForm.getText();
        Assertions.assertTrue(productAddingFormText.contains("Наименование"), "нет поля - Наименование");
        Assertions.assertTrue(productAddingFormText.contains("Тип"), "нет поля - Тип");
        Assertions.assertTrue(productAddingFormText.contains("Экзотический"), "нет поля - Экзотический");

    }

    @И("Кликнуть на поле \"Наименование\"")
    public void кликнуть_на_поле_наименование() {
        WebElement addName = Hooks.driver.findElement(By.xpath("//*[@id=\"name\"]"));
        addName.click();

    }

    @И("Заполнение поля \"Наименование\" данными - {string}")
    public void Заполнение_поля_Наименование(String string) {
        WebElement addName = Hooks.driver.findElement(By.xpath("//*[@id=\"name\"]"));
        addName.clear();
        addName.sendKeys(string);
        addName.sendKeys(Keys.TAB);

    }

    @И("Зафиксировать изменение значение поля скрином - {string}")
    public void saveScreenshot(String name) {
        WebElement productAddingForm = Hooks.driver.findElement(By.xpath("//*[@class=\"modal-content\"]"));

        File scr = productAddingForm.getScreenshotAs(OutputType.FILE);

        try {
            FileHandler.copy(scr, new File("src\\test\\screenshot\\" + name + ".png"));
        } catch (IOException e) {
            throw new RuntimeException("screenshot не создан");
        }
    }

    @И("Проверить, что выпадающий список \"Тип\" содержит значения:")
    public void поле_тип_содержит(List<String> dataTable) {
        WebElement addTypeList = Hooks.driver.findElement(By.xpath("//select[@id=\"type\"]"));
        String addTypeListText = addTypeList.getText();

        for (String type : dataTable) {


            Assertions.assertTrue(addTypeListText.contains(type), "нет поля - "+type);

        }

    }


    @И("Выбрать из выпадающего списка (Фрукт|Овощ) .$")
    public void выбрать_из_выпадающего_списка(String type) {
        Select addType = new Select(Hooks.driver.findElement(By.xpath("//select[@id=\"type\"]")));

        if (type.contains("Фрукт")) {
            addType.selectByVisibleText("Фрукт");
        } else addType.selectByVisibleText("Овощ");

    }

    @И("Проверить кликабельность чекбокса")
    public void проверить_кликательность_чекбокса() {
        WebElement addTypeExotic = Hooks.driver.findElement(By.xpath("//*[@id=\"exotic\"]"));
        addTypeExotic.click();
        Assertions.assertTrue(addTypeExotic.isSelected(), "чекбокс не сменил статус на вкл.");
        addTypeExotic.click();
        Assertions.assertFalse(addTypeExotic.isSelected(), "чекбокс не сменил статус на выкл.");

    }

    @И("Закрыть форму Добавления товара")
    public void закрыть_форму_Добавления_товара() {
        WebElement saveBtn = Hooks.driver.findElement(By.xpath("//button[@id=\"save\"]"));
        WebElement productAddingForm = Hooks.driver.findElement(By.xpath("//*[@class=\"modal-content\"]"));

        saveBtn.click();

    }


    @И("Проверить наличие на странице столбцов :")
    public void проверка_названий_столбцов(List<String> dataTable) {
        WebElement tableRow = Hooks.driver.findElement(By.xpath("//*[@class= 'table']/thead/tr"));
        String tableRowText = tableRow.getText();

        for (String type : dataTable) {
            Assertions.assertTrue((tableRowText.contains(type)), "строка не содержит "+type);

        }
        

    }

    @И("Зафиксировать информацию с количеством строк в табличной части")
    public void зафиксировать_количество_строк() {

        List<WebElement> tablRow0 = Hooks.driver.findElements(By.xpath("//*[@class= 'table']/tbody/tr"));
        n0 = tablRow0.size();

    }

    @И("Добавить товар через форму:")
    public void добавить_товар(Map<String, String> dataTable) {
        WebElement addBtn = Hooks.driver.findElement(By.xpath("//button[text()=\"Добавить\"]"));
        Select addType = new Select(Hooks.driver.findElement(By.xpath("//select[@id=\"type\"]")));
        WebElement saveBtn = Hooks.driver.findElement(By.xpath("//button[@id=\"save\"]"));
        WebElement addTypeExotic = Hooks.driver.findElement(By.xpath("//*[@id=\"exotic\"]"));

        addBtn.click();
        WebElement addName = Hooks.driver.findElement(By.xpath("//*[@id=\"name\"]"));
        addName.sendKeys(dataTable.get("Наименование"));


        if (dataTable.get("Тип").contains("FRUIT")) {
            addType.selectByVisibleText("Фрукт");
        } else if (dataTable.get("Тип").contains("VEGETABLE")) {
            addType.selectByVisibleText("Овощ");
        } else {
            addType.selectByVisibleText(dataTable.get("Тип"));
        }
        System.out.println(dataTable.get("Экзотический").contains("false")+"dataTable.get(\"Экзотический\").contains(\"false\")");
        System.out.println((addTypeExotic.isSelected())+"(addTypeExotic.isSelected())");
        if (dataTable.get("Экзотический").contains("true"))  {
            if (!addTypeExotic.isSelected()){
                addTypeExotic.click();}

        } else {if (addTypeExotic.isSelected()){
            addTypeExotic.click();}
        }


        saveBtn.click();

        //дождаться добавления новой строки в таблицу
        Hooks.wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@class= 'table']/tbody"), dataTable.get("Наименование")));

    }

    @И("Проверить изменение количества строк")
    public void провекрить_количество_строк() {

        List<WebElement> tablRow1 = Hooks.driver.findElements(By.xpath("//*[@class= 'table']/tbody/tr"));
        n1 = tablRow1.size();

        Assertions.assertTrue((n1 - n0) == 1, "количество строк в таблице не изменилось на 1");

    }

    @И("Проверит, что добавленная строка содержит:")
    public void проверить_добавленный_товар(List<String> dataTable) {

        List<WebElement> table = Hooks.driver.findElements(By.tagName("tr"));
        String end_string = table.get(n1).getText();
        for (String type : dataTable) {
            Assertions.assertTrue((end_string.contains(type)), "строка не содержит "+type);

        }

    }


    @И("Проверить наличие  в таблице столбцов соответствующих полям \"Наименование\", \"Тип\", \"Экзотический\"")
    public void проверкаНаличияПолей() {
        List<String> colomsName = Metod.getColomsName();
        List<String> colomsName0 = new ArrayList<String>();
        colomsName0.add("FOOD_NAME");
        colomsName0.add("FOOD_TYPE");
        colomsName0.add("FOOD_EXOTIC");
        Assertions.assertTrue(colomsName.containsAll(colomsName0));

    }

    @И("Зафиксировать максимальное значение FOOD_ID")
    public void saveMaxFOOD_ID() {
        List<Metod.Food> foodMaxId = Metod.getFoodMaxId();
        rowsStart = foodMaxId.get(0).foodId();
    }

    @И("Проверка отсутствие записи в таблице FOOD с характеристиками:")
    public void checkProperties(List<String> dataTable) throws SQLException {

        List<Metod.Food> foodTest = Metod.getFoodTest(dataTable);
        int foodTestSize = foodTest.size();
        Assertions.assertTrue(foodTestSize == 0, "Товара который мы планируем добавть уже есть в таблице, необходимо его удалить или изменить характеристики товрат");


    }

    @И("Проверить добавление данных в БД")
    public void проверить_добавление_данных_в_БД(List<String> dataTable) throws SQLException {
        List<Metod.Food> foodTestRes = Metod.getFoodTest(dataTable);
        rowsEnd = foodTestRes.get(0).foodId();
        Assertions.assertTrue(rowsEnd - rowsStart > 0, "id добавленного не изменилось 1 - товар не добавлен");
        System.out.println(dataTable.get(0) + "foodTestRes.get(0).foodName()     " + foodTestRes.get(0).foodName());

        Assertions.assertAll("проверка полей добавленного товара",
                () -> foodTestRes.get(0).foodName().equals(dataTable.get(0)),
                () -> foodTestRes.get(0).foodType().equals(dataTable.get(1)),
                () -> foodTestRes.get(0).foodExotic().equals(dataTable.get(2)));

    }

    @И("Удалить товар:")
    public void deletFood(Map<String, String> dataTable) {
        System.out.println("Наименование");
        System.out.println("Тип");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        String sql = "DELETE FROM FOOD WHERE FOOD_NAME= ?  ";
        jdbcTemplateObject.update(sql, dataTable.get("Наименование"));

    }


}