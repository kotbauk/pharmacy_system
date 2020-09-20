package gui;


import transation.TransactionUtils;
import model.Role;
import model.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class GuiManager {
    private static User user;

    private static Queue<GuiManager> guiManagers = new LinkedList<>();
    private final Page page;

    public GuiManager(Page page) {
        this.page = page;
    }

    public void showPage(){
        if (!guiManagers.isEmpty()){
            guiManagers.poll().hidePage();
        }

        guiManagers.add(this);
        page.setVisible(true);
    }

    public void hidePage(){
        page.setVisible(false);
        page.dispose();
    }

    public static Role getRole(){
        return user.getRole();
    }


    public static boolean signIn(String login, String password) throws SQLException {
        user = TransactionUtils.signIn(login, password);
        return user != null;
    }
}
