import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server
{

    //vector til at gemme aktive clients
    static Vector<ClientHandler> vec = new Vector();


    public static void main(String[] args)
        throws IOException {

        ServerSocket serverSocket = new ServerSocket(1337);
        Socket s;


        while(true){
            s = serverSocket.accept();

            System.out.println("Klient modtaget " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String userName = dis.readUTF();

            ClientHandler clientHandler = new ClientHandler(s, userName, dis, dos);

            Thread t = new Thread(clientHandler);

            System.out.println("Tilføjer klient til aktive liste over aktive brugere");
            vec.add(clientHandler);


            //start tråden
            t.start();
        }
    }


}