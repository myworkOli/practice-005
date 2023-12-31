# language: ru

@all


Функция: Добавление новых товаров


  @selenium
  Структура сценария: Проверка формы добавления товара
    * Кликнуть на кнопку  "Добавить"
    * Проверить наличие в форме элементов: "Наименование","Тип", "Экзотический"
    * Кликнуть на поле "Наименование"
    * Заполнение поля "Наименование" данными - "Кивано"
    * Зафиксировать изменение значение поля скрином - "4_scr_товар"
    * Заполнение поля "Наименование" данными - "Кивано (Kiwano)  (Новая Зеландия) # 1"
    * Зафиксировать изменение значение поля скрином - "6_scr_товар_изменен"
    * Заполнение поля "Наименование" данными - "Кивано (Kiwano) (Новая Зеландия) # 1☼"
    * Зафиксировать изменение значение поля скрином - "7_scr_товар_изменен_спецсимвол"
    * Проверить, что выпадающий список "Тип" содержит значения:
      | Овощ  |
      | Фрукт |
    * Выбрать из выпадающего списка Овощ .
    * Зафиксировать изменение значение поля скрином - "9_scr_тип_Овощ"
    * Выбрать из выпадающего списка Фрукт .
    * Зафиксировать изменение значение поля скрином - "10_scr_тип_Фрукт"
    * Проверить кликабельность чекбокса
    * Закрыть форму Добавления товара

  @selenium
  Структура сценария:Валидация отображения в списке нового товаров: <name>,<type>
    * Проверить наличие на странице столбцов :
      | Наименование |
      | Тип          |
      | Экзотический |
    * Зафиксировать информацию с количеством строк в табличной части
    * Добавить товар через форму:
      | Наименование | <name> |
      | Тип          | <type> |
      | Экзотический | <exot> |

    * Проверить изменение количества строк
    * Проверит, что добавленная строка содержит:
      | <name> |
      | <type> |
      | <exot> |

    Примеры:
      | name                        | type  | exot  |
      | Кивано # 1 (Новая Зеландия) | Овощ  | true  |
      | Lemon                       | Фрукт | false |










