package me.huqiao.concurrent.interrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReaderThread extends Thread{
	
	private final Socket socket;
	private final InputStream in;

	public ReaderThread(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.in = socket.getInputStream();
	}


	@Override
	public void interrupt() {
		try {
			//关闭socket，与后面的try-catch配合使用
			this.socket.close();
		}catch(Exception e) {
		}finally{
			super.interrupt();
		}
	}


	@Override
	public void run() {
		byte[] buf = new byte[1024];
		try {
			while(true) {
				int count = in.read(buf);//依赖socket.close异常跳出while
				if(count<0) {
					break;
				}else if(count>0) {
					processBuffer(buf,count);
				}
			}
		}catch(Exception e) {
			
		}
	}


	private void processBuffer(byte[] buf, int count) {
		
	}
	
}
