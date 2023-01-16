package org.example;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.example.PagesEnum.MAIN_PAGE;

public class Steps {

    WebDriver driver;

    private String popCourses = "//div[@class='container container-lessons']//div[@class='lessons__new-item-container']/div[contains(@class,'lessons__new-item-title_with-tags')]/parent::div";
    private String specCourses = "//div[@class='container container-lessons']//div[contains(@class,'lessons__new-item-bottom_spec')]/parent::div";
    private String recomendationCourses = "//div[@class='container container-lessons']//div[@class='lessons__new-item-container']/div[contains(@class,'lessons__new-item-title  lessons__new-item-title_with-bg')]/parent::div";
    private String allCourses = "//div[@class='container container-lessons']//a[contains(@class,'lessons__new-item')]/div";
    private String dateCourse = ".//div[@class='lessons__new-item-start'] | ./div[contains(@class,'lessons__new-item-bottom_spec')]/div[@class='lessons__new-item-time']";

    public void dELETEsetDriver(WebDriver driver){
        this.driver = driver;
    }

    public void clickButton() {
        getButton().click();
    }

    public SelenideElement getButton() {
        return $x("");
    }

    public void lightingElement(SelenideElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAtribute('style','border: 6px solid red;');", element);
        Selenide.executeJavaScript("arguments[0].setAtribute('style','border: 6px solid red;');", element);
    }

    public void unLightingElement(SelenideElement element) {
//        Selenide.executeJavaScript("arguments[0].setAtribute('style','');", element);
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAtribute('style','');", element);
    }

    public SelenideElement getCourseForQueue(String coursesName, String queue) {
        ElementsCollection courses = getCoursesList(coursesName);
        List<LocalDate> dates = getDatesCourses(courses);
        LocalDate min = null;
        if (queue.equals("ранний")) {
            min = Collections.min(dates);
        } else if (queue.equals("поздний")) {
            min = Collections.max(dates);
        }
        int index;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i).equals(min)) {
                list.add(i);
            }
        }
        if (list.size() > 0) {
            index = (int) (Math.random() * (list.size() + 1));
        } else {
            index = list.get(0);
        }
        return courses.get(index);
    }

    private List<LocalDate> getDatesCourses(ElementsCollection courses) {
        List<LocalDate> list = new ArrayList<>();
        for (SelenideElement c : courses) {
            String preText = c.$x(dateCourse).getOwnText()
                    .trim();
            String[] texts = preText.split("\n", 0);
            String text = Arrays.stream(texts)
                    .filter(t -> !t.equals(""))
                    .findFirst()
                    .get();
            list.add(dateFormatter(text));
        }
        return list;
    }

    private LocalDate dateFormatter(String string) {
        if (string.matches("С \\d{2}.+")) {
            String[] datePeaces = string
                    .replaceAll("С ", "")
                    .split("\\s");
            return dateBuilder(datePeaces[0], datePeaces[1], null);
        } else if (string.matches("\\d{2}.+")) {
            String[] datePeaces = string
                    .split("\\s");
            return dateBuilder(datePeaces[0], datePeaces[1], null);
        } else if (string.matches("В.+\\d{4} года")) {
            String[] datePeaces = string
                    .replaceAll(" года", "")
                    .replaceAll("В ", "")
                    .split("\\s");
            return dateBuilder(null, datePeaces[0], datePeaces[1]);
        }
        return null;
    }

    private LocalDate dateBuilder(String day, String month, String year) {
        LocalDate now = LocalDate.now();
        if (day == null) {
            day = "01";
        }
        if (month == null) {
            month = now.format(DateTimeFormatter.ofPattern("MM"));
        } else {
            try {
                month = Months.getMonthNumber(month);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (year == null) {
            year = String.valueOf(now.getYear());
        }
        LocalDate date = LocalDate.parse(day + month + year, DateTimeFormatter.ofPattern("ddMMyyyy"));
        return date;
    }

    private ElementsCollection getCoursesList(String coursesName) {
        openPage();
        switch (coursesName) {
            case "Популярные курсы":
                $$x(popCourses).forEach(e -> e.shouldBe(visible));
                return $$x(popCourses);
            case "Специализации":
                $$x(specCourses).forEach(e -> e.shouldBe(visible));
                return $$x(specCourses);
            case "Рекомендации для вас":
                $$x(recomendationCourses).forEach(e -> e.shouldBe(visible));
                return $$x(recomendationCourses);
            default:
                $$x(allCourses).forEach(e -> e.shouldBe(visible));
                return $$x(allCourses);
        }
    }

    private void openPage() {
        Selenide.open(MAIN_PAGE.getValue());
    }

}
