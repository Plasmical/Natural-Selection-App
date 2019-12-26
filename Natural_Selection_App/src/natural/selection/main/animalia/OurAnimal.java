package natural.selection.main.animalia;

import java.util.Random;

public class OurAnimal extends Animal {

	int speed, size, aggresssionAmt, genWeeks, stealth, vision;
	int hunger = 100;
	
	private static final Random random = new Random();
	
	public OurAnimal(int speed, int size, int aggressionAmt, int generationWeeks, int stealth, int vision) {
		super(speed, size, aggressionAmt, generationWeeks, stealth, vision);
		this.speed = speed;
		this.size = size;
		this.aggressionAmt = aggressionAmt;
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
		return 0;
	}

	@Override
	public int calcChanceOfFoodFound(int stealthPrey, int sizePrey) {
		// CAN YOU FIND FOOD?
		if(((10*vision) - (Math.abs((10*stealthPrey) - (10*sizePrey)))) < 0) {
			return 5;
		} else if(((10*vision) - (Math.abs((10*stealthPrey) - (10*sizePrey)))) > 0) {
			// CAN YOU KILL THE FOOD?
			if((size - sizePrey) > 0) {
				return 90;
			} else if((size - sizePrey) < 0) {
				return 10;
			}
		}
		
		return 50;
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

	public int getAggresssionAmt() {
		return aggresssionAmt;
	}

	public void setAggresssionAmt(int aggresssionAmt) {
		this.aggresssionAmt = aggresssionAmt;
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

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
}