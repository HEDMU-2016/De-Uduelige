
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class Launcher extends Application implements Runnable  {
	boolean running;
	Thread thread;
	public static void main(String[] args)throws SQLException {
	launch(args);
	}

	public void start(Stage loginStage) throws Exception {
		Login_IO login_IO = new Login_IO();
		startrunning();
		login_IO.start(loginStage);
		
	}
	public synchronized void startrunning() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public void stop(){
		running=false;
	}
	@Override
	public void run() {
		long lasttime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = Math.pow(10, 9) / amountOfTicks;
		double delta = 0;
		long timer = System.nanoTime();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lasttime) / ns;
			lasttime = now;
			while (delta >=1) {
				delta--;
				
			}
			if (running)
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			stop();
		}
	}

}