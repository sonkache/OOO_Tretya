# Lopushok Desktop

JavaFX-приложение для работы с продуктами и материалами.
Состоит из двух модулей:
* app - само приложение на JavaFX
* WSUniversalLib - библиотека расчётов

## Начало работы

Эти шаги помогут собрать и запустить проект локально.

### Необходимые условия

Что нужно для установки:

```
Java 17  (java -version)
Maven 4+ (mvn -v)
MySQL 8+ (mysql --version)
```

### Установка

1. Клонируем проект

```
git clone https://github.com/sonkache/OOO_Tretya.git
cd OOO_Tretya/Сессия_2/Код
```

2. Настраиваем подключение к базе данных
   Редактируем файл:

```
app/src/main/resources/db.properties
```

и указываем свои данные:

```
url=jdbc:mysql://localhost:3306/lopushok?useSSL=false&serverTimezone=UTC
user=root
password=1234
```

3. Проверяем подключение

```
mvn -pl app exec:java -Dexec.mainClass="pro.lopushok.PingDb"
```

Если видите строку вроде `OK, products: n` всё работает.

4. Запускаем приложение

```
mvn -pl app javafx:run
```

После запуска появится окно JavaFX с интерфейсом управления продуктами.

## Авторы
- Баранов Вячеслав Иванович 241-362
- Саввин Владислав Александрович 241-362
- Черникова Софья Михайловна 241-362