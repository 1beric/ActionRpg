package engine.animation;

public class BlankAnimation extends Animation {
	
	public BlankAnimation(Animatable rc, float length) {
		super(rc, length);
	}

	@Override
	public void process(float factor, Animatable rc) {}

}
