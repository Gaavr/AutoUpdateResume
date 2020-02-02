package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private static String login = null;
    private static String password = null;

    public static void main(String[] args) throws InterruptedException {
     //   launch(args);
        System.out.println("Программа предназначена для автоматического поднятия " +
                "Вашего резюме в топ поиска кандидатов сайта HH.ru");
        ChromeDriver driver;
        try {
            System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
            System.out.println("Такс, папка с WebDriver найдена," +
                    "он почти запущен и готов помочь Вам найти новую работу");
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Не удается получить доступ к драйверу, проверьте папку с расположением WebDriver");
        }

        setSettings();

        while (true) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            goToSite(driver);
            TimeUnit.MINUTES.sleep((int) (240 + (Math.random() * 20)));
        }

    }

    public static void setSettings() {
        System.out.println("Для начала введите данные для авторизации.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Введите Ваш логин: ");
            login = reader.readLine();
        }
        catch (Exception e) {
            System.out.println("Логин не может быть пустым");
        }

        try {
            System.out.println("Введите Ваш пароль: ");
            password = reader.readLine();
        }
        catch (Exception e) {
            System.out.println("Пароль не может быть пустым");
        }
    }

    public static void goToSite(ChromeDriver driver) throws InterruptedException {

        driver.get("https://hh.ru/");
        driver.findElementByXPath("/html/body/div[5]/div[2]/div/div[1]/div[1]/div/div[5]/a").click(); // нажать кнопку автозизации
        driver.findElementByXPath("/html/body/div[2]/div/div[2]/div/div/form/" +
                "div[1]/input").sendKeys(login); //ввести логин
        driver.findElementByXPath("/html/body/div[2]/div/div[2]/div" +
                "/div/form/div[3]/input").sendKeys(password); // ввести пароль
        try {
            driver.findElementByXPath("/html/body/div[2]/div/div[2]/div/div/form/div[4]/input").click(); // нажать кнопку войти
        }
        catch (Exception e) {
            System.out.println("Неправильный логин или пароль, ну или вина лежит на " + e);
            System.exit(0);
        }
        driver.findElementByXPath("/html/body/div[4]/div[1]/div/div/div[1]/div[1]/a").click();// открыть мои резюме
        try {
            driver.findElementByXPath("//*[@id=\"HH-React-Root\"]/div/div/div/div[1]/div[3]/div/div[5]/div/div/div/div[1]/span/button").click(); // обновить резюме
            System.out.println("Резюме успешно обновлено, следующее обновление произойдет прмерно через 4 часа");
        }
        catch (Exception e) {
            System.out.println("Невозможно обновить резюме, повтор попытки будет прмерно через 4 часа");
        }
        finally {
            driver.close();
        }
    }

}
