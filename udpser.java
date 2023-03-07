import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class udpser {
  

  void DatagramPacket(byte[] buf, int length) {}
  void DatagramPacket(byte[] buf, int length, InetAddress address, int port) {}

  void DatagramSocket(Integer port_numer) {

  }

  // constructors for the packet and the socket 

  public static void main(String[] args) throws NumberFormatException, IOException {
    Integer acul = 0;
    Integer value;
    if (args.length!=1) {
      System.out.print("correct format: <port number>");
      System.exit(0);
    }
    DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(args[0]));
    while (true) {
      // a loop that never ends because the server program never exits
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      serverSocket.receive(packet); // receiving the packet from the client
      Integer miaw = 0;
      Integer counter = packet.getLength(); // getting the length of the data in the packet
      byte[] array = packet.getData(); // getting the data from the packet
      while (true) {

        value = ByteBuffer.wrap(array).getInt(miaw);

        if (counter == miaw) {
          break;
        }
        miaw = miaw + 4;
        acul = acul + value;

        System.out.println(acul);
      }
      // a loop to extract each number in the packet that we received and add it to the acccumulator
      byte[] Buff = ByteBuffer.allocate(4).putInt(acul).array();
      DatagramPacket answer = new DatagramPacket(Buff, Buff.length, packet.getSocketAddress());
      // creating the packet that will be sent back to the client with the accumulator's result
      
        serverSocket.send(answer);
        // sending back the packet
      
    }
  }
}