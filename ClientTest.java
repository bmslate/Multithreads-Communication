import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {

	public static void main (String args[]) throws IOException, IOException {
		
		Scanner inputUserName = new Scanner(System.in);
		System.out.println("Please enter your name: ");
		String userName = inputUserName.nextLine();
		Socket socket = new Socket("localhost",1245);
		Client client = new Client(socket,userName);
		client.getMessage();
		client.sendMessage();
		
		
	}
}
