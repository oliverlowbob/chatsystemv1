import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * Chatbot-systemet er et program som tillader flere brugere at joine og skrive med hinanden
 *
 * @author  Oliver Dehnfjeld
 * @version 1.0
 */

public class Client
{
    int ServerPort;
    InetAddress ip;

    public Client(int serverPort, InetAddress ip) {
        ServerPort = serverPort;
        this.ip = ip;
    }

    /**
     * Metode til at køre klienten
     * Tildeler client modtaget fra clientmain en socket
     * Opretter en input og outputstream til clienten som bruges vha threads til at sende og modtage beskeder
     * @throws IOException
     */
    public void runClient() throws IOException
    {
        Scanner scanner = new Scanner(System.in);

        Socket s = new Socket(ip, ServerPort);
        //besked, så bruger ved første input er brugernavn
        System.out.println("Indtast brugernavn");

        //instantierer input og outputstream
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        //laver en tråd til at sende en besked
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    // læser beskeden som skal sendes

                    try {
                        String msg = scanner.nextLine();

                        // skriv vha outputstream
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //laver en tråd til at modtage en besked
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // læser beskeden som er sendt til denne client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
                        //hvis socket bliver lukket, eller client mister forbindelse, skrives farvel-besked
                        System.out.println("Goodbye");
                        break;
                    }
                }
            }
        });
        //starter begge tråde
        sendMessage.start();
        readMessage.start();

    }



}
