import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    static int ServerPort;

    public static void main(String args[]) throws IOException
    {
        Scanner scanner = new Scanner(System.in);

        // skriv ip og port
        System.out.println("ip");
        InetAddress ip = InetAddress.getByName(scanner.nextLine());
        System.out.println("port");
        ServerPort = scanner.nextInt();

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
                    String msg = scanner.nextLine();

                    try {
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

                        e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }
}
