import java.io.*;   
import java.net.*;  //For socket programming

public class MyJavaClient{
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

            int check = 0; // as a boolean to run gets all only once
            int nRecs =0;  // no. of server records
            String BServer = "";  // name of biggest server i.e., more no. of cores
            String EachLArr[];
            int M_Cores = 0;  // Maximum no. of cores
            int N_BServers = 0; // For finding no. of biggest servers
            int BServer_ID = 0; // implies Biggest Server ID
            int job_ID = 0; //implies job id
            while(!Server_R.equals("NONE")){
                

                if(check==0){                     
                
                    Client_R = "REDY\n";
                    dout.write(Client_R.getBytes());
                    dout.flush();
                    Server_R = in.readLine();
                    System.out.println("Server says "+Server_R);

                Client_R = "GETS All\n";
                dout.write(Client_R.getBytes());
                dout.flush();
                String TempServer_R = in.readLine();
                System.out.println("Server says "+Server_R);

                String EachWArr[] = TempServer_R.split(" ",-1); 

                nRecs = Integer.parseInt(EachWArr[1]);

                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();

                EachLArr = new String[nRecs];

                for(int i=0; i<nRecs ; i++){
                    EachLArr[i] = in.readLine();
                    EachWArr = EachLArr[i].split(" ",-1);
                    int temp = Integer.parseInt(EachWArr[4]);
                    if(M_Cores< temp){
                        M_Cores = temp;
                        BServer = EachWArr[0];    // for finding name of biggest server
                    }
                
                }
                int count = 0;
                for (int i=0; i<nRecs ; i++){
                    EachWArr = EachLArr[i].split(" ",-1);
                    String temp = EachWArr[0];
                    if( BServer.equals(temp)){
                        count++;
                    }
                }
                N_BServers = count; // provide us with total no. of largest servers

                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();

                TempServer_R = in.readLine();
                System.out.println("Server says "+TempServer_R);


                
               // String strArr[] = Server_R.split(" ",-1); //  server's reply gets copied into the string array
                check = 1;
                continue;

            }
             
            

            String EachWArr[] = Server_R.split(" ",-1); 
            if(EachWArr[0].equals("JOBN")){
                job_ID = Integer.parseInt(EachWArr[2]);
                Client_R = "SCHD " + job_ID  + " "+ BServer+ " " +BServer_ID+ "\n"; // FOR JOB SCHEDULING
                    dout.write(Client_R.getBytes());
                    dout.flush();
                    Server_R = in.readLine();
                    System.out.println("Server says "+Server_R);

            }
            if(EachWArr[0].equals("JCPL")){   // for ignoring rescheduling i.e., JCPL
                Client_R = "REDY\n";
               dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);
                continue;
            }
            Client_R = "REDY\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);
            
            if(BServer_ID==N_BServers-1){
                BServer_ID = 0;
            }
            else{
                BServer_ID++ ;
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