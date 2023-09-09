package steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import java.util.List;
import java.util.Map;

import static steps.Hooks.pageFood;


public class MyStep {
    private int n0, n1;


    @И("Кликнуть на кнопку  \"Добавить\"")
    public void кликнуть_на_кнопку_добавление() {
        pageFood.clickAddBtn();
    }

    @И("Проверить наличие в форме элементов: \"Наименование\",\"Тип\", \"Экзотический\"")
    public void проверить_наличие_в_форме_элементов() {

        String productAddingFormText = pageFood.getTextproductAddingForm();
        Assertions.assertTrue(productAddingFormText.contains("Наименование"), "нет поля - Наименование");
        Assertions.assertTrue(productAddingFormText.contains("Тип"), "нет поля - Тип");
        Assertions.assertTrue(productAddingFormText.contains("Экзотический"), "нет поля - Экзотический");

    }

    @И("Кликнуть на поле \"Наименование\"")
    public void кликнуть_на_поле_наименование() {
        pageFood.clickAddName();
    }
    @И("Заполнение поля \"Наименование\" данными - {string}")
    public void Заполнение_поля_Наименование(String string) {
        pageFood.clearAddName();
        pageFood.inputAddName(string);
        pageFood.inputAddName(Keys.TAB);
    }
    @И("Зафиксировать изменение значение поля скрином - {string}")
    public void saveScreenshot(String name) {
        pageFood.saveScreenshotAddingForm(name);
    }
    @И("Проверить, что выпадающий список \"Тип\" содержит значения:")
    public void поле_тип_содержит(List<String> dataTable) {
        String addTypeListText = pageFood.getTextSelectForm();
        for (String type : dataTable) {
            Assertions.assertTrue(addTypeListText.contains(type), "нет поля - "+type);
        }
    }
    @И("Выбрать из выпадающего списка (Фрукт|Овощ) .$")
    public void выбрать_из_выпадающего_списка(String type) {
        if (type.contains("Фрукт")) {
            pageFood.selecType("Фрукт");
        } else pageFood.selecType("Овощ");
    }
    @И("Проверить кликабельность чекбокса")
    public void проверить_кликательность_чекбокса() {
        pageFood.checkCheckbox();
    }

    @И("Закрыть форму Добавления товара")
    public void закрыть_форму_Добавления_товара() {
        pageFood.clickSaveBtn();
    }
    @И("Проверить наличие на странице столбцов :")
    public void проверка_названий_столбцов(List<String> dataTable) {
        String tableRowText = pageFood.getTextTableRow();
        for (String type : dataTable) {
            Assertions.assertTrue((tableRowText.contains(type)), "строка не содержит "+type);
        }
    }
    @И("Зафиксировать информацию с количеством строк в табличной части")
    public void зафиксировать_количество_строк() {
        n0 = pageFood.getNumberRow();
    }
    @И("Добавить товар через форму:")
    public void добавить_товар(Map<String, String> dataTable) {
        pageFood.addFood(dataTable);
    }
    @И("Проверить изменение количества строк")
    public void провекрить_количество_строк() {
        n1 = pageFood.getNumberRow();
        Assertions.assertTrue((n1 - n0) == 1, "количество строк в таблице не изменилось на 1");
    }
    @И("Проверит, что добавленная строка содержит:")
    public void проверить_добавленный_товар(List<String> dataTable) {
        String end_string= pageFood.getTextRow(n1);
        for (String type : dataTable) {
            Assertions.assertTrue((end_string.contains(type)), "строка не содержит "+type);
        }
    }



}