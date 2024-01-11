import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ShoppingServer {

	private ServerSocket shoppingServer;
	
	

	
	public ShoppingServer(ServerSocket serverSocket) {
		this.shoppingServer = serverSocket;
	}
	
	
	public void runShoppingServer()  {
		try {
			
			
			while(!shoppingServer.isClosed()) {
			
				
				Socket connection = shoppingServer.accept();
				System.out.println("A customer has connected: ");
			
				ClientProcess client = new ClientProcess(connection);
				
				Thread clientThread = new Thread(client);
				clientThread.start();
			}
		}catch(IOException e) {
			closeShoppingServer();
		}
	}
	
	public void closeShoppingServer() {
		try {
		if(shoppingServer != null) {
			shoppingServer.close();
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
