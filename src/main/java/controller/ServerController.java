package controller;

import models.Server;
import view.ServerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerController {
    private final ServerView serverView;
    public ServerController() {
        this.serverView = new ServerView(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initServer(serverView.getPort());
            }
        });
    }
    public void initServer(int port) {
        try {
            new Thread(() -> {
                new Server(port);
            });
        }catch (Exception ex) {
            System.err.println("Error al iniciar el servidor: " + ex.getMessage());
        }
    }
}
