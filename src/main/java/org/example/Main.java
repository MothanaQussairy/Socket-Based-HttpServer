package org.example;

import controller.RequestController;
import view.HttpResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    private static RequestController requestController = new RequestController();
    public static void main(String[] args) throws IOException, SQLException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server listening on port " + 8080);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                RequestController RequestController;
                requestController.handleRequest(clientSocket);  // Delegate request to the controller
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
