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
		this.filename ="new.png";
		this.bw = false;
	}
	public Image () {
		Pixel [][] pixel= new Pixel [0][0];
		this.pixel=pixel;
		this.hauteur = 0;
		this.largeur = 0;
		this.filename ="new.png";
		this.bw = false;
	}

	public int getLargeur() {
		this.largeur = this.pixel[0].length;
		return this.largeur;
	}

	public int getHauteur() {
		this.hauteur = this.pixel.length;
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
	public boolean getBW() {
		return this.bw;	
	}
	public void setBW(boolean b) {
		this.bw=b;	
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

	public void toFile(String path) {
		ImageUtil.ecrireFichier(path+this.filename, this.pixel);
	}

	public static Image fromFile(String path, String file) {
		Image res = new Image(ImageUtil.lireFichier(path+file));
		res.setName(file);
		res.largeur=res.pixel[0].length;
		res.hauteur=res.pixel.length;
		res.setBW(false);
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
	public void incruster(Image adImage, int shiftLargFon, int shiftHautFon) {
		int larg = adImage.getLargeur();				// determinaison les bords des images
		if (shiftLargFon<0) shiftLargFon=0;				// pour ne pas depasser aux limites des Tabs 
		if (shiftLargFon+larg >= this.largeur) {
			larg=this.largeur-shiftLargFon;
		}
		int haut = adImage.getHauteur();
		if (shiftHautFon<0) shiftHautFon=0;				
		if (shiftHautFon+haut >= this.hauteur) {
			haut=this.hauteur-shiftHautFon;
		}
		System.out.println("Fon     hauteur:"+this.hauteur+", largeur: "+this.largeur);
		System.out.println("AdImage hauteur:"+adImage.hauteur+", largeur: "+adImage.largeur);
		System.out.println("shift   hauteur:"+shiftHautFon+",  largeur: "+shiftLargFon);
		System.out.println("Cadre   hauteur:"+(haut)+", largeur: "+(larg));
		System.out.println("Limites hauteur:"+(shiftHautFon+haut)+", largeur: "+(shiftLargFon+larg));
		
		for (int i = 0; i < haut; i++) {
			for (int j = 0; j < larg; j++) {
				if (adImage.pixel[i][j].getAlpha() == 255) {
					if (i>=adImage.hauteur || j>=adImage.largeur ||
							shiftHautFon+i>=this.hauteur || shiftLargFon+j>=this.largeur) {
						System.out.println("i:"+i+", j:"+j);
						System.out.println("shiftHautFon+i:"+(shiftHautFon+i)+"   shiftLargFon+j: "+(shiftLargFon+j));
						System.out.println("fon pixel: "+this.pixel[shiftHautFon+i][shiftLargFon+j].toString());
						System.out.println("ad pixel: "+adImage.pixel[i][j].toString());
					}
					this.pixel[shiftHautFon+i][shiftLargFon+j].summaryPixel(adImage.pixel[i][j]);
				}
			}
		}
		if (!adImage.bw) { 				// si l'image incrusté est en couleur
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
		in.setBW(this.bw);
		in.setName(this.getName());
		return in;
	}
}
