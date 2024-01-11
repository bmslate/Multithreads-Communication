import java.io.IOException;
import java.net.ServerSocket;


public class ShoppingServerTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server = new ServerSocket(1245);
		ShoppingServer shoppingServer = new ShoppingServer(server);
		shoppingServer.runShoppingServer();

	}

}
