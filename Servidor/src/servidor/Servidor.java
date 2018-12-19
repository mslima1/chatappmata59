
package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

public class Servidor {

	public static void main(String[] args) throws IOException {
		String msgRec;
		ArrayList<PrintStream> clientes = new ArrayList<>();
		DatagramSocket ds = new DatagramSocket(10000);
		byte[] data = new byte[100];
		DatagramPacket pacote = new DatagramPacket(data, data.length);
		try {
			ServerSocket server = new ServerSocket(5000);
			Socket socket;

			while (true) {
				socket = server.accept();
				ds.receive(pacote);
				clientes.add(new PrintStream(socket.getOutputStream()));

				Mensagem mensagem = new Mensagem(socket, clientes);
				ds.send(pacote);

//                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
//                BufferedReader br = new BufferedReader(isr);
//                while((msgRec = br.readLine()) != null){
//                    System.out.println(mensagem);
//                }

			}

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}
