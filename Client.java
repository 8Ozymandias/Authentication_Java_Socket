import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {                                                                                                 
    Socket socket;
    BufferedReader read;
    PrintWriter output;
                                                         
    Connect c = new Connect();

    public class Connect {
        private String USERNAME = "java";
        private String PASSWORD = "java";
        private int PORT = 9090;
        private String HOSTNAME = "localhost";
    
        public String getUsername(){
            return this.USERNAME;
        }
    
        public String getPassword(){                                                                                                             
    
            return this.PASSWORD;
        }
    
        public int getPort(){
            return this.PORT;
        }
    
        public String gethostName(){
            return this.HOSTNAME;
        }
    }

    public void startClient() throws UnknownHostException, IOException{

        Scanner scn = new Scanner(System.in);

        //Create socket connection
        socket = new Socket(c.gethostName(), c.getPort());

        //create printwriter for sending login to server
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        //prompt for user name
        System.out.println("Enter Username:");
        String username = scn.nextLine();
        //send user name to server
        output.println(username);

        //prompt for password
        System.out.println("Enter Password:");
        String password = scn.nextLine();
        //send password to server
        output.println(password);
               
        output.flush();

        //create Buffered reader for reading response from server
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read response from server
        String response = read.readLine();
        System.out.println("This is the response: " + response);

        //display response
        System.out.println(response);
    }

    public void fileInfo(){

    }

    public static void main(String args[]){
        Client client = new Client();
        try {
            client.startClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}