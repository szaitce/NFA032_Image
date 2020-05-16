package tp_image;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.image.*;
import java.awt.*;



/**
 * @author François Barthélemy
 * 
 * Classe dont les instances sont des fenêtres affichées à l'écran
 * contenant une image.
 *
 */
public class Afficheur extends JFrame{

	private static final long serialVersionUID = 1L;
	private int[] tab;
	private JPanel jp;

	/**
	 * @param tab2 l'image à afficher
	 */
	public Afficheur(Pixel[][] tab2){
		int width, height;
		width = tab2.length;
		height = tab2[0].length;
		BufferedImage bim = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		int[] pixtab = arrayFromPix(tab2);
		tab = ( (DataBufferInt) bim.getRaster()
				.getDataBuffer() ).getData();
		System.arraycopy(pixtab, 0, tab, 0, pixtab.length);
		jp = new ImagePanel(bim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp.setPreferredSize(new Dimension(bim.getWidth(),bim.getHeight()));
		this.add(jp);
		this.pack();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		/*JFrame jframe = this;
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				  System.out.println("closing");
			    jframe.dispose();
			  }
			});*/
		
		this.setVisible(true);
	}
	public void closeJFrame () {
		this.setVisible(false);
		this.dispose();
	}
	/**
	 * Méthode servant à afficher une nouvelle image dans la feneêtre.
	 * 
	 * Cette image doit avoir les mêmes dimensions que celle donnée en 
	 * paramètre au constructeur lors de la création de l'objet sur lequel
	 * la méthode est appelée.
	 * @param pixels l'image à afficher
	 * @throws IndexOutOfBoundsException parfois levée si pixels n'a pas
	 * la même dimension que l'image précédemment affichée par l'objet.
	 */
	public void update(Pixel [][] pixels){
		int[] pixtab = arrayFromPix(pixels);
		System.arraycopy(pixtab, 0, tab, 0, pixtab.length);
		jp.revalidate();
		jp.repaint();
	}
	private int[] arrayFromPix(Pixel[][] tab2){
		int[] res = new int[tab2.length*tab2[0].length];
		for (int col = 0; col< tab2.length; col++){
			for (int lig=0; lig<tab2[0].length; lig++){
				res[tab2.length*lig+col] = intFromPixel(tab2[col][lig]);
			}
		}
		return res;
	}
	private int intFromPixel(Pixel tab2){
		int res = tab2.getAlpha();
		res = (((res<<8) + tab2.getRouge()<< 8) + tab2.getVert());
		return (res<<8) + tab2.getBleu();
	}

	/** Méthode qui ferme la fenêtre.
	 * 
	 */
	//public void fermer(){
	//	this.dispose();
	//}
	class ImagePanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;
		public ImagePanel(BufferedImage i) {
			image=i;
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null); 
		}

	}

}
