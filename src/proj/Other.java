package proj;

public abstract class Other {
	private boolean choice;
	private float value;
	public boolean getChoice(){
		return choice;
	}
	protected void setChoice(boolean choice){
		this.choice = choice;
	}
	public abstract void newChoice(Other o);
	public float getV(){
		return value;
	}
	public void setV(float v){
		this.value = v;
	}
	public void addV(float v){
		this.value+= v;
	}
	public abstract void reset();
}
