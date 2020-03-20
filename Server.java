import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args){
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    ServerSocket serversocket;
    Socket client;
    int bytesRead;
    BufferedReader input;
    PrintWriter output;
 

    Connect c = new Connect();

    public class Connect {
        public String USERNAME = "java";
        public String PASSWORD = "java";
        public int PORT = 9090;
        public String HOSTNAME = "localhost";
    
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
class ClientHandler extends Thread  //ClientHandler is a subclass. It inherits the attributes and methods from the Thread class,
{  
    public BufferedReader input; 
    public PrintWriter output; 
    public Socket client;    
  
    // Constructor 
    public ClientHandler(Socket client, BufferedReader input, PrintWriter output)  
    { 
        
        this.client = client; 
        this.input = input; 
        this.output = output; 
        
    } 

    @Override
    public void run() {          
    
        Server server = new Server(); //https://www.w3schools.com/java/java_inner_classes.asp
        Server.Connect c = server.new Connect();

        //NEW CODE-I am attempting to integrate a while loop into the code 
        //so that the ClientHandler class in Server.java can repeatedly/continuously
        //send and receive input from the Client class in Client.java
       
        try{//https://stackoverflow.com/questions/36075831/why-i-got-unhandled-exception-type-ioexception/36075937
        
            while(true)
            {
            //open buffered reader for reading data from client
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //open printwriter for writing data to client
            output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
    
            System.out.println("Assigning new thread for this client"); 
            System.out.println("Testing");
      
    
            String username = input.readLine();
            System.out.println("SERVER SIDE" + username);
            String password = input.readLine();
            System.out.println("SERVER SIDE" + password);
          
            // } If I close/end the while statement here,I get an error that says several of the objects/methods in the if else statement cannot be resolved or resolved to a type   
            
            if(username.equals(c.getUsername()) &&password.equals(c.getPassword())){
                output.println("Welcome, " + username);
            }else{
                output.println("Login Failed");
            }

            output.flush();//Placing this here I get the Connection reset error but it only appears once //http://net-informations.com/java/err/reset.htm
            // output.close(); //With output.flush() and output.close(); directly below the end of the else statement, everything works except for the fact that inside the terminal for Server.java I get the error java.net.SocketException: Socket is closed. I think the reason I may be getting the connectio reset and or the socket is closed error is becuase they are inside of a loop. If you look at the terminal for Server.java we see Assining the new thread for this client and testing message appear a second time which means that the loop is working because after is processed everything, it went back up in the code, to the top of loop and processed everything and then output those two message when it encountered them again but after it does that we get the error message. http://net-informations.com/java/err/reset.htm
        
            }

            // output.flush(); //With uncommenting this and running the code I get a new error, that is, java.net.SocketException: Connection reset and it shows up repeatedly.The code on the client side runs perfectly.This may not show up repeatedly anymore within this code because I change dup the placement of the while loop and try catch statement
            // output.close(); //When I uncomment this I get the error Unreachable code
        
        } 
        catch(IOException e){
            e.printStackTrace();
        }
        // output.flush();//With this placed at the end of the final catch statement with output.close being commented out, the if else statement does not execute.
        // output.close();
        //I think the problem with putting this here is that it will close infinitely since it's inside the while loop 
        //which is what I think happened earlier today(March 18,2020) in the terminal
        //where an error was consistently being published to the screen without end 
        //and the part I remember seeing is "socket.close()" 
        //I just ran it again and the same thing happened(March 19,2020). THis is when
        
        
//Why is that when I introduce the while loop to cover/encapsulate the try catch statement
//that is inside the run method which is inside the ClientHandler class in the code
//I get an error of Unreachable code for output.flush(); 
//ALSO when I try to run the code by removing these two it doesn't completely work, the code
//on Client.java only allows us to type and show the username and password but nothing more
            
        // output.flush();
        // output.close();
    
       

    }
    

 
  
   
} 


