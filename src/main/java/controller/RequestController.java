package controller;

import model.User;
import model.UserModel;
import view.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class RequestController {
    private UserModel userModel = new UserModel();
    private HttpResponse response = new HttpResponse("");
    private User userDataExtractor(BufferedReader input) throws IOException {
        while(!input.readLine().isEmpty());
        String userName = input.readLine();
        String phone = input.readLine();
        User user = new User(userName,phone);
        return user;
    }

    private String generateResponseBodyForAllUsers() throws SQLException {
        List<User> users = userModel.getAllUsers();
        if(users.size() == 0) {
            return "No Avaliable Users";
        }
        StringJoiner res = new StringJoiner(",\n", "[\n", "\n]");

        for (User user : users) {
            res.add(user.toJson());
        }

        return res.toString();
    }



    public void handleRequest(Socket clientSocket) throws IOException, SQLException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(out);
        String firstLine = in.readLine();
        if(firstLine == null) return;
        if(firstLine.contains("GET") && !firstLine.contains("Id")) {
            response = new HttpResponse(generateResponseBodyForAllUsers());
            writer.write(response.generateResponse());
            writer.flush();

        }else if(firstLine.contains("GET") && firstLine.contains("Id")) {
            int id = Integer.parseInt(firstLine.substring((firstLine.indexOf("=")+1),firstLine.indexOf("HTTP")-1));

            response = new HttpResponse(userModel.getUserById(id).toJson());
            writer.write(response.generateResponse());
            writer.flush();
            clientSocket.close();
        }else if(firstLine.contains("PUT") && !firstLine.contains("Id")) {
            User user = userDataExtractor(in);
            userModel.insertUser(user);
            response.generateResponse();
        }
        else {
            int id = Integer.parseInt(firstLine.substring((firstLine.indexOf("=")+1),firstLine.indexOf("HTTP")-1));
            userModel.deleteUser(id);
            writer.write(response.generateResponse());
            writer.flush();
        }
        clientSocket.close();

    }
}
