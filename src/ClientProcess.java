import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;

public class ClientProcess implements Runnable {
	
	private Socket socket;
	private String userName;
	private BufferedReader input;
	private BufferedWriter output;
	public static ArrayList<ClientProcess> clientProcess = new ArrayList<>();
	
	public ClientProcess(Socket socket) {
		try {
		this.socket = socket;
		this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		this.userName=input.readLine();
		clientProcess.add(this);
		promptTOtherUser("ShoppingServer: "+userName+" "+"has enter into conversation: ");
		}catch(IOException e) {
			closeClient(socket, input,output);
		}
	}
	
	public void promptTOtherUser(String messageToNotify){
		
		
		for (ClientProcess client : clientProcess) {
			try {	
				if(!client.userName.equals(userName)) {
					client.output.write(messageToNotify);
					client.output.newLine();
					client.output.flush();
					
				}
			}catch(IOException e) {
				closeClient(socket, input, output);
			}
		}
		
	}
	

	@Override
	public void run() {
		
		String messageToSent;
		
		while(socket.isConnected()) {
			try {
				
			messageToSent = input.readLine();
			promptTOtherUser(messageToSent);
			
			
			}catch(IOException e) {
				closeClient(socket, input, output);
				break;
			}
		
		}
	}
	
	public void removeClientProcess() {
		clientProcess.remove(this);
		promptTOtherUser("Server: "+" "+userName+"has left this conversation.");
	}
	
	public void closeClient(Socket socket,BufferedReader input,BufferedWriter output) {
		removeClientProcess();
		try {
		if(socket != null) {
			socket.close();
		}
		
		if(input != null) {
			input.close();
		}
		
		if(output != null) {
			output.close();
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
