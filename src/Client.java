import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    int ServerPort;
    InetAddress ip;

    public Client(int serverPort, InetAddress ip) {
        ServerPort = serverPort;
        this.ip = ip;
    }

    public void runClient() throws IOException
    {
        Scanner scanner = new Scanner(System.in);

        Socket s = new Socket(ip, ServerPort);
        System.out.println("Indtast brugernavn");

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.

                    try {
                        String msg = scanner.nextLine();

                        // write on the output stream
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
                        System.out.println("Goodbye");
                        break;
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }



}
