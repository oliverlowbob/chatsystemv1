import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Chatbot-systemet er et program som tillader flere brugere at joine og skrive med hinanden
 *
 * @author  Oliver Dehnfjeld
 * @version 1.0
 */

public class Server
{

    //følgende gør der kun kan kaldes instansen af serveren via getInstance (singleton-pattern)
    private static Server instance;
/**
 * metoden sikrer singleton-pattern, da der kun kan være en instans af Server-klassen
 * @return instansen af Server
 */
    private static Server getInstance(){
        if(instance==null){
            instance = new Server();
        }
        return instance;
    }
    private Server() {
    }

    //vector til at gemme aktive clients
    static Vector<ClientHandler> vec = new Vector(5);

    /**
     * Main metode til at køre serveren
     * @param args
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException {

        Logger logger = Logger.getLogger(Server.class.getName());
        logger.setLevel(Level.FINEST);
        FileHandler fileHandler = new FileHandler("log.txt", true);
        logger.addHandler(fileHandler);



        ServerSocket serverSocket = new ServerSocket(1337);
        Socket s;

        //While-loop til at modtage nye clients som tilslutter serveren, kører hele tiden
        while(true){
            s = serverSocket.accept();

            System.out.println("Klient modtaget " + s);

            //instantierer inputstream, outputstream og username
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String userName = dis.readUTF();
            //Modtager en klient, opretter et objekt af clienthandler med socket, inputstream, outputsream og username
            ClientHandler clientHandler = new ClientHandler(s, userName, dis, dos);

            Thread t = new Thread(clientHandler);

            System.out.println("Tilføjer klient til aktive liste over aktive brugere");
            //Tilføjer objektet til vores vector
            vec.add(clientHandler);
            dos.writeUTF("J_OK");


            //start tråden
            t.start();
        }
    }


}