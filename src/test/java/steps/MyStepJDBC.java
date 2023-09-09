package steps;

import io.cucumber.java.ru.И;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MyStepJDBC {

    int rowsStart, rowsEnd;
    public static DataSource dataSource = getH2DataSource();

    public static DataSource getH2DataSource() {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(ConfProperties.getProperty("db.host"));
        dataSource.setUser(ConfProperties.getProperty("db.login"));
        dataSource.setPassword(ConfProperties.getProperty("db.password"));
        return dataSource;
    }
    @И("Проверить наличие в таблице столбцов соответствующих полям \"Наименование\", \"Тип\", \"Экзотический\"")
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
    @И("Добавить товар:")
    public void addFood(Map<String, String> dataTable) throws SQLException{
        Metod.addFood(dataTable) ;

    }
    @И("Проверить добавление данных в БД")
    public void проверить_добавление_данных_в_БД(List<String> dataTable) throws SQLException {
        List<Metod.Food> foodTestRes = Metod.getFoodTest(dataTable);
        rowsEnd = foodTestRes.get(0).foodId();
        Assertions.assertTrue(rowsEnd - rowsStart > 0, "id добавленного не изменилось 1 - товар не добавлен");

        Assertions.assertAll("проверка полей добавленного товара",
                () -> foodTestRes.get(0).foodName().equals(dataTable.get(0)),
                () -> foodTestRes.get(0).foodType().equals(dataTable.get(1)),
                () -> foodTestRes.get(0).foodExotic().equals(dataTable.get(2)));
    }
    @И("Удалить товар:")
    public void deletFood(Map<String, String> dataTable) {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        String sql = "DELETE FROM FOOD WHERE FOOD_NAME= ? ";
        jdbcTemplateObject.update(sql, dataTable.get("Наименование"));
    }

}
