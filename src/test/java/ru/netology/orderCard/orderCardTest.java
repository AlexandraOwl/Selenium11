package ru.netology.orderCard;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.trim;

public class orderCardTest {
    private WebDriver driver;

    @BeforeAll
      static void setUpAll() {
    WebDriverManager.chromedriver().setup();
        }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSomething() {
        driver.get("http://localhost:9999");
    }
    @Test
    void shouldFillForm() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Николайчук Александра");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79880116795");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFillNameWrong() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Ghrtert");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79880116795");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        if (driver.findElement(By.cssSelector("[data-test-id='name']")).getAttribute("class").contains("input_invalid")) {
            String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText().trim();
            assertEquals(expected, actual);
        } else {
            fail("Отсутствует необходимый для теста аттрибут.");
        }
    }  //Не знала как сделать селектор, который содержал бы data-test-id и классы input_invalid и input__sub одновременно

    @Test
    void shouldNotFillName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79880116795");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Поле обязательно для заполнения";
        if (driver.findElement(By.cssSelector("[data-test-id='name']")).getAttribute("class").contains("input_invalid")) {
            String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText().trim();
            assertEquals(expected, actual);
        } else {
            fail("Отсутствует необходимый для теста аттрибут.");
        }
    }

    @Test
    void shouldFillPhoneWrong() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Николайчук Александра");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7988011679");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        if (driver.findElement(By.cssSelector("[data-test-id='phone']")).getAttribute("class").contains("input_invalid")) {
            String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();
            assertEquals(expected, actual);
        } else {
            fail("Отсутствует необходимый для теста аттрибут.");
        }
    }

    @Test
    void shouldNotFillPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Николайчук Александра");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Поле обязательно для заполнения";
        if (driver.findElement(By.cssSelector("[data-test-id='phone']")).getAttribute("class").contains("input_invalid")) {
            String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();
            assertEquals(expected, actual);
        } else {
            fail("Отсутствует необходимый для теста аттрибут.");
        }
    }

    @Test
    void shouldNotCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Николайчук Александра");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79880116795");
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        if (driver.findElement(By.cssSelector("[data-test-id='agreement']")).getAttribute("class").contains("input_invalid")) {
            String actual = driver.findElement(By.cssSelector("[data-test-id='agreement']")).getText().trim();
            assertEquals(expected, actual);
        } else {
            fail("Отсутствует необходимый для теста аттрибут.");
        }
    }

}
