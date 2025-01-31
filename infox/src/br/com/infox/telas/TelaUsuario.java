package br.com.infox.telas;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.awt.Container;

public class TelaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public TelaUsuario(JDesktopPane desktopPane) {
		setTitle("Usuários");
	    setClosable(true); // Permite fechar o JInternalFrame
	    setIconifiable(true); // Permite minimizar
	    setMaximizable(true); // Permite maximizar
	    setResizable(false); // Permite redimensionamento
	    setSize(482, 518); // Define o tamanho do frame

	
	 // Centralizando no JDesktopPane
	    if (desktopPane != null) {
            int x = (desktopPane.getWidth() - getWidth()) / 2;
            int y = (desktopPane.getHeight() - getHeight()) / 2;
            setLocation(x, y);
        }
    
	}

}
