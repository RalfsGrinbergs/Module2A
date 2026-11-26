package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ConsoleMenu {
    private static final List<MenuOption> options = List.of(
            new MenuOption(1, "Add animal"),
            new MenuOption(2, "List all animals"),
            new MenuOption(3, "find animals by species"),
            new MenuOption(4, "List available animals"),
            new MenuOption(5, "mark animal as adopted"),
            new MenuOption(0, "Exit")
    );
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start(){
        boolean active = true;
        while (active) {
            try {
        printMenu();
        System.out.println("Choose what you want to do : ");
        int number = scanner.nextInt();
        switch (number) {
            case 0 -> active = false;
            case 1 ->
                addAnimal();
            case 2 ->
                getAllAnimals();
            case 3 ->
                findBySpecies();
            case 4 ->
                findAvailableAnimals();
            case 5 ->
               markAsAdopted();
            default ->
                System.out.println("Input must be a number from 0 to 5");
                }
            }catch(InputMismatchException e) {
                System.out.println("Please enter the number and try again");
                scanner.nextLine();
        }
        }
        }

        private void printMenu () {
            for (MenuOption option : options) {
                System.out.println(option.number() + " " + option.label());
            }
        }

    private void addAnimal() {
        while (true) {
            System.out.println("""
                    Choose animal type:
                    1. Dog
                    2. Cat
                    3. Bird
                    0. Exit""");
            int choose = scanner.nextInt();
            if(choose == 0) {
                return;
            }
            if (choose < 0 || choose > 3) {
                System.out.println("The number must be from 0 to 3");
                continue;
            }
                System.out.println("Enter animal name: ");
                String name = scanner.next();
                System.out.println("Enter animal age: ");
                int age = scanner.nextInt();
                switch (choose) {
                    case 1 ->
                        shelter.addAnimal(new Dog(new AnimalId(), name, age));
                    case 2 ->
                        shelter.addAnimal(new Cat(new AnimalId(), name, age));
                    case 3 ->
                        shelter.addAnimal(new Bird(new AnimalId(), name, age));
                }

        }
    }

    private void getAllAnimals() {
        List<Animal> animals = shelter.getAllAnimals();
        System.out.println(animals.stream()
                .map(Animal::toString)
                .collect(Collectors.joining(" , ")));
    }

    private void findBySpecies() {
        System.out.println("Select a species: ");
        String species = scanner.next();
        List<Animal> listedAnimals = shelter.findBySpecies(species);
        System.out.println(listedAnimals.stream()
                .map(Animal::toString)
                .collect(Collectors.joining(" , ")));
    }

    private void findAvailableAnimals() {
        List<Animal> availableAnimals = shelter.findAvailableAnimals();
        System.out.println(availableAnimals.stream()
                .map(Animal::toString)
                .collect(Collectors.joining(" , ")));
    }

    private void markAsAdopted() {
        System.out.println("Write an animal id: ");
        String id = scanner.next();
        shelter.markAsAdopted(id);
    }
    }

