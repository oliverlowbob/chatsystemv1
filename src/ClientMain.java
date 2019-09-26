import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Chatbot-systemet er et program som tillader flere brugere at joine og skrive med hinanden
 *
 * @author  Oliver Dehnfjeld
 * @version 1.0
 */

public class ClientMain {

    /**
     * Main-metode til at køre klienter
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);


        //tager input fra brugeren (skal følge protokol)
        System.out.println("JOIN ip:port");
        String line = scanner.nextLine();
        //splitter input op vha delimiter, som er mellemrum og :
        String[] tokens = line.split(" |\\:");
        //tester om input overholder protokol
        if(tokens[0].equals("JOIN")) {


            //sætter ip og port til anden og tredje element i tokens-array
            InetAddress ip = InetAddress.getByName(tokens[1]);

            int serverPort = Integer.parseInt(tokens[2]);

            //instantierer og kører client med ip og port
            Client client = new Client(serverPort, ip);
            client.runClient();
        }
    }
}
