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
			OurAnimal animal = new OurAnimal(random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), 3, random.nextInt(10));
			allOurAnimals.add(animal);
			allAnimals.add(animal);
		}
		for(int i=0; i<(180+random.nextInt(10)); i++) {
			Prey animal = new Prey(random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), 1, random.nextInt(10));
			allPreyAnimals.add(animal);
			allAnimals.add(animal);
		}
		for(int i=0; i<(50+random.nextInt(10)); i++) {
			Predator animal = new Predator(random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10), 4, random.nextInt(10));
			allPredatorAnimals.add(animal);
			allAnimals.add(animal);
		}
		animalAmt[0] = allAnimals.size();
		preyAnimalAmt[0] = allPreyAnimals.size();
		ourAnimalAmt[0] = allOurAnimals.size();
		predatorAnimalAmt[0] = allPredatorAnimals.size();
	}
	
	private void tick() {
		if(timeSinceLastWeek>=30) {
			for(int a=0; a<allOurAnimals.size(); a++) {
				for(int p=0; p<7; p++) { // Amount of times to simulate getting attacked
					int predator = random.nextInt(allPredatorAnimals.size());
					if(allOurAnimals.get(a).calcChanceOfDeath(allPredatorAnimals.get(predator).getVision(), allPredatorAnimals.get(predator).getSize(), 
							allPredatorAnimals.get(predator).getSpeed(), allPredatorAnimals.get(predator).getAggroAmt())>random.nextInt(100)) {
						allOurAnimals.remove(a);
					} else {
						if(allPredatorAnimals.get(predator).getHunger() <= 0) {
							allPredatorAnimals.remove(predator);
						} else {
							allPredatorAnimals.get(predator).setHunger(allPredatorAnimals.get(predator).getHunger()-15);
						}
					}
				}
				for(int p=0; p<14; p++) { // Amount of times to simulate finding food
					int prey = random.nextInt(allPreyAnimals.size());
					if(allOurAnimals.get(a).calcChanceOfFoodFound(allPreyAnimals.get(prey).getStealth(), allPreyAnimals.get(prey).getSize()) > random.nextInt(100)) {
						allPreyAnimals.remove(prey);
					} else {
						if(allOurAnimals.get(a).getHunger() <= 0) {
							allOurAnimals.remove(a);
						} else {
							allOurAnimals.get(a).setHunger(allOurAnimals.get(a).getHunger()-15);
						}
					}
				}
			}
			timeSinceLastWeek = 0;
		}
		
		if(secondsSinceLastGeneration1>=90) {
			// Create X new babies with certain traits where X is half of the population size
			for(int i=0; i<allOurAnimals.size()/2; i++) {
				OurAnimal parent1 = allOurAnimals.get(random.nextInt(allOurAnimals.size()));
				OurAnimal parent2 = allOurAnimals.get(random.nextInt(allOurAnimals.size()));
				int newSpeed, newSize, newAggro, newStealth, newVision;
				if(random.nextBoolean()) {
					newSpeed = parent1.getSpeed() + ((parent1.getSpeed()+parent2.getSpeed())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newSpeed = parent2.getSpeed() + ((parent1.getSpeed()+parent2.getSpeed())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newSize = parent1.getSize() + ((parent1.getSize()+parent2.getSize())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newSize = parent2.getSize() + ((parent1.getSize()+parent2.getSize())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newAggro = parent1.getAggressionAmt() + ((parent1.getAggressionAmt()+parent2.getAggressionAmt())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newAggro = parent2.getAggressionAmt() + ((parent1.getAggressionAmt()+parent2.getAggressionAmt())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newStealth = parent1.getStealth() + ((parent1.getStealth()+parent2.getStealth())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newStealth = parent2.getStealth() + ((parent1.getStealth()+parent2.getStealth())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newVision = parent1.getVision() + ((parent1.getVision()+parent2.getVision())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newVision = parent2.getVision() + ((parent1.getVision()+parent2.getVision())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(newSpeed>10) {
					newSpeed = 10;
				}
				if(newSize>10) {
					newSize = 10;
				}
				if(newAggro>10) {
					newAggro = 10;
				}
				if(newStealth>10) {
					newStealth = 10;
				}
				if(newVision>10) {
					newVision = 10;
				}
				OurAnimal newAnimal = new OurAnimal(newSpeed, newSize, newAggro, parent1.getGenWeeks(), newStealth, newVision);
				allOurAnimals.add(newAnimal);
			}
			secondsSinceLastGeneration1 = 0;
		}
		if(secondsSinceLastGeneration2>=120) {
			// Create X new babies with certain traits where X is half of the population size
			for(int i=0; i<allPredatorAnimals.size()/2; i++) {
				Predator parent1 = allPredatorAnimals.get(random.nextInt(allPredatorAnimals.size()));
				Predator parent2 = allPredatorAnimals.get(random.nextInt(allPredatorAnimals.size()));
				int newSpeed, newSize, newAggro, newStealth, newVision;
				if(random.nextBoolean()) {
					newSpeed = parent1.getSpeed() + ((parent1.getSpeed()+parent2.getSpeed())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newSpeed = parent2.getSpeed() + ((parent1.getSpeed()+parent2.getSpeed())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newSize = parent1.getSize() + ((parent1.getSize()+parent2.getSize())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newSize = parent2.getSize() + ((parent1.getSize()+parent2.getSize())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newAggro = parent1.getAggressionAmt() + ((parent1.getAggressionAmt()+parent2.getAggressionAmt())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newAggro = parent2.getAggressionAmt() + ((parent1.getAggressionAmt()+parent2.getAggressionAmt())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newStealth = parent1.getStealth() + ((parent1.getStealth()+parent2.getStealth())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newStealth = parent2.getStealth() + ((parent1.getStealth()+parent2.getStealth())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newVision = parent1.getVision() + ((parent1.getVision()+parent2.getVision())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newVision = parent2.getVision() + ((parent1.getVision()+parent2.getVision())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(newSpeed>10) {
					newSpeed = 10;
				}
				if(newSize>10) {
					newSize = 10;
				}
				if(newAggro>10) {
					newAggro = 10;
				}
				if(newStealth>10) {
					newStealth = 10;
				}
				if(newVision>10) {
					newVision = 10;
				}
				Predator newAnimal = new Predator(newSpeed, newSize, newAggro, parent1.getGenWeeks(), newStealth, newVision);
				allPredatorAnimals.add(newAnimal);
			}
			secondsSinceLastGeneration2 = 0;
		}
		if(secondsSinceLastGeneration3>=30) {
			// Create X new babies with certain traits where X is half of the population size
			for(int i=0; i<allPreyAnimals.size()/2; i++) {
				Prey parent1 = allPreyAnimals.get(random.nextInt(allPreyAnimals.size()));
				Prey parent2 = allPreyAnimals.get(random.nextInt(allPreyAnimals.size()));
				int newSpeed, newSize, newAggro, newStealth, newVision;
				if(random.nextBoolean()) {
					newSpeed = parent1.getSpeed() + ((parent1.getSpeed()+parent2.getSpeed())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newSpeed = parent2.getSpeed() + ((parent1.getSpeed()+parent2.getSpeed())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newSize = parent1.getSize() + ((parent1.getSize()+parent2.getSize())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newSize = parent2.getSize() + ((parent1.getSize()+parent2.getSize())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newAggro = parent1.getAggressionAmt() + ((parent1.getAggressionAmt()+parent2.getAggressionAmt())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newAggro = parent2.getAggressionAmt() + ((parent1.getAggressionAmt()+parent2.getAggressionAmt())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newStealth = parent1.getStealth() + ((parent1.getStealth()+parent2.getStealth())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newStealth = parent2.getStealth() + ((parent1.getStealth()+parent2.getStealth())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(random.nextBoolean()) {
					newVision = parent1.getVision() + ((parent1.getVision()+parent2.getVision())/2) - random.nextInt(2) + random.nextInt(2);
				} else {
					newVision = parent2.getVision() + ((parent1.getVision()+parent2.getVision())/2) - random.nextInt(2) + random.nextInt(2);
				}
				if(newSpeed>10) {
					newSpeed = 10;
				}
				if(newSize>10) {
					newSize = 10;
				}
				if(newAggro>10) {
					newAggro = 10;
				}
				if(newStealth>10) {
					newStealth = 10;
				}
				if(newVision>10) {
					newVision = 10;
				}
				Prey newAnimal = new Prey(newSpeed, newSize, newAggro, parent1.getGenWeeks(), newStealth, newVision);
				allPreyAnimals.add(newAnimal);
			}
			secondsSinceLastGeneration3 = 0;
		}
	}
	
	private int secondsSinceLastGeneration1 = 0, secondsSinceLastGeneration2 = 0, secondsSinceLastGeneration3 = 0; // The amount of seconds since the app started.
	private int timeSinceLastWeek = 0; // The app will simulate a week every 30 seconds.
	private int weekNumber = 0; // The amount of weeks passed.
	
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
		
		g.drawString("Amount of our animals alive: " + allOurAnimals.size(), 20, 180);
		g.drawString("Amount of our prey alive: " + allPreyAnimals.size(), 20, 200);
		g.drawString("Amount of our predators alive: " + allPredatorAnimals.size(), 20, 220);
		
		g.drawString("Time until next week: " + (30-timeSinceLastWeek), 20, 400);
		
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
		
		g.setColor(Color.black);
		
		g.drawString("Week " + weekNumber, 20, 80);
		
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
				secondsSinceLastGeneration1++;
				secondsSinceLastGeneration2++;
				secondsSinceLastGeneration3++;
				timeSinceLastWeek++;
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