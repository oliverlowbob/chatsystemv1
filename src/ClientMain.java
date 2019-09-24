import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);



        System.out.println("JOIN ip:port");
        String line = scanner.nextLine();
        String[] tokens = line.split(" |\\:");
        if(tokens[0].equals("JOIN")) {



            InetAddress ip = InetAddress.getByName(tokens[1]);

            int serverPort = Integer.parseInt(tokens[2]);


            Client client = new Client(serverPort, ip);
            client.runClient();
        }
    }
}
