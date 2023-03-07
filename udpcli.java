import java.util.Scanner;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class udpcli {

  void DatagramPacket(byte[] buf, int length, InetAddress address, int port) {}

  void DatagramSocket() {}

  public static void main(String[] args) throws IOException {
    Integer port_numer;
    InetAddress ip_address;
    Scanner scanner = new Scanner(System.in);
    Integer value;
    if (args.length != 2 || Integer.parseInt(args[1]) == 0) {
      System.out.println("Correct format : ip_address port_number");
      System.exit(0);
    } else if (args[1].equals("0")) {
      System.out.println("port number 0 doesnt exist ");
      System.out.println("Exiting..");
      System.exit(0); // checks the arguments and makes sure the format is correct
    }
    ip_address = InetAddress.getByName(args[0]);
    port_numer = Integer.parseInt(args[1]);

    System.out.println("Please, Enter a number(s) . If multiple, put a space between each number and only put a 0 when you finish (the zero wont be sent or counted) ");
    Integer miaw = 0;
    String words[] = scanner.nextLine().split(" ");
     // gets all numbers into a string array
     
     Integer exception= words.length;
    if ( words[0].equals("0")){
      System.out.print("Exiting...");
    System.exit(0);
    }
     for (int i = 0; i < words.length ; i++) {
       if (words[i].equals("0")){
         exception=i;
         
       }
     }
   
    
     ByteBuffer Buf = ByteBuffer.allocate(4 * exception);  // alocattes enough byte memory for all numbers to be sent

    while (true) {
      value = Integer.parseInt(words[miaw]);
      if (value == 0) {
        break;
      }
      Buf.putInt(value);
      miaw = miaw + 1;
      if (miaw == exception) {

        break;
      }
    } // a loop to put all the numbers in the buffer

    DatagramPacket message = new DatagramPacket(Buf.array(), Buf.array().length, ip_address, port_numer);
    DatagramSocket clientSocket = new DatagramSocket();
    clientSocket.setSoTimeout(10 * 1000);
    // creating the packet that will be sent and the socket and setting a timeout time
    try {
      clientSocket.send(message);
      System.out.println("Connecting...");
      // sending the message
    } catch (IOException e) {

      e.printStackTrace();
    }
    byte[] buffer = new byte[256];

    DatagramPacket answer = new DatagramPacket(buffer, buffer.length);
    // creating the constructor for the packet will be received
    try {
      clientSocket.receive(answer);
      // receiving the packet
      clientSocket.close();
      // closing the socket
    } catch (SocketTimeoutException ex) {
      System.out.println("Timeout error \nThere was a problem connecting to the server:" + ip_address);
      System.out.println("please, try again.");
      System.exit(0);
    }
    // if the packet isnt received in 10 seconds a timeout error is displayed
    value = ByteBuffer.wrap(answer.getData()).getInt();
    System.out.println("Output:" + value);
    //extracting and printing the value that the server sent back
  }

}