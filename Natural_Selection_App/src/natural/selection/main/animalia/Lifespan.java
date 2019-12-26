package natural.selection.main.animalia;

public enum Lifespan {

	// FORMAT: AnimalName# - # is the amount of weeks able to survive when finding food and not being attacked
	ourAnimal(10),
	predatorAnimal(8),
	preyAnimal(7),
	plant(15);
	
	private int lifespan;
	
	private Lifespan(int lifespan) {
		this.lifespan = lifespan;
	}

	public int getLifespan() {
		return lifespan;
	}

	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}
}