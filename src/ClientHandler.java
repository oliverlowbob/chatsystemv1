import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler extends Thread{

    String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos){
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    public void run() {
        String recived;

        while(!this.isInterrupted()){
            //modtager besked og printer ud
            try {
                recived = dis.readUTF();
                System.out.println(recived);
                if(recived.equals("QUIT")){
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }

                if(recived.equals("active")){
                    for(ClientHandler ch : Server.vec) {
                        dos.writeUTF(ch.toString());
                    }
                    continue;
                }


            StringTokenizer stringTokenizer = new StringTokenizer(recived, "#");
                String msgToSend = stringTokenizer.nextToken();

                String client = stringTokenizer.nextToken();

            for(ClientHandler ch : Server.vec){
                if(ch.name.equals(client) && ch.isloggedin==true){
                    ch.dos.writeUTF(name+" : " +msgToSend);
                    break;
                }
            }

                }

            catch (IOException e) {
                e.printStackTrace();
                this.interrupt();
            }

        }
                try {
                    this.dis.close();
                    this.dos.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


    }
    @Override
    public String toString(){
        return name;
    }

}
