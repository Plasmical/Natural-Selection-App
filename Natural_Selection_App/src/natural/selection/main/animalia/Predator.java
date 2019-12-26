package natural.selection.main.animalia;

public class Predator extends Animal {

	private int speed, size, aggroAmt, genWeeks, stealth, vision;
	
	public Predator(int speed, int size, int aggressionAmt, int generationWeeks, int stealth, int vision) {
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
		return 20;
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
}