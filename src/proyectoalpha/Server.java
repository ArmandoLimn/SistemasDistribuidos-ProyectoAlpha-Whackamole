package proyectoalpha;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyectoalpha.Server.lock;

public class Server {
    static Lock lock = new ReentrantLock();
    

    public static void main(String[] args) {
        MulticastSocket multicastSocket = null;
        ServerSocketChannel serverSocketChannel;
        SocketChannel socketChannel;

        InitialThread initialThread;
        Connection connection;
        String molePosition;

        byte[] buffer;
        boolean winner = false;
        int winnerIndex = -1;
        int portMulticast = 50600;
        int serverPort = 50500;
        int N = 10;

        try {
            // Join to Multicast Group.
            InetAddress group = InetAddress.getByName("228.5.6.10");
            multicastSocket = new MulticastSocket(portMulticast);
            multicastSocket.joinGroup(group);
            buffer = new byte[1000];
            
            // Instance of TCP Connection.
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(serverPort));
            serverSocketChannel.configureBlocking(false);

            // Starting initial thread.
            initialThread = new InitialThread();
            initialThread.start();

            while(true) {
                byte[] mole = new byte[1000];
                // randomPosition returns random coords to generate a new mole.
                molePosition = randomPosition();
                mole = molePosition.getBytes();

                // Instance of UDP.
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                DatagramPacket messageOut = new DatagramPacket(mole, mole.length, group, portMulticast);
                multicastSocket.send(messageOut);
                multicastSocket.receive(messageIn);

                // Return mole's position.
                // System.out.println("[" + new String(messageOut.getData()) + "]");

                // If the players don't hit the mole, we have to wait 10 seconds to send another mole.
                try {
                    socketChannel = serverSocketChannel.accept();

                    // We want know who is the first player that hit to the mole.
                    lock.lock();
                    Connection.isFirst = true;
                    lock.unlock();

                    while(socketChannel != null) {
                        connection = new Connection(socketChannel.socket());
                        connection.start();
                        socketChannel = serverSocketChannel.accept();
                    }
                    
                    
                   for (int i = 0; i < Connection.score.size(); i++) {
                       System.out.println(Connection.players.get(i) + "'s Score: " + Connection.score.get(i));
                       if (Connection.score.get(i) == N) {
                           winnerIndex = i;
                           winner = true;
                       }
                   }
                                      
                   if (winner) {
                       String send = "Finished: ";
                       send += Connection.players.get(winnerIndex);

                       mole = new byte[1000];
                       mole = send.getBytes();
                       messageIn = new DatagramPacket(buffer, buffer.length);
                       messageOut = new DatagramPacket(mole, mole.length, group, portMulticast);

                       multicastSocket.send(messageOut);
                       multicastSocket.receive(messageIn);

                       System.out.println(new String(messageOut.getData()));

                       winner = false;
                       lock.lock();
                       for (int i = 0; i < Connection.score.size(); i++) {
                           Connection.score.set(i, 0);
                       }
                       lock.unlock();
                       Thread.sleep(1000);
                   }
                   Thread.sleep(1000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket Exception: " + e.getMessage());
        } catch (IOException e){
             System.out.println("IO Exception: " + e.getMessage());
        } finally {
            if(multicastSocket != null) {
                multicastSocket.close();
            }
        }
    }

    // randomPosition returns two numbers that represent the position of mole.
    public static String randomPosition(){
        Random rand = new Random();
        String randomNumber;
        int num1 = (int)(rand.nextDouble() * 4 + 0);
        int num2 = (int)(rand.nextDouble() * 4 + 0);

        randomNumber = "" + num1 + "," + num2;
        
        return randomNumber;
    }
}

class InitialThread extends Thread {
    ServerSocketChannel serverSocketChannel = null;
    SocketChannel socketChannel = null;
    Socket clientSocket = null;
    DataInputStream in;
    DataOutputStream out;
    int serverPort = 50501;
    public String player = null;
    @Override
    public void run() {
        int position = 0;
        while (true) {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(serverPort));

                // Waiting to players join to the game.
                serverSocketChannel.configureBlocking(true);
                socketChannel = serverSocketChannel.accept();

                clientSocket = socketChannel.socket();

                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                String data = in.readUTF();
                System.out.println("IP: " + clientSocket.getRemoteSocketAddress() + ". Player: " + data);
                player = data;

                // Inicialize each player with 0 points.
                lock.lock();
                Connection.players.add(player);
                Connection.score.add(0);
                lock.unlock();
                        
                Thread.sleep(1000);
            } catch(EOFException e) {
                System.out.println("EOF InitialThread Exception: " + e.getMessage());
            } catch(IOException e) {
                System.out.println("IO InitialThread Exception: " + e.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(InitialThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeAll();
            }
        }
    }

    // closeAll function close all the connections.
    public void closeAll(){
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }

            if (socketChannel != null) {
                socketChannel.close();
            }
            if (serverSocketChannel != null) {
                serverSocketChannel.close();
            }
        } catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}

// Connection class is concurrent, therefore the server is concurrent too.
// We use threads in this class to build a concurrent server.
class Connection extends Thread {
    public static ArrayList<Integer> score = new ArrayList<Integer>();
    public static ArrayList<String> players = new ArrayList<String>();
    public static boolean isFirst;
    Lock lock = new ReentrantLock();
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    
    public Connection (Socket connectionSocket) {
        try {
            clientSocket = connectionSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch(IOException e) {
            System.out.println("IO Connection Exception: " + e.getMessage());
        }
    }

    @Override
    public void run(){
        try {
            String data = in.readUTF();
            // System.out.println("Message received from: " + clientSocket.getRemoteSocketAddress() + ".\nMessage is: " + data);
            if (isFirst) {
                int index = players.indexOf(data);

                lock.lock();
                if (index != -1) {
                    isFirst = false;
                    score.set(index, score.get(index) + 1);
                }
                lock.unlock();
                System.out.println(data + " hit first.");
            }
        } catch(EOFException e) {
            System.out.println("EOF Exception: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("IO Exception: " + e.getMessage());
            }
        }
    }
}
   
