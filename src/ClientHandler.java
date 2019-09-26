import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
/**
 * Chatbot-systemet er et program som tillader flere brugere at joine og skrive med hinanden
 *
 * @author  Oliver Dehnfjeld
 * @version 1.0
 */


public class ClientHandler implements Runnable {

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

    //opretter run-metode, som køres ved oprettelse af objekt af klassen
    public void run() {
        String recived;

        while(true){
            try {
                //modtager besked
                recived = dis.readUTF();
                //tjekker om besked er active
                if(recived.equals("active")){
                    //kører for-each loop på alle aktive klienter, og printer dem ud vha toString-metode
                    for(ClientHandler ch : Server.vec) {
                        dos.writeUTF(ch.toString());
                    }
                    continue;
                }
                //tjekker om beskeden er QUIT, hvis det er true, lukkes socket og der printes ud i serveren hvem der er leavet
                if(recived.equals("QUIT")){
                    this.isloggedin=false;
                    this.s.close();
                    System.out.println(name + " disconnected");
                    break;
                }


            //Stringtokenizer splitter en string op i tokens, med en given delimiter, tager en string og delimiter som argument
            StringTokenizer stringTokenizer = new StringTokenizer(recived, ":");
                //første token er beskeden som skal sendes, andet token er klienten der skal sendes til
                String msgToSend = stringTokenizer.nextToken();
                String client = stringTokenizer.nextToken();

                //tjekker hvis der skrives all
                if(client.equalsIgnoreCase("all")) {
                        //hvis det et true, køres et for-each loop på alle clienthandlerobjekter i vectoren
                        for (ClientHandler ch : Server.vec) {
                            if(!ch.name.equals(client)) {
                                //hvis navnet på useren der skriver all ikke er lig med navnet i clienthandler-objektet skrives beskeden
                                ch.dos.writeUTF(name + " : " + msgToSend);
                            }
                        }
                        break;
                }
            //tjekker alle clienhandlerobjekter i vectoren
            for(ClientHandler ch : Server.vec){
                //hvis navnet i ch er det samme som client-string og ch er logged ind sendes beskeden
                if(ch.name.equals(client) && ch.isloggedin==true){
                    ch.dos.writeUTF(name+" : " +msgToSend);
                    break;
                }
            }

                }

            catch (IOException e) {
                e.printStackTrace();

            }
            //logging funktion


        }



    }
    //returnerer navnet
    @Override
    public String toString(){
        return name;
    }

}
