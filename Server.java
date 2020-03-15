import java.io.*;
import java.net.*;

public class Server {
    ServerSocket serversocket;
    Socket client;
    int bytesRead;
    BufferedReader input;
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

    public void start() throws IOException{
        System.out.println("Connection Starting on port:" + c.getPort());
        //make connection to client on port specified
        serversocket = new ServerSocket(c.getPort());


       	// running infinite loop for getting client request 
        while(true){
       
        try {

        //accept connection from client or socket object to receive incoming client requests
        client = serversocket.accept();

        System.out.println("Waiting for connection from client");

        System.out.println("A new client is connected : " + client); 

        //obtaining input and output steams
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        System.out.println("Assigning new thread for this client");
        //create a new thread object
        Thread t = new ClientHandler(client, input, output);
        //Invoking the start() method
        t.start();

            }

            catch (Exception e) {
            e.printStackTrace();
                                }
                   }
    }

          
}

// ClientHandler class 
class ClientHandler extends Thread  
{ 
    
    
    
    final BufferedReader input; 
    final PrintWriter output; 
    final Socket client; 
      
  
    // Constructor 
    public ClientHandler(Socket client, BufferedReader input, PrintWriter output)  
    { 
        this.client = client; 
        this.input = input; 
        this.output = output; 
    } 


    @Override
    public void run() {          
      
        
            //open buffered reader for reading data from client
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //open printwriter for writing data to client
            output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
    
            System.out.println("Assigning new thread for this client"); 
      
    
            String username = input.readLine();
            System.out.println("SERVER SIDE" + username);
            String password = input.readLine();
            System.out.println("SERVER SIDE" + password);
         
    
            if(username.equals(c.getUsername()) &&password.equals(c.getPassword())){
                output.println("Welcome, " + username);
            }else{
                output.println("Login Failed");
            }
    
            output.flush();
            output.close();
    
       

    }


    public static void main(String[] args){
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
  
   
} 


