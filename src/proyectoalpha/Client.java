package proyectoalpha;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Client extends GUI {

    public static Client client = new Client();
    public static String user;

    public Client() {
        super();
    }

    // drawMole function divides the coords string and refresh the windows with
    // the new mole.
    public static void drawMole(String position) {
        position = position.trim();
        String coords[] = position.split(",");
        Integer row = Integer.parseInt(coords[0]);
        Integer column = Integer.parseInt(coords[1]);
        
        client.putMole(row, column);
        client.refresh();
    }

    public static void main(String args[]) throws IOException {
        MulticastSocket multicastSocket = null;
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        int portMulticast = 50600;
        byte[] message;
        byte[] mole;

        System.out.println("Write your username:");
        String userName = scanner.next();
        userName = userName.toUpperCase();

        searchingGame(userName);
        jLabel1.setText(userName);
        user = userName;

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                client.setVisible(true);

            }
        });

        try {
            InetAddress group = InetAddress.getByName("228.5.6.10");
            multicastSocket = new MulticastSocket(portMulticast);
            multicastSocket.joinGroup(group);

            while (true) {
                message = new byte[1000];
                DatagramPacket messageIn = new DatagramPacket(message, message.length);

                try {
                    multicastSocket.receive(messageIn);
                    mole = (new String(messageIn.getData())).getBytes();
                    
                    if (new String(mole).contains("Finished: ")) {
                        System.out.println("Winner is: " + new String(mole).replace("Finished: ", ""));
                        JOptionPane.showMessageDialog(null, "Winner is: " + new String(mole).replace("Finished: ", "").trim());
                        JOptionPane.showMessageDialog(null, "¿NEW GAME?");
                    }
                    else {
                        String position = new String(mole);
                        drawMole(position);
                    }
                } catch (IOException e) {
                    System.out.println("IO Exception: " + e.getMessage());
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } finally {
            if (multicastSocket != null) {
                multicastSocket.close();
            }
        }
    }

    private static void searchingGame(String userName) {
        Socket socketTCP = null;
        int serverPort = 50501;

        try {
            socketTCP = new Socket("localhost", serverPort);

            DataOutputStream out = new DataOutputStream(socketTCP.getOutputStream());
            out.writeUTF(userName);

            if (socketTCP != null) {
                socketTCP.close();
            }

            JOptionPane.showMessageDialog(null, "Successfull Connection.");
            System.out.println("Successfull Connection.");
        } catch (Exception e) {
            System.out.println("Failed Connection.");
            System.exit(0);
        }
    }

    @Override
    protected void onMonsterClick() {
        Socket socketTCP = null;
        int serverPort = 50500;

        try {
            socketTCP = new Socket("localhost", serverPort);

            DataOutputStream out = new DataOutputStream(socketTCP.getOutputStream());
            out.writeUTF(user);

            if (socketTCP != null) {
                socketTCP.close();
            }

            System.out.println("You hit the mole.");
        } catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
