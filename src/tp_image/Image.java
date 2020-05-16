package tp_image;

import java.io.File;
import java.util.ArrayList;

public class Image {

	private Pixel[][] pixel;
	private int largeur;
	private int hauteur;
	private String filename;
	private boolean bw;
	

	public Image(Pixel [][] pixel) {
		this.pixel = pixel;
		this.hauteur = pixel.length;
		this.largeur = pixel[0].length;
		this.filename ="";
		this.bw = false;
	}

	public Image(int hauteur, int largeur) {
		Pixel[][] pixel = new Pixel [hauteur][largeur];
		this.pixel=pixel;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.filename ="";
		this.bw = false;
	}
	public Image () {
		this.pixel=pixel;
		this.filename ="";
		this.bw = false;
	}

	public int getLargeur() {
		return this.largeur;
	}

	public int getHauteur() {
		return this.hauteur;
	}
	public Pixel getPixel(int hauteurPixel, int largeurPixel) {
		return this.pixel[hauteurPixel][largeurPixel];
	}
	public String getName () {
		return this.filename;
	}
	public void setName (String filename) {
		this.filename=filename;
	}
	public void setBW(boolean b) {
		this.bw=b;	
	}
	
	public boolean getBW() {
		return this.bw;	
	}
	
	public String toString() {
		String res = "Largeur: " + this.largeur + "hauteur: " + this.hauteur+", name: "+this.filename;
//		System.out.println(res);
		return res;
	}

	public boolean isNull() {
		if (this.pixel == null) {
			return true;
		}
		return false;
	}

	public void toFile() {
		ImageUtil.ecrireFichier(this.filename, this.pixel);
	}

	public static Image fromFile(String file) {
		Image res = new Image(ImageUtil.lireFichier(file));
		res.setName (file);
		return res;
	}

	public void afficherImage() {
			ImageUtil.afficheImage(this.pixel);
	}

	public static String[] recevoirListDir(String path) {
		File catalog = new File(path);
		String[] files = catalog.list();
		File file;
		ArrayList<String> fileAL = new ArrayList<String>();
		for (String ff : files) {
			file = new File(catalog, ff);
			if (!ff.isEmpty() || file.isFile() || !file.isHidden() || file.canRead()) {
				fileAL.add(ff);
			}
		}
		String[] res = new String[fileAL.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.toString(i + 1) + " - " + fileAL.get(i);
		}
		return res;
	}

	public static String[] voirDIR(String workingDIR) {
		String[] images;
		images = recevoirListDir(workingDIR);
		return images;
	}

	public static String nameFileFromMenu(int i, String str) {
		int index = str.indexOf(" - ");
		String res = str.substring(index + 3);
		return res;
	}

	public void toGrayScale() {
		int haut = this.hauteur;
		int larg = this.largeur;
		for (int i = 0; i < haut; i++) {
			for (int j = 0; j < larg; j++) {
//				System.out.println("i:"+i+", j:"+j);
				this.pixel[i][j].toGray();
			}
		}
		this.bw = true;
	}
	public void incruster(Image adImage, int shiftLargBI, int shiftHautBI) {
		if (shiftLargBI<0) shiftLargBI=0;				// determinaison les bords des images 
		if (shiftHautBI<0) shiftHautBI=0;				// pour ne pas depasser aux limites des Tabs
		int larg = adImage.getLargeur();
		int haut = adImage.getHauteur();
		if (shiftLargBI+larg >= this.largeur) {
			larg=this.largeur-shiftLargBI;
		}
		if (shiftHautBI+haut >= this.hauteur) {
			haut=this.hauteur-shiftHautBI;
		}
		for (int i = 0; i < haut; i++) {
			for (int j = 0; j < larg; j++) {
				if (adImage.pixel[i][j].getAlpha() == 255) {
//				System.out.println("i:"+i+", j:"+j);
					this.pixel[shiftLargBI+i][shiftHautBI+j].summaryPixel(adImage.pixel[i][j]);
				}
			}
		}
		if (!adImage.bw) { 				// si l'image incrusté est en couleur on change
			this.bw=false;				// on declare que le fon deviens en couleur				
			} 
	}

	public Image scale(double scaleH, double scaleL) {	
		int hautNew = (int) (this.hauteur*scaleH);
		int largNew = (int) (this.largeur*scaleL);
		Image in = new Image (hautNew, largNew);
		for (int i=0; i<hautNew; i++) {
			for (int j=0; j<largNew; j++){
				in.pixel [i][j] = this.pixel[(int)(i/scaleH)][(int)(j/scaleL)];
				}
			}
		
		return in;
	}
}
