package tp_image;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * @author François Barthélemy
 * Classe permettant de lire des fichiers contenant des images.
 */
public class ImageUtil {
	/** Lit une image au format JPEG ou au format PNG.
	 * 
	 * @param path chemin d'accès au fichier contenant l'image
	 * @return renvoie l'image sous forme d'un tableau à trois dimensions: 
	 *   colonne, ligne et composante de couleur ou d'opacité.
	 * @throws IOException 
	 */
	public static Pixel[][] lireFichier(String path){
		String typeFichier = path.substring(path.length()-3).toLowerCase();
		if (typeFichier.equals("ppm")) {
			return PPM.lireFichier(path);
		}else {
			Pixel [][] res;
			int[] tab;
			try {
				BufferedImage bufi = ImageIO.read(new File(path));
				int width = bufi.getWidth();
				int height = bufi.getHeight();
				tab = bufi.getRGB(0,0,width,height,null,0,width);
				res = new Pixel [width][height];
				for (int i=0; i<tab.length; i++){
					res[i%width][i/width] = explodePixel(tab[i]);
				}
				return res;
			}catch (IOException ioe) {
				throw new IllegalArgumentException(ioe.getMessage());
			}
		}
	}

	public static void afficheImage(Pixel[][] tab){
		new Afficheur(tab);
	}

	public static void ecrireFichier(String nomFich,Pixel[][] picture){
		String typeFichier = nomFich.substring(nomFich.length()-3).toLowerCase();
		if (typeFichier.equals("ppm")) {
			PPM.ecrireFichier(nomFich, picture);
		}else {
			try {
				BufferedImage bufi = new BufferedImage(picture.length,picture[0].length, BufferedImage.TYPE_INT_ARGB);
				int[] tabbis = packedFromNatural(picture);
				bufi.setRGB(0,0,picture.length,picture[0].length,tabbis,0,picture.length);
				ImageIO.write(bufi, typeFichier, new File(nomFich));
			}catch(IOException ioe) {
				throw new IllegalArgumentException(ioe.getMessage());
			}
		}
	}

	public static void main(String[] args){
		int[][][] tabInt = {
				{{255,0,0,255},{255,0,0,255},{255,0,0,255},{255,0,0,255}},
				{{255,0,0,255},{255,0,0,255},{255,0,0,255},{255,0,0,255}},
				{{255,0,0,255},{255,0,0,255},{255,0,0,255},{255,0,0,255}},
				{{255,0,0,255},{255,0,0,255},{255,0,0,255},{255,0,0,255}},
				{{255,0,0,255},{255,0,0,255},{255,0,0,255},{255,0,0,255}},
				{{255,0,0,255},{255,0,0,255},{255,0,0,255},{255,0,0,255}},
				{{0,0,255,255},{0,0,255,255},{0,0,255,255},{0,0,255,255}},
				{{0,0,255,255},{0,0,255,255},{0,0,255,255},{0,0,255,255}},
				{{0,0,255,255},{0,0,255,255},{0,0,255,255},{0,0,255,255}},
				{{0,0,255,255},{0,0,255,255},{0,0,255,255},{0,0,255,255}}};

		Pixel[][] perruches = lireFichier("src/images/perruche.png");
//		afficheImage(tab);
		afficheImage(perruches);
		ecrireFichier("src/images/xxxx_bis.png",perruches);
		perruches = lireFichier("src/images/xxxx_bis.png");
		afficheImage(perruches);
	}
	private static int[] packedFromNatural(Pixel [][] tab){
		int[] res = new int[tab.length*tab[0].length];
		for (int col = 0; col<tab.length; col++){
			for (int lig=0; lig<tab[0].length; lig++){
				res[lig*tab.length+col] = packedFromArray(tab[col][lig]);
			}
		}
		return res;
	}

	private static int packedFromArray(Pixel pix){
		int res = pix.getAlpha();
		res = (((res<<8) + pix.getRouge()<< 8) + pix.getVert());
		return (res<<8) + pix.getBleu();
//		int res = pix[3];
//		res = (((res<<8) + pix[0]<< 8) + pix[1]);
//		return (res<<8) + pix[2];
	}

	private static Pixel explodePixel(int pix){
		Pixel pt = new Pixel();
		pt.setAlpha(pix >> 24 & 0x000000FF);
		pt.setRouge(pix >> 16 & 0x000000FF);
		pt.setVert(pix >> 8 & 0x000000FF);
		pt.setBleu( pix & 0x000000FF);
//		pt[3]= pix >> 24 & 0x000000FF;
//			pt[0]= pix >> 16 & 0x000000FF;
//				pt[1]= pix >> 8 & 0x000000FF;
//				pt[2]= pix & 0x000000FF;
		return pt;
	}	 
}

