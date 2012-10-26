package ca.mitmaro.ldb.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import ca.mitmaro.ldb.gui.model.PaperModel;
import ca.mitmaro.ldb.gui.view.PaperDialog;

public abstract class PaperDialogEvents {
	
	private final PaperModel model;
	private final PaperDialog dialog;
	
	public PaperDialogEvents(PaperModel model, PaperDialog dialog) {
		this.model = model;
		this.dialog = dialog;
	}
	
	public abstract class InputKeyListener implements KeyListener {
		
		protected JTextField input;
		
		protected String name;
		
		public InputKeyListener(JTextField input, String name) {
			this.input = input;
			this.name = name;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			PaperDialogEvents.this.model.setPaperField(this.name, this.input.getText());
		}
	}
	
	public final AbstractAction close_handler =  new AbstractAction("Close") {
		private static final long serialVersionUID = -3443722540557588402L;
		@Override
		public void actionPerformed(ActionEvent e) {
			PaperDialog dialog= PaperDialogEvents.this.dialog;
			dialog.setVisible(false);
			dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
		}
	};
	
}
