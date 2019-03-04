 
import java.net.*;
import java.io.*;
public class TCPClient {
  public static void main(String[] args) throws Exception {
  try{
    Socket socket=new Socket(InetAddress.getLocalHost(),28882);
    DataInputStream inStream=new DataInputStream(socket.getInputStream());
    DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String clientMessage="",serverMessage="";
    while(!clientMessage.equals("bye")){
    	System.out.print("Please enter 1 for window, 2 for aisle, or 0 to exit: ");
      clientMessage=br.readLine();
      outStream.writeUTF(clientMessage);
      outStream.flush();
      serverMessage=inStream.readUTF();
      System.out.println(serverMessage);
    }
    outStream.close();
    outStream.close();
    socket.close();
  }catch(Exception e){
    System.out.println(e);
  }
  }
}
