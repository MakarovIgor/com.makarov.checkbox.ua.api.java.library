# com.makarov.checkbox.ua.api.java.library
### JAVA SDK для работи з Checkbox.ua API

#### Примечание:

> Не всі ще функції реалізовані, але ті що є, повинно вистачити для роботи в онлайн режимі, 
> ще декілька днів буду наповнювати новими функціями, якщо в кого є бажання можете бути контріб'ютором, форкати та робити пул-реквести

> Прошу сильно по коду не ругать, краще залишайте комменти та пропозиції або пишіть на пошту igormakarov1991@gmail.com , де та які принципи я порушив, буду радий  :)

#### Офіційна документація:

<https://dev-api.checkbox.in.ua/api/redoc>

<https://dev-api.checkbox.in.ua/api/docs>

<https://docs.google.com/document/d/1Zhkc4OljKjea_235YafVvZunkWSp6TCAKeckhgl8t2w/edit>

 #### Підключення до проєкту Maven
Додаємо залежність в <dependencies></dependencies>
```xml
<dependency>
  <groupId>com.makarov.checkbox.ua.api</groupId>
  <artifactId>checkbox-ppo</artifactId>
  <version>0.1</version>
</dependency>
```

### Робота з API

#### Налаштування:
Для початку роботи, треба налаштувати та передати конфіг:
> адреса продакшн сервера http://api.checkbox.in.ua
> тестового сервера http://dev-api.checkbox.in.ua

```java
Config config = new Config([
    Config.API_URL => 'https://dev-api.checkbox.in.ua/api/v1',
    Config.LOGIN => 'логін касира',
    Config.PASSWORD => 'пароль касира',
    Config.LICENSE_KEY => 'ключ ліцензії касси'
]);
```

#### Вхід користувача (касира):
Для початку роботи з касою треба залогінити касира

 ```java
CheckboxAPI api = new CheckboxAPI(config)
api.cashierSignIn();
 ```

#### Логаут касира:

 ```java
api.cashierSignOut();
 ```

##### Виняткові ситуаціЇ (Exceptions):
```java
InvalidCredentialsException - не вірні дані логіну або паролю
```
```java
ValidationException - помилка валідаціЇ 
```
```java
NotActiveShiftException - зміна не активна
```
```java
\Exception  - стандартна помилка
```

### Основні методи(Basic methods):

#### Касир

##### Вхід користувача (касира) за допомогою логіна та паролю:

 ```java
CheckboxAPI api = new CheckboxAPI(config)
api.cashierSignIn();
 ```

##### Вихід користувача (касира):

 ```java
api.cashierSignOut();
 ```
 
##### Отримання інформації про поточного користувача (касира):

 ```java
Cashier cashier = api.getCashierProfile(); 
 ```

##### Отримання інформації про активну зміну користувача (касира):

 ```java
Shift activeShift = api.getCashierActiveShift();
 ```


#### пРРО
##### Перевірка зв'язку з ДПС. При наявності зв'язку повертає статус DONE:

 ```java
api.pingTaxService();
 ```

#### Зміни
##### Отримання змін поточного касира:

```java
ArrayList<Shift> shifts = api.getShifts();
 ```
 
##### Відкриття нової зміни касиром:

```java
api.openShift();
 ```

##### Отримання інформації про зміну:

```java
Shift shift = api.getShift(String shiftId);
```

##### Створення Z-Звіту та закриття поточної зміни користувачем:

```java
api.closeShift();
```

#### Чеки
##### Отримання інформації про чек:

```java
SellReceipt sellReceipt = api.getReceipt(String receiptId);
```

##### Створення чеку продажу/повернення:

```java
SellReceipt receipt = new SellReceipt.Builder(

).build();

api.receiptSell(receipt);
```

##### Створення сервісного чеку внесення або винесення коштів:
внесення:
```java
ServiceReceipt serviceReceipt = checkboxAPI.createServiceReceipt(
      new ServiceReceipt(new Payment(PaymentType.CASH, 100))
);
```
винесення - просто сума оплати з мінусовім значенням:
```java
ServiceReceipt serviceReceipt = checkboxAPI.createServiceReceipt(
      new ServiceReceipt(new Payment(PaymentType.CASH, -100))
);
```
