package com.example.demo.ConsoleViews;

import com.example.demo.ConsoleControllers.AdminController;
import com.example.demo.Interfaces.AdminCommands;


import java.io.IOException;
import java.util.Scanner;

public class AdminView {
    Scanner scanner = new Scanner(System.in);
    public AdminController adminController;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void openAdminCommunicationMenu() throws IOException {
        System.out.println("You re logged in as an " + adminController.getAdmin().getUserType());
        System.out.println("Do you want to delete or recover account(Recover/Delete)");
        String choice = scanner.nextLine();
        switch (choice) {
            case "Recover" -> {
                recoverUserAccountDialog();
            }
            case "Delete" -> {
                deleteUserAccountDialog();
            }
        }
    }

    public void recoverUserAccountDialog() throws IOException {
        System.out.println("What account you want to recover");
        String accountName = scanner.nextLine();
        String password = scanner.nextLine();
        if (!adminController.recoverUserAccount(accountName, password)) {
            System.out.println("No user with that name found. Do you want to try again?");
            String choice = scanner.nextLine();
            if (choice.equals("Y")) {
                recoverUserAccountDialog();
            }
        } else {
            System.out.println("Success");
        }
    }

    public void deleteUserAccountDialog() throws IOException {
        System.out.println("Enter account username you want to remove");
        String accountName = scanner.nextLine();
        String password = scanner.nextLine();
        if (!adminController.deleteUserAccount(accountName, password)) {
            System.out.println("No user with that name found. Do you want to try again?");
            String choice = scanner.nextLine();
            if (choice.equals("Y")) {
                deleteUserAccountDialog();
            }
        } else {
            System.out.println("Success");
        }
    }


}
