import java.io.*;   
import java.net.*;  //For socket programming

public class MyJavaClient{
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

            int check = 0; // as a boolean to run gets all only once
            int nRecs =0;
            String BServer;
            String ArrOfStr[];
            int M_Cores = 0;
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
                Server_R = in.readLine();
                System.out.println("Server says "+Server_R);

                String strArr[] = Server_R.split(" ",-1);

                nRecs = Integer.parseInt(strArr[1]);

                Client_R = "OK\n";
                dout.write(Client_R.getBytes());
                dout.flush();

                ArrOfStr = new String[nRecs];

                for(int i=0; i<nRecs ; i++){
                    ArrOfStr[i] = in.readLine();
                    strArr = ArrOfStr[i].split(" ",-1);
                    int temp = Integer.parseInt(strArr[4]);
                    if(M_Cores< temp){
                        M_Cores = temp;
                        BServer = strArr[0];
                    }
                }





                
               // String strArr[] = Server_R.split(" ",-1); //  server's reply gets copied into the string array
                
                


                }
            

            }
            Client_R = "REDY\n";
            dout.write(Client_R.getBytes());
            dout.flush();
            Server_R = in.readLine();
            System.out.println("Server says "+Server_R);

            
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