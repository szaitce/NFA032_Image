package tp_image;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	static String workingDIR = System.getProperty("user.dir");
//	static int [][][] image;
	static Image i1 = new Image();
	static String fileImage;

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
				if (res <= min || res >= max)
					System.out.println("Erreur: votre choix doit etre compris entre " + min + " et " + max);
			} catch (Exception e) {
				System.out.println("Erreur: votre choix doit etre nombre entier");
			}
		} while (res < 1 || res > max);
		return res;
	}

	public static void changeName(String ad) {
		int ext = i1.getName().length() - 4; // filename sauf l'extension
		String fileImageNew = "";
		fileImageNew = fileImageNew + i1.getName().substring(0, ext) + ad + i1.getName().substring(ext);
		i1.setName(fileImageNew);
		System.out.println("Le nom d'image été changé à " + i1.getName());
	}

	public static void main(String[] args) {
		ArrayList<Image> alImage = new ArrayList<Image>();
		String[] menuPrincipal = { "1- ouvrir un fichier image", "2- afficher l'image", "3- modifier l'image",
				"4- sauver l'image dans un fichier", "0- quitter" };
		Scanner scan = new Scanner(System.in);
		int choix;
		do {
			choix = menu(scan, menuPrincipal);
			if (choix == 1) {
				String[] listImages = Image.voirDIR(workingDIR + "/src/images");
				int choixImage = menu(scan, listImages);
				fileImage = Image.nameFileFromMenu(choixImage - 1, listImages[choixImage - 1]);
				i1 = Image.fromFile(workingDIR + "/src/images/" + fileImage);
				System.out.println("l'image " + fileImage + " est téléchargée");
				i1.setBW(false);

			}
			if (choix == 2) {
				if (!i1.isNull()) {
					i1.afficherImage();
				} else
					System.out.println("Aucune image n'est pas en memoire. Il faut télécharger une image");

			}
			if (choix == 3) {
				if (!i1.isNull()) {
					modificationImage();
				} else
					System.out.println("Aucune image n'est pas en memoire. Il faut télécharger une image");

			}
			if (choix == 4) {
				if (!i1.isNull()) {
					i1.toFile();
					System.out.println("l'image " + i1.getName() + " est enregistrée");
				} else
					System.out.println("Aucune image n'est pas en memoire. Il faut télécharger une image");
			}
			if (choix == 5) {

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
				"6- incruster cette image sur une autre image",
				"7- utiliser cette image comme le fon pour incruster une autre image", "0- revenir au menu principal" };
		Scanner scan = new Scanner(System.in);
		int choix;
		do {
			choix = menu(scan, menuModif);
			if (choix == 1) {
				i1.afficherImage();
			}
			if (choix == 2) {
				i1.toGrayScale();
				System.out.println("l'image " + i1.getName() + " est transphormée.");
				changeName ("_bw");
			}
			if (choix == 3) {
				int scaleInt = askNumber(scan, 0, 500, "Entrez l'échele de transformation (en pourcentage)");
				double scale = scaleInt;
				scale = scale / 100;
				i1 = i1.scale(scale, 1);
				System.out.println("l'image " + i1.getName() + " est transphormée.");
				changeName ("_hor");
			}
			if (choix == 4) {
				int scaleInt = askNumber(scan, 0, 500, "Entrez l'échele de transformation (en pourcentage)");
				double scale = scaleInt;
				scale = scale / 100;
				i1 = i1.scale(1, scale);
				System.out.println("l'image " + i1.getName() + " est transphormée.");
				changeName ("_ver");
			}
			if (choix == 5) {
				int scaleInt = askNumber(scan, 0, 500, "Entrez l'échele de transformation (en pourcentage)");
				double scale = scaleInt;
				scale = scale / 100;
				i1 = i1.scale(scale, scale);
				System.out.println("l'image " + i1.getName() + " est transphormée.");
				changeName ("_resize" + scaleInt);
			}

			if (choix == 6) {
				System.out.println("Procedure d'incrustration " + fileImage);
				String[] listImages = Image.voirDIR(workingDIR + "/src/images");
				int choixImage = menu(scan, listImages);
				String fileImage2 = Image.nameFileFromMenu(choixImage - 1, listImages[choixImage - 1]);
				Image i2 = new Image();
				i2 = Image.fromFile(workingDIR + "/src/images/" + fileImage2);
				System.out.println("l'image " + fileImage2 + " est téléchargée" + "\n");

				int posHoriz = askNumber(scan, 0, i2.getLargeur() - 1, "Entrez la position horisontal du bord gauche");
				int posVert = askNumber(scan, 0, i2.getHauteur() - 1, "Entrez la position vertical du bord haut");
				i2.incruster(i1, posHoriz, posVert);
				i1 = i2;
				System.out.println("l'image " + i1.getName() + " est transphormée.");
				changeName ("_incr");
			}
			if (choix == 7) {
				System.out.println("Procedure d'incrustration sur " + fileImage);
				String[] listImages = Image.voirDIR(workingDIR + "/src/images");
				int choixImage = menu(scan, listImages);
				String fileImage2 = Image.nameFileFromMenu(choixImage - 1, listImages[choixImage - 1]);
				Image i2 = new Image();
				i2 = Image.fromFile(workingDIR + "/src/images/" + fileImage2);
				System.out.println("l'image " + fileImage2 + " est téléchargée" + "\n");

				int posHoriz = askNumber(scan, 0, i1.getLargeur() - 1, "Entrez la position horisontal du bord gauche");
				int posVert = askNumber(scan, 0, i1.getHauteur() - 1, "Entrez la position vertical du bord haut");
				i1.incruster(i2, posHoriz, posVert);
				changeName ("_incr");
			}

		} while (choix != 0);
	}

}
