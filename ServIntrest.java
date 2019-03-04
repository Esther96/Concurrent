// Java program to illustrate Server Side Programming 
// for Simple Calculator using TCP 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.StringTokenizer; 
  
public class ServIntrest
{ 
    public static void main(String args[]) throws IOException 
    { 
  
        // Step 1: Establish the socket connection. 
        ServerSocket ss = new ServerSocket(4444); 
        Socket s = ss.accept(); 
  
        // Step 2: Processing the request. 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
  
        while (true) 
        { 
            // wait for input 
            String input = dis.readUTF(); 
  
            if(input.equals("bye")) 
                break; 
  
            System.out.println("Values received:-" + input); 
            int result; 
  
            // Use StringTokenizer to break the equation into operand and 
            // operation 
            StringTokenizer st = new StringTokenizer(input); 
  
            int amount = Integer.parseInt(st.nextToken()); 
            int rate = Integer.parseInt(st.nextToken()); 
            int time = Integer.parseInt(st.nextToken()); 
  
            double interest = (amount * rate * time) / 100.0;
            System.out.println("Sending the result..."); 
  
            // send the result back to the client. 
            dos.writeUTF(Double.toString(interest)); 
        } 
    } 
} 