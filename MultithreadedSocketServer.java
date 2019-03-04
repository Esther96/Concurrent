 
import java.net.*;
import java.util.Date;
import java.io.*;
public class MultithreadedSocketServer {
public static int[] seats = new int[12];
     
  public static void main(String[] args) throws Exception {

	 System.out.println("Welcome to the DIC lovin train reservation system!");
	 int counter=0;
	 try{
      ServerSocket server=new ServerSocket(28882);
      
      
      System.out.println("Server Started ....");
      while(true){
        counter++;
        
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(" >> " + "Client No:" + counter + " started!");
        
        ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
        sct.start();
      }
      
    }catch(Exception e){
      System.out.println(e);
    }
  }
}

  class ServerClientThread extends Thread {
	  Socket serverClient;
	  int clientNo;
	  
	  ServerClientThread(Socket inSocket,int counter){
	    serverClient = inSocket;
	    clientNo=counter;  
	  }
  
	  @Override
	public void run(){
	    try
	    {
	    
	        System.out.println("Server Started ....");
	        // Step 2: Processing the request. 
	        DataInputStream dis = new DataInputStream(serverClient.getInputStream()); 
	        DataOutputStream dos = new DataOutputStream(serverClient.getOutputStream()); 
	       
	        while (true) 
	        { 
	            // wait for input 
	        	String input = dis.readUTF(); 
	  
	            if(input.equals("0")) 
	                break; 
	        
	            System.out.println("Values received in server:-" + input); 
	            String result ="";
	            int seatnumber = 0;
	          
	            int choice = Integer.parseInt(input);
	           
	            seatnumber = 0;

	            // If they chose a window seat, attempt to book it.
	            if (choice == 1) {
	                seatnumber = bookWindow();
	                // No window seats available, try booking an aisle seat for them instead.
	                if (seatnumber == -1) {
	                    seatnumber = bookAisle();
	                    if (seatnumber != -1) {
	                        result = "Sorry, we were not able to book a window seat. But do have an aisle seat." +seatnumber;
	                        printBoardingPass(seatnumber);
	                    }
	                }
	                else {
	                    // Booking a window seat was successful.
	                    result = "You are in luck, we have a window seat available!" + seatnumber;
	                    printBoardingPass(seatnumber);
	                }
	            }

	            else if (choice == 2) {
	 
	                // If they chose booking an isle, check to see if it is available.

	                seatnumber = bookAisle();

	                // If not available, see if we have window seats available.

	                if (seatnumber == -1) {

	                    seatnumber = bookWindow();

	                    if (seatnumber != -1) {

	                        result = "Sorry, we were not able to book an aisle seat. But do have a window seat." + seatnumber;
	                        printBoardingPass(seatnumber);
	                    }
	                }
	                else {
	                    // Booking an aisle seat was successful.

	                    result = "You are in luck, we have an aisle seat available!" + seatnumber;
	                    printBoardingPass(seatnumber);
	                }
	            }
	            else {
	                // Print an error message if they did not choose 1, 2, or 0 for their choice.
	                result ="Invalid choice made. Please try again!";
	                choice = 0;
	            }
	            // No window or aisle seats were available. 

	            if (seatnumber == -1) {
	                result = "We are sorry, there are no window or aisle seats available.";
	                //System.out.println();
	            }           
	            
	        dos.writeUTF(result + " seat " + seatnumber );
	        
	        dos.flush();
	      }
	        
	      dis.close();
	      dos.close();
	      serverClient.close();
	    }
	    catch(Exception ex)
	    {
	      System.out.println(ex);
	    }
	    finally{
	      System.out.println("Client -" + clientNo + " exit!! ");
	    }
	  }
	
	
	 private static int bookAisle() {

          for (int counter = 6; counter < 12; counter++) {

              if (MultithreadedSocketServer.seats[counter] == 0) {

            	  MultithreadedSocketServer.seats[counter] = 1;

                  return counter + 1;

              }

          }

          return -1;

      }
  private static int bookWindow() {

      for (int counter = 0; counter < 6; counter++) {

          if (MultithreadedSocketServer.seats[counter] == 0) {

        	  MultithreadedSocketServer.seats[counter] = 1;

              return counter + 1;

          }

      }

      return -1;

  }
  private static void printBoardingPass(int seatnumber) {
	  Date timenow = new Date();
      System.out.println();
      System.out.println("Date: " + timenow.toString());
      System.out.println("Boarding pass for seat number: " + seatnumber);
      System.out.println("This ticket is non-refundable and non-transferable.");
      System.out.println("Please be curteous, do not smoke. Enjoy your trip.");
      System.out.println();
  	} 
  }


