package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Инициализация JSON-парсера
        Gson gson = new Gson();
        List<User> users;

        // Чтение JSON-файла
        try (Reader reader = new FileReader("books.json")) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения JSON-файла", e);
        }

        // Задание 1: Печать всех пользователей
        System.out.println("Список пользователей:");
        users.forEach(System.out::println);

        // Задание 2: Подсчет уникальных книг
        Set<Book> uniqueBooks = users.stream()
                .filter(user -> user.getFavoriteBooks() != null) // Проверка на наличие списка книг
                .flatMap(user -> user.getFavoriteBooks().stream())
                .collect(Collectors.toSet());

        System.out.println("\nКоличество уникальных книг: " + uniqueBooks.size());
        System.out.println("Список уникальных книг:");
        uniqueBooks.forEach(System.out::println);

        // Задание 3: Сортировка книг по году издания
        List<Book> sortedBooks = users.stream()
                .flatMap(user -> user.getFavoriteBooks().stream())
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .collect(Collectors.toList());

        System.out.println("\nКниги, отсортированные по году издания:");
        sortedBooks.forEach(book -> System.out.println(book.getName() + ": " + book.getPublishingYear()));
    }
}

@Getter
class User {
    private String name;
    private String surname;
    private String phone;
    private Boolean subscribed;
    private List<Book> favoriteBooks;

    public User() {}

    public User(String name, String surname, String phone, Boolean subscribed) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + phone + " " + subscribed + " " + favoriteBooks;
    }
}

@Getter
class Book {
    private String name;
    private String author;
    private Integer publishingYear;
    private String isbn;
    private String publisher;

    public Book() {}

    public Book(String name, String author, Integer publishingYear, String isbn, String publisher) {
        this.name = name;
        this.author = author;
        this.publishingYear = publishingYear;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return name + " " + author + " " + publishingYear + " " + isbn + " " + publisher;
    }
}
