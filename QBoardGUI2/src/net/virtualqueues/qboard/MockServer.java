package net.virtualqueues.qboard;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

class MockServer implements Runnable {
		InetAddress localhost = InetAddress.getLoopbackAddress();
		InetSocketAddress localsocketaddr = new InetSocketAddress(localhost, 2342);

		InputStream is;
		ServerSocketChannel sockChannel = null;
		Socket clientSocket = null;
		 
		@Override
		public void run() {
			try {
				sockChannel = ServerSocketChannel.open();
				sockChannel.bind(localsocketaddr);
				clientSocket = sockChannel.accept().socket();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				is = clientSocket.getInputStream();
				byte[] output = new byte[256];
				is.read(output);
				String outputText = new String(output);
				System.out.println(outputText);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}