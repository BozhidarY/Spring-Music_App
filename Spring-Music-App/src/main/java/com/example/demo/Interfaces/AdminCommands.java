package com.example.demo.Interfaces;

import java.io.IOException;

public interface AdminCommands {
    boolean deleteUserAccount(String userName, String password) throws IOException;
    boolean recoverUserAccount(String userName, String password) throws IOException;
}
