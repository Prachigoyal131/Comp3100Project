import java.io.*;   
import java.net.*;  

public class Stage2{
    public static void main(String args[]){

        try{
            Socket s = new Socket("localhost",50000); //using default Port no.
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String Server_R; // for server reply
            String Client_R; //for client reply

            Client_R = "HELO\n";                   // to initiate three way handshake
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
            int numServers = 0;
            int mem = 0;
            int disk =0;
            
            while(!Server_R.equals("NONE")){
                
                Client_R = "REDY\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);

                if(Server_R.contains("JOBN")){
                
                String TempServer_R = Server_R;
                String EachWArr[] = TempServer_R.split(" ",-1); 

                job_ID = Integer.parseInt(EachWArr[2]);
                numServers =  Integer.parseInt(EachWArr[4]);
                mem =  Integer.parseInt(EachWArr[5]);
                disk =  Integer.parseInt(EachWArr[6]);


                Client_R = "GETS Avail " + numServers + " " + mem + " " + disk + "\n";    // to check servers available for the job
                dout.write(Client_R.getBytes());
                dout.flush();
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);
                
                EachWArr = Server_R.split(" ", -1);
                nRecs = Integer.parseInt(EachWArr[1]);
                
                if(nRecs != 0){
                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();

                EachLArr = new String[nRecs];

                for(int i=0; i<nRecs ; i++){
                    EachLArr[i] = in.readLine();
                    System.out.println("Server says "+EachLArr[i]);               
                }
                
                EachWArr = EachLArr[0].split(" ", -1);

                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                TempServer_R = in.readLine();
                System.out.println("Server says "+TempServer_R);

                Client_R = "SCHD " + job_ID  + " "+ EachWArr[0]+ " " + EachWArr[1] + "\n"; // FOR JOB SCHEDULING
                dout.write(Client_R.getBytes());
                dout.flush();
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);

            }
            else{
                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                TempServer_R = in.readLine();
                System.out.println("Server says "+TempServer_R);
                
                Client_R = "GETS Capable " + numServers + " " + mem + " " + disk + "\n"; // To check for the capable server when there is no readily available server
                dout.write(Client_R.getBytes());
                dout.flush();
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);
                
                EachWArr = Server_R.split(" ", -1);
                nRecs = Integer.parseInt(EachWArr[1]);

                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();

                EachLArr = new String[nRecs];

                for(int i=0; i<nRecs ; i++){
                    EachLArr[i] = in.readLine();
                    System.out.println("Server says "+EachLArr[i]);               
                }
                
                EachWArr = EachLArr[0].split(" ", -1);

                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                TempServer_R = in.readLine();
                System.out.println("Server says "+TempServer_R);

                Client_R = "SCHD " + job_ID  + " "+ EachWArr[0]+ " " + EachWArr[1] + "\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);

        
            }

             }
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