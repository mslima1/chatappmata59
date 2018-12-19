package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mensagem {

	private Socket s;
	private ArrayList<PrintStream> clientes;

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ArrayList<PrintStream> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<PrintStream> clientes) {
		this.clientes = clientes;
	}

	public Mensagem(Socket s, ArrayList<PrintStream> clientes) {
		this.s = s;
		this.clientes = clientes;
		Thread();
	}

	private void Thread() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				String mensagem = "";

				try {
					InputStreamReader isr = new InputStreamReader(s.getInputStream());
					BufferedReader br = new BufferedReader(isr);

					while ((mensagem = br.readLine()) != null) {
						enviarMensagem(mensagem);

					}

				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});

		t.start();

	}

	private void enviarMensagem(String mensagem) {
		for (int a = 0; a < clientes.size(); a++) {

			clientes.get(a).println(mensagem);
			clientes.get(a).flush();

		}
	}

}
