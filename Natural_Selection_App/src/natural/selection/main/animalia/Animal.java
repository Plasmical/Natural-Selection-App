package natural.selection.main.animalia;

public abstract class Animal {

	int speed, size, aggressionAmt, genWeeks, stealth, vision;
	
	public Animal(int speed, int size, int aggressionAmt, int generationWeeks, int stealth, int vision) {
		this.speed = speed;
		this.size = size;
		this.aggressionAmt = aggressionAmt;
		this.genWeeks = generationWeeks;
		this.stealth = stealth;
		this.vision = vision;
	}

	public abstract int calcChanceOfDeath(int visionPredator, int sizePredator, int speedPredator, int aggroPredator);
	
	public abstract int calcChanceOfFoodFound(int stealthPrey, int sizePrey);

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

	public int getAggressionAmt() {
		return aggressionAmt;
	}

	public void setAggressionAmt(int aggressionAmt) {
		this.aggressionAmt = aggressionAmt;
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
}