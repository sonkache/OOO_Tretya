# Lopushok Desktop

A JavaFX application for managing products and materials.
It consists of two modules:

* **app** – the main JavaFX application
* **WSUniversalLib** – a calculation library

## Getting Started

These steps will help you build and run the project locally.

### Prerequisites

What you need to install:

```
Java 17  (java -version)
Maven 4+ (mvn -v)
MySQL 8+ (mysql --version)
```

### Installation

1. Clone the project

```
git clone https://github.com/sonkache/OOO_Tretya.git
cd OOO_Tretya/Сессия_2/Код
```

2. Configure the database connection
   Edit the file:

```
app/src/main/resources/db.properties
```

and specify your own credentials:

```
url=jdbc:mysql://localhost:3306/lopushok?useSSL=false&serverTimezone=UTC
user=root
password=1234
```

3. Test the database connection

```
mvn -pl app exec:java -Dexec.mainClass="pro.lopushok.PingDb"
```

If you see a line like `OK, products: n`, everything is working correctly.

4. Run the application

```
mvn -pl app javafx:run
```

After launching, a JavaFX window with the product management interface will appear.

## Authors

* Baranov Vyacheslav Ivanovich 241-362
* Savvin Vladislav Alexandrovich 241-362
* Chernikova Sofya Mikhailovna 241-362
