import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private String userName;
	private BufferedReader input;
	private BufferedWriter output;
	
	
	public Client(Socket socket,String userName) {
		
		try {
			this.socket = socket;
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.userName = userName;
			
		}catch(IOException e) {
			closeClient(socket,input,output);
		}
		
	}
	
	public void sendMessage() {
		try {
			
		output.write(userName);
		output.newLine();
		output.flush();
		
		Scanner scanner = new Scanner(System.in);
		
		while(socket.isConnected()) {
			
			String message = scanner.nextLine();
			output.write(userName + ":" + message);
			output.newLine();
			output.flush();
			
		}
		
		}catch(IOException e) {
			closeClient(socket,input,output);
		}
	}
	
	public void getMessage() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String messageFromOther;
				
				while(socket.isConnected()) {
					try {
						messageFromOther = input.readLine();
						System.out.println(messageFromOther);
					}catch(IOException e) {
						closeClient(socket,input,output);
						break;
					}
				}
			}
			
		}).start();
		
	}
	
	public void closeClient(Socket socket,BufferedReader input,BufferedWriter output) {
		
		try {
					
			if(input != null) {
				input.close();
			}
			
			if(output != null) {
				output.close();
			}
			
			if(socket != null) {
				socket.close();
			}
			
			}catch(IOException e) {
				e.printStackTrace();
			}
		
	}
	
}
