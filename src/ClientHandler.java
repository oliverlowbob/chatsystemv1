import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable{

    Scanner scanner = new Scanner(System.in);
    static String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    boolean loop;

    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos){
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    public void run()
            {
        String recived;


        while(loop=true){
            //modtager besked og printer ud
            try {
                recived = dis.readUTF();
                System.out.println(recived);
                if(recived.equals("logout")){
                    this.isloggedin=false;
                    this.s.close();
                    loop=false;
                }


            StringTokenizer stringTokenizer = new StringTokenizer(recived, "##");
            String msgToSend = stringTokenizer.nextToken();
            String user = stringTokenizer.nextToken();

            for(ClientHandler ch : Server.vec){
                if(ch.name.equals(user) && ch.isloggedin==true){
                    ch.dos.writeUTF(this.name+" ## " + msgToSend);
                    loop=false;
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
                try {
                    this.dis.close();
                    this.dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


    }

}
