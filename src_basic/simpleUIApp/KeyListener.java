package simpleUIApp;

import javax.swing.JFrame;

import fr.ubordeaux.simpleUI.KeyHandler;

public class KeyListener implements KeyHandler {

	private JFrame mFrame;

	public KeyListener(JFrame frame) {
		mFrame = frame;
	}

	@Override
	public JFrame getParentFrame() {
		return mFrame;
	}

	@Override
	public void keyPressed(char arg0) {

	}

	@Override
	public void keyReleased(char arg0) {

	}

	@Override
	public void keyTyped(char arg0) {
		switch (arg0) {
		case '+':
			System.out.println("+ has been typed");
			if(MouseListener.prct < 1) {
				MouseListener.prct += 0.1f;
			}else {
				System.out.println("limite max taille escadron atteinte");
			}
			break;
		case '-':
			System.out.println("- has been typed");
			if(MouseListener.prct > 0.1) {
				MouseListener.prct -= 0.1f;
			}else {
				System.out.println("limite min taille escadron atteinte");
			}
			break;
		default:
			// do nothing
			break;
		}
	}
	
	

}
