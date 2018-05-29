/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humaninterface.impl.remote;

import dk.tobiasgrundtvig.util.socket.ConnectionHandler;
import dk.tobiasgrundtvig.util.socket.SocketConnection;
import dk.tobiasgrundtvig.util.socket.impl.Server;
import humaninterface.remote.HumanInterfaceConnectionImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azurwular
 */
public class HumanInterfaceServer implements ConnectionHandler {

    private HumanInterfaceCallSide HumanInterfaceCallSide;

    public static void main(String[] args) {
        Server server = new Server(new HumanInterfaceServer(), 3456);
        server.run();
    }

    public HumanInterfaceServer() {
        HumanInterfaceCallSide = null;
    }

    @Override
    public void handleConnection(SocketConnection conn) {
        String email = "";
        int num;
        String add;
        String pass;
        if (HumanInterfaceCallSide == null) {
            System.out.println("Handling a new connection");
            HumanInterfaceCallSide = new HumanInterfaceCallSide(new HumanInterfaceConnectionImpl(conn));
            email = HumanInterfaceCallSide.askForEmail("What is your email?: ");
            num = HumanInterfaceCallSide.askForInteger("give me an int: ");
            add = HumanInterfaceCallSide.askForString("What is your address: ");
            pass = HumanInterfaceCallSide.askForPassword("Password: ");
            HumanInterfaceCallSide.sendMessage("What we know about you, email: " + email + " num: " + num + " address: " + " password: " + pass + "\n");
            try {
                HumanInterfaceCallSide.close();
            } catch (IOException ex) {
                Logger.getLogger(HumanInterfaceServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
