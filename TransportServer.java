// Java program to illustrate Server Side Programming 
// for Simple Calculator using TCP 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

  
public class TransportServer
{ 
	private static int[] seats = new int[12];
    public static void main(String args[]) throws IOException 
    { 
  
    	// Step 1: Establish the socket connection. 
        ServerSocket ss = new ServerSocket(4444); 
        Socket s = ss.accept(); 
        System.out.println("Server Started ....");
        // Step 2: Processing the request. 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
       
        while (true) 
        { 
            // wait for input 
        	String input = dis.readUTF(); 
  
            if(input.equals("0")) 
                break; 
        
            System.out.println("Values received in server:-" + input); 
            String result ="";
            int seatnumber = 0;
  
            // Use StringTokenizer to break the equation into operand and 
          
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
        }
  }
          
    private static int bookWindow() {
        for (int i = 0; i < 6; i++) {
            if (seats[i] == 0) {
                seats[i] = 1;
                return i + 1;
            }
        }
        return -1;
    }


    // This function checks to see if aisle seats were available, -1 if full.

    private static int bookAisle() {
        for (int i = 6; i < 12; i++) {
            if (seats[i] == 0) {
                seats[i] = 1;
                return i + 1;
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
    // This simply prints out a nice little boarding pass message with their seat number and date of issue.
   
} 

