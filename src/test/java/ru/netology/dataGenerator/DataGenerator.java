package ru.netology.dataGenerator;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import lombok.Value;

public class DataGenerator {

    static Faker faker = new Faker(new java.util.Locale("ru"));

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        var cities = new String[]{"Горно-Алтайск","УфаУлан-Удэ","Махачкала","Магас","Нальчик","Петразоводск","Сыктывкар",
                "Йошкар-Ола","Саранск","Якутск","Владикавказ","Казань","Кызыл","Ижевск","Абакан","Грозный","Чебоксары",
                "Барнаул","Чита","Петропавловск-Камчатский","Краснодар","Красноярск","Ставрополь","Пермь","Хабаровск",
                "Благовещенск","Архангельск","Астрахань","Белгород","Брянск","Владимир","Волгоград","Вологда","Воронеж",
                "Иваново","Иркутск","Калининград","Калуга","Кемерово","Киров","Кострома","Курган","Курск","Санкт-Петербург",
                "Липецк","Магадан","Москва","Мурманск","Нижний Новгород","Великий Новгород","Новосибирск","Оренбург","Орел",
                "Пенза","Псков","Ростов-на-Дону","Рязань","Саратов","Южно-Сахалинск","Самара","Смоленск","Тамбов","Екатеринбург",
                "Тверь","Томск","Тула","Тюмень","Ульяновск","Челябинск","Ярославль","Нарьян-Мар","Ханты-Мансийск","Анадырь","Салехард","Биробиджан" };
        return cities [new Random().nextInt(cities.length)];
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}

