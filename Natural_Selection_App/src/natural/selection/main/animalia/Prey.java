package natural.selection.main.animalia;

import java.util.Random;

public class Prey extends Animal {

	private int speed, size, aggroAmt, genWeeks, stealth, vision;
	
	int hunger = 100;
	
	private Random random = new Random();
	
	public Prey(int speed, int size, int aggressionAmt, int generationWeeks, int stealth, int vision) {
		super(speed, size, aggressionAmt, generationWeeks, stealth, vision);
		this.speed = speed;
		this.size = size;
		this.aggroAmt = aggressionAmt;
		this.genWeeks = generationWeeks;
		this.stealth = stealth;
		this.vision = vision;
	}

	@Override
	public int calcChanceOfDeath(int visionPredator, int sizePredator, int speedPredator, int aggroPredator) {
		// ARE YOU SEEN?
		boolean seen = false;
		if(((10*visionPredator)-Math.abs(stealth-size))>random.nextInt(100)) {
			seen = true;
		}
		// IF SEEN, ARE YOU ATTACKED?
		boolean attacked = false;
		if(seen) {
			if((aggroPredator - size)>2) {
				attacked = true;
			} if((aggroPredator - size)>0 && (aggroPredator - size)<2) {
				attacked = random.nextBoolean();
			}
		}
		// IF ATTACKED, WHAT ARE YOUR CHANCES OF SURVIVAL?
		if(attacked) {
			// ARE YOU CAUGHT?
			if((((2*speed)-size) - ((2*speedPredator)-size))<0) {
				if((size - sizePredator)<0) {
					return 90;
				} else {
					return 10;
				}
			}
		}	
		return 10;
	}

	@Override
	public int calcChanceOfFoodFound(int stealthPrey, int sizePrey) {
		return 90;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getAggroAmt() {
		return aggroAmt;
	}

	public void setAggroAmt(int aggroAmt) {
		this.aggroAmt = aggroAmt;
	}

	public int getGenWeeks() {
		return genWeeks;
	}

	public void setGenWeeks(int genWeeks) {
		this.genWeeks = genWeeks;
	}

	public int getStealth() {
		return stealth;
	}

	public void setStealth(int stealth) {
		this.stealth = stealth;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
}