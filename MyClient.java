import java.io.*;   
import java.net.*;  //For socket programming

public class MyClient{
    public static void main(String args[]){

        try{
            Socket s = new Socket("localhost",50000); //using default Port no.
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String Server_R;
            String Client_R;

            Client_R = "HELO\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);

            String User = System.getProperty("user.name");
            Client_R = "AUTH " +User+ "\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says" +Server_R);

            Client_R = "REDY\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);

            int check = 0;
            int nRecs =0;
            String L_server;
            

            while(!Server_R.equals("NONE")){
                Client_R = "REDY\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);


            }
            














            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
       
    }
}