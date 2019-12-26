package natural.selection.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import natural.selection.main.animalia.Animal;
import natural.selection.main.animalia.OurAnimal;
import natural.selection.main.animalia.Predator;
import natural.selection.main.animalia.Prey;

public class MainApp implements Runnable {

	private Display display;
	private int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private Random random = new Random();
	
	private BufferStrategy bs;
	private Graphics g;
	
	private ArrayList<Animal> allAnimals = new ArrayList<>();
	private int[] animalAmt = new int[20];
	
	private ArrayList<Prey> allPreyAnimals = new ArrayList<>();
	private int[] preyAnimalAmt = new int[20];
	private ArrayList<OurAnimal> allOurAnimals = new ArrayList<>();
	private int[] ourAnimalAmt = new int[20];
	private ArrayList<Predator> allPredatorAnimals = new ArrayList<>();
	private int[] predatorAnimalAmt = new int[20];
	
	public MainApp(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init(){
		display = new Display(title, width, height);
		
		for(int i=0; i<90; i++) {
			OurAnimal animal = new OurAnimal(random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10));
			allOurAnimals.add(animal);
			allAnimals.add(animal);
		}
		for(int i=0; i<(80+random.nextInt(10)); i++) {
			Prey animal = new Prey(random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10));
			allPreyAnimals.add(animal);
			allAnimals.add(animal);
		}
		for(int i=0; i<(50+random.nextInt(10)); i++) {
			Predator animal = new Predator(random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10));
			allPredatorAnimals.add(animal);
			allAnimals.add(animal);
		}
		animalAmt[0] = allAnimals.size();
		preyAnimalAmt[0] = allPreyAnimals.size();
		ourAnimalAmt[0] = allOurAnimals.size();
		predatorAnimalAmt[0] = allPredatorAnimals.size();
	}
	
	private void tick() {
		
	}
	
	// Amount of weeks to simulate
	private int weeksToSim = 20;
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		g.setColor(Color.black);
		
		// LINE GRAPH OF SPECIES COUNT
		// Draw the graph
		for(int x=0; x<weeksToSim; x++) {
			g.drawLine(x*93+125, height-100, x*93+125, 0);
			g.drawString("Week " + (x+1), x*93+125-20, height-80);
		}
		for(int y=0; y<10; y++) {
			g.drawLine((125), (y+1)*93, width, (y+1)*93);
			g.drawString(Math.abs(y*10-90) + " alive", (80), (y+1)*93);
		}
		// Draw the line
		for(int i=0; i<(animalAmt.length-1); i++) {
			// predator animal line
			g.setColor(Color.red);
			g.drawLine(i*93+125, (Math.abs((predatorAnimalAmt[i]*10)-90)), (i+1)*93+125, (Math.abs((predatorAnimalAmt[i+1]*10)-90)));
			g.drawLine(100, height-45, 120, height-45);
			g.drawString("PREDATOR", 30, height-40);
			// prey animal line
			g.setColor(Color.green);
			g.drawLine(i*93+125, (Math.abs((preyAnimalAmt[i]*10)-90)), (i+1)*93+125, (Math.abs((preyAnimalAmt[i+1]*10)-90)));
			g.drawLine(100, height-60, 120, height-60);
			g.drawString("PREY", 30, height-55);
			// our animal's line
			g.setColor(Color.blue);
			g.drawLine(i*93+125, (Math.abs((ourAnimalAmt[i]*10)-90)), (i+1)*93+125, (Math.abs((ourAnimalAmt[i+1]*10)-90)));
			g.drawLine(100, height-75, 120, height-75);
			g.drawString("OUR ANIMAL", 30, height-70);
		}
		
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		init();
		
		int fps = 10000;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		Toolkit.getDefaultToolkit().sync();
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}