import java.util.Scanner;

public class HuffmanMain {

	public static void main(String[] args) {
	    Scanner scan=new Scanner(System.in);
	    System.out.print("Type your message here... for compression: ");
	    char[] message = scan.nextLine().toCharArray();
	   
	     
	     HuffmanCodingClass obj=new HuffmanCodingClass(message);
	     obj.printMessageDetails();
	     obj.huffmanCompressor();
	     StringBuffer sb=obj.encoding();
	     obj.decoding(sb);
	     obj.CompressionDetails();
	     scan.close();
	}

}
