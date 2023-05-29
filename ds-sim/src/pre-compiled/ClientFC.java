import java.io.*;   
import java.net.*;  //For socket programming

public class ClientFC{
    public static void main(String args[]){

        try{
            Socket s = new Socket("localhost",50000); //using default Port no.
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String Server_R; // for server reply
            String Client_R; //for client reply

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

            
            int nRecs =0;  // no. of server records
            String EachLArr[];
            int job_ID = 0; //implies job id

            while(!Server_R.equals("NONE")){
            
            Client_R = "REDY\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);
            
            if(Server_R.equals("NONE")){
                break;
            }

            String temp = Server_R;

            // temp --> JOBN 36 0 656 3 600 1800
             String tempArr[] = temp.split(" ",-1);
             // tempArr[2] --> jobid
             job_ID = Integer.parseInt(tempArr[2]);

             if(tempArr[0].equals("JCPL")){
                continue;
             }

            Client_R = "GETS Capable"+ " " + tempArr[4] + " " + tempArr[5] + " " + tempArr[6] + "\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);
            
            String EachWArr[] = Server_R.split(" ", -1);
            // DATA 5 128
            nRecs = Integer.parseInt(EachWArr[1]);
            
            EachLArr = new String[nRecs];

            Client_R = "OK\n";
            dout.write(Client_R.getBytes());
            dout.flush();


            for(int i=0; i< nRecs;i++){
                EachLArr[i] = in.readLine();
                System.out.println("Server says "+EachLArr[i]);
            }

            EachWArr = EachLArr[0].split(" ",-1);
            // server serverid . . .. . . . . . .. . 

            Client_R = "OK\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);

            Client_R = "SCHD" + " " + job_ID + " " + EachWArr[0] + " " + EachWArr[1] + "\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);

        }
            Client_R = "QUIT\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R); 
    
    
                dout.close();
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
           
        }
    }