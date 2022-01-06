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
    Config.LOGIN => 'логин кассира',
    Config.PASSWORD => 'пароль кассира',
    Config.LICENSE_KEY => 'ключ лицензии кассы'
]);
```

#### Логин кассира:


 
