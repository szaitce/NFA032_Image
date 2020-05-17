package tp_image;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	static String workingDIR = System.getProperty("user.dir");
//	static int [][][] image;
	static Image i1 = new Image();
	static String fileImage;
	static ArrayList<Image> alImage = new ArrayList<Image>();

	public static int menu(Scanner scan, String[] menu) {

		int res = -1;
		do {
			for (String m : menu) {
				System.out.println(m);
			}
			System.out.print("votre choix: ");
			try {
				res = scan.nextInt();
				if (res < 0 || res > menu.length)
					System.out.println("Erreur: votre choix doit etre compris entre 1 et " + menu.length);
			} catch (Exception e) {
				System.out.println("Erreur: votre choix doit etre nombre entier");
			}
		} while (res < 0 || res > menu.length);
		return res;
	}

	public static int askNumber(Scanner scan, int min, int max, String text) {
		int res = 0;
		do {
			System.out.println(text);
			System.out.println("Entrez une nombre de " + min + " à " + max + " s.v.p.");
			System.out.print("votre choix: ");
			try {
				res = scan.nextInt();
				if (res < min || res > max)
					System.out.println("Erreur: votre choix doit etre compris entre " + min + " et " + max);
			} catch (Exception e) {
				System.out.println("Erreur: votre choix doit etre nombre entier");
			}
		} while (res < min || res > max);
		return res;
	}

	public static void changeName(String ad) {
		int ext = i1.getName().length() - 4; // filename sauf l'extension
		String fileImageNew = "";
		fileImageNew = fileImageNew + i1.getName().substring(0, ext) + ad + i1.getName().substring(ext);
		i1.setName(fileImageNew);
		System.out.println("Le nom d'image été changé à " + i1.getName());
	}

	public static Image activationImage() {
		String[] names = new String[alImage.size()];
		for (int i = 0; i < alImage.size(); i++) {
			names[i] = i+"- "+(alImage.get(i).getName());
		}
		Scanner scan = new Scanner(System.in);
		int choix = menu(scan, names);
		return alImage.get(choix);
	}

	public static void main(String[] args) {
		String[] menuPrincipal = { "1- ouvrir un fichier image", "2- afficher l'image", "3- modifier l'image",
				"4- sauver l'image dans un fichier", "5- activer l'image téléchargée", "0- quitter" };
		Scanner scan = new Scanner(System.in);
		int choix;
		do {
			System.out.println("*******************************************");
			System.out.println("----------   Menu  principal   ------------");
			System.out.println("l'image active est " + i1.getName());
			System.out.println();
			choix = menu(scan, menuPrincipal);
			if (choix == 1) {
				String[] listImages = Image.voirDIR(workingDIR + "/src/images");
				int choixImage = menu(scan, listImages);
				fileImage = Image.nameFileFromMenu(choixImage - 1, listImages[choixImage - 1]);
				i1 = Image.fromFile(workingDIR + "/src/images/", fileImage);
				i1.setName(fileImage);
				i1.setBW(false);
				alImage.add(i1);
				System.out.println("l'image " + i1.getName() + " est ouverte");

			}
			if (choix == 2) {
				if (!i1.isNull()) {
					i1.afficherImage();
				} else
					System.out.println("Aucune image n'est pas en memoire. Il faut ouvrir un fichier image");

			}
			if (choix == 3) {
				if (!i1.isNull()) {
					modificationImage();
				} else
					System.out.println("Aucune image n'est pas en memoire. Il faut ouvrir un fichier image");

			}
			if (choix == 4) {
				if (!i1.isNull()) {
					i1.toFile(workingDIR + "/src/images/");
					System.out.println("l'image " + i1.getName() + " est sauvegardée");
				} else
					System.out.println("Aucune image n'est pas en memoire. Il faut ouvrir un fichier image");
			}
			if (choix == 5) {
				i1=activationImage();
			}

			if (choix == 0) {
				System.out.println("Merci, à bien tôt");

			}

		} while (choix != 0);
	}

	/////////////////////////////
	/////////////////////////////
	/////////////////////////////

	public static void modificationImage() {
		String[] menuModif = { "1- afficher l'image", "2- transformer l'image en noir et blanc",
				"3- deformation horizontale", "4- deformation verticale", "5- deformation proportionelle",
				"6- incruster image sur une autre image de disque dûr",
				"7- incruster image sur une autre image téléchargée",
				"8- utiliser cette image comme le fon pour incruster une autre image de disque dûr",
				"9- utiliser cette image comme le fon pour incruster une autre image téléchargé",
				"0- revenir au menu principal" };
		Scanner scan = new Scanner(System.in);
		int choix;
		do {
			System.out.println("*********************************************");
			System.out.println("Modification d'image " + i1.getName());
			choix = menu(scan, menuModif);
			if (choix == 1) {
				i1.afficherImage();
			}
			if (choix == 2) {
				i1.toGrayScale();
				System.out.println("l'image " + i1.getName() + " est transformée.");
				changeName("_bw");
				alImage.add(i1);
			}
			if (choix == 3) {
				int scaleInt = askNumber(scan, 0, 500, "Entrez l'échele de transformation (en pourcentage)");
				double scale = scaleInt;
				scale = scale / 100;
				i1 = i1.scale(scale, 1);
				System.out.println("l'image " + i1.getName() + " est transphormée.");
				changeName("_hor");
				alImage.add(i1);
			}
			if (choix == 4) {
				int scaleInt = askNumber(scan, 0, 500, "Entrez l'échele de transformation (en pourcentage)");
				double scale = scaleInt;
				scale = scale / 100;
				i1 = i1.scale(1, scale);
				System.out.println("l'image " + i1.getName() + " est transformée.");
				changeName("_ver");
				alImage.add(i1);
			}
			if (choix == 5) {
				int scaleInt = askNumber(scan, 0, 500, "Entrez l'échele de transformation (en pourcentage)");
				double scale = scaleInt;
				scale = scale / 100;
				i1 = i1.scale(scale, scale);
				System.out.println("l'image " + i1.getName() + " est transformée.");
				changeName("_resize");
				alImage.add(i1);
			}

			if (choix == 6) {
				System.out.println("Procedure d'incrustration " + i1.getName());
				String[] listImages = Image.voirDIR(workingDIR + "/src/images");
				int choixImage = menu(scan, listImages);
				String fileImage2 = Image.nameFileFromMenu(choixImage - 1, listImages[choixImage - 1]);
				Image i2 = new Image();
				i2 = Image.fromFile(workingDIR + "/src/images/",fileImage2);
				System.out.println("l'image " + fileImage2 + " est ouvert" + "\n");

				int posHoriz = askNumber(scan, 0, i2.getLargeur() - 1, "Entrez la position horisontal du bord gauche");
				int posVert = askNumber(scan, 0, i2.getHauteur() - 1, "Entrez la position vertical du bord haut");
				i2.incruster(i1, posHoriz, posVert);
				i1 = i2;
				System.out.println("l'image " + i1.getName() + " est transformée.");
				changeName("_incr");
				alImage.add(i1);
			}
			if (choix == 7) {
				System.out.println("Procedure d'incrustration " + i1.getName());
				Image i2 = new Image();
				i2 = activationImage();
				System.out.println("l'image " + i2.getName() + " est activé" + "\n");

				int posHoriz = askNumber(scan, 0, i2.getLargeur() - 1, "Entrez la position horisontal du bord gauche");
				int posVert = askNumber(scan, 0, i2.getHauteur() - 1, "Entrez la position vertical du bord haut");
				i2.incruster(i1, posHoriz, posVert);
				i1 = i2;
				System.out.println("l'image " + i1.getName() + " est transformée.");
				changeName("_incr");
				alImage.add(i1);
			}
			if (choix == 8) {
				System.out.println("Procedure d'incrustration sur " + i1.getName());
				String[] listImages = Image.voirDIR(workingDIR + "/src/images");
				int choixImage = menu(scan, listImages);
				String fileImage2 = Image.nameFileFromMenu(choixImage - 1, listImages[choixImage - 1]);
				Image i2 = new Image();
				i2 = Image.fromFile(workingDIR + "/src/images/", fileImage2);
				System.out.println("l'image " + fileImage2 + " est ouvert" + "\n");

				int posHoriz = askNumber(scan, 0, i1.getLargeur() - 1, "Entrez la position horisontal du bord gauche");
				int posVert = askNumber(scan, 0, i1.getHauteur() - 1, "Entrez la position vertical du bord haut");
				i1.incruster(i2, posHoriz, posVert);
				changeName("_incr");
				alImage.add(i1);
			}
			if (choix == 9) {
				System.out.println("Procedure d'incrustration sur " + i1.getName());
				Image i2 = new Image();
				i2 = activationImage();
				System.out.println("l'image " + i2.getName() + " est activé" + "\n");

				int posHoriz = askNumber(scan, 0, i1.getLargeur() - 1, "Entrez la position horisontal du bord gauche");
				int posVert = askNumber(scan, 0, i1.getHauteur() - 1, "Entrez la position vertical du bord haut");
				i1.incruster(i2, posHoriz, posVert);
				changeName("_incr");
				alImage.add(i1);
			}
		} while (choix != 0);
	}

}
