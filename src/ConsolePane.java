

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ConsolePane extends JScrollPane {
	private static final long serialVersionUID = 3L;
	private JTextArea textArea ;
	private String save;
	public ConsolePane() {
		super() ;
		textArea = new JTextArea() ;
		textArea.setEditable(false) ;
		textArea.setFocusable(false) ;
	//	textArea.setText("Console:\n") ;
		this.setViewportView(textArea) ;
	}
	public void println(String message) {
		if (textArea.getText().equals(new String(""))) {
			print(message);
		} else {
			textArea.append("\n" + message) ;
			// Positionne la scrollPane à son extrémité inférieure.
			JScrollBar vertical = this.getVerticalScrollBar() ;
			vertical.setValue(vertical.getMaximum()) ;
		}
	}
	
	public void printEquipe(boolean equipe) {
		if (equipe) {
			textArea.append("equipe 1");
		} else {
			textArea.append("equipe 2");
		}
	}
	
	public void print(String message) {
		textArea.append(message) ;
		JScrollBar vertical = this.getVerticalScrollBar() ;
		vertical.setValue(vertical.getMaximum()) ;
	}
	
	public void clear() {
		textArea.setText("");
	}
	
	public void save() {
		save = textArea.getText();
	}
	
	public void recover() {
		if (save != null) {
			println(save);
		}
	}
	
	public void clearSave() {
		save = "";
	}
}
