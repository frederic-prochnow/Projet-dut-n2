/**
 * Importation
 */
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Class ConsolePane
 * @author Team J3
 */
class ConsolePane extends JScrollPane {
	
	/**
	 * Attribut
	 */
	private static final long serialVersionUID = 3L;
	private JTextArea textArea ;
	private String save;
	
	/**
	 * Constructeur de la classe
	 */
	public ConsolePane() {
		super() ;
		textArea = new JTextArea() ;
		textArea.setEditable(false) ;
		textArea.setFocusable(false) ;
	//	textArea.setText("Console:\n") ;
		this.setViewportView(textArea) ;
	}
	
	/**
	 * Fonction d'affichage
	 * @param String message
	 */
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
	
	/**
	 * Fonction d'affichage de l'équipe
	 * @param booleen représentant l'équipe
	 */
	public void printEquipe(boolean equipe) {
		if (equipe) {
			textArea.append("equipe 1");
		} else {
			textArea.append("equipe 2");
		}
	}
	
	/**
	 * Fonction d'affichage
	 * @param String message
	 */
	public void print(String message) {
		textArea.append(message) ;
		JScrollBar vertical = this.getVerticalScrollBar() ;
		vertical.setValue(vertical.getMaximum()) ;
	}
	
	/**
	 * Nettoyage
	 */
	public void clear() {
		textArea.setText("");
	}
	
	/**
	 * Sauvegarde
	 */
	public void save() {
		save = textArea.getText();
	}
	
	/**
	 * Restauration
	 */
	public void recover() {
		if (save != null) {
			println(save);
		}
	}
	
	/**
	 * Nettoyage de la sauvegarde
	 */
	public void clearSave() {
		save = "";
	}
}
