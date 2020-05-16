package tp_image;

public class Pixel {

	private int [] px;
	private int rouge;
	private int vert;
	private int bleu;
	private int alpha;

	
	public Pixel (int [] px) {
		this.rouge = px[0];
		this.vert = px[1];
		this.bleu = px[2];
		this.alpha = px[3];
	}
	public Pixel () {
		int [] px = new int [4];
		this.px=px;
	}
//	public Pixel(int rouge, int vert, int bleu, int alpha) {
//		this.rouge = rouge;
//		this.vert = vert;
//		this.bleu = bleu;
//		this.alpha = alpha;
//	}
	@Override
	public String toString() {
		return "Pixel [rouge=" + this.rouge + 
				", vert=" + this.vert + 
				", bleu=" + this.bleu + 
				", alpha=" + this.alpha + "]";
	}
	public int getRouge() {
		return rouge;
	}
	public void setRouge(int rouge) {
		this.rouge = rouge;
	}
	public int getVert() {
		return vert;
	}
	public void setVert(int vert) {
		this.vert = vert;
	}
	public int getBleu() {
		return bleu;
	}
	public void setBleu(int bleu) {
		this.bleu = bleu;
	}
	public int getAlpha() {
		return alpha;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int [] getTabPx() {
		return this.px;
	}
	public void toGray () {
		this.rouge=(int) (this.rouge*0.33+this.vert*0.33+this.bleu*0.33);
		this.vert=this.rouge;
		this.bleu=this.vert;
	}
	public void summaryPixel(Pixel px) {
		this.setRouge(px.getRouge());
		this.setVert(px.getVert());
		this.setBleu(px.getBleu());
//		this.setAlpha(px.getAlpha());
	}
	
	
	
	
	public static void main(String [] args) {
		int [] px1= new int [] {255,0,0,255};
		int [] px2= new int [] {0,0,255,255};
		Pixel p1 = new Pixel(px1);
		Pixel p2 = new Pixel(px2);
		System.out.println ("Pixel 1: "+p1.toString());
		System.out.println ("Pixel 2: "+p2.toString());
		p1.setBleu(135);
		p1.setVert(135);
		p1.setRouge(135);
		p2.setBleu(0);
		p2.setVert(255);
		p2.setRouge(0);
		System.out.println ("Pixel 1: "+p1.toString());
		System.out.println ("Pixel 2: "+p2.toString());
		
	}
}
