package view;

import models.Server;

import java.awt.event.ActionListener;

public class ServerView {
    private Server server;
    private final ServerFrame frame;

    public ServerView(ActionListener actionListener) {
        frame = new ServerFrame();
        frame.setVisible(true);
        frame.setActionListener(actionListener);
    }

    public int getPort() {
        int port = Integer.parseInt(frame.portTxt.getText());
        if(port >= 49152 && port <= 65535) {
            return port;
        }else {
            return 55555;
        }
    }

    public Object getInitBtn() {
        return frame.getInitBtn();
    }

}
