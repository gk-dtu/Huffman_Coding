import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

class Node{
   char ch;
   int  data;
   Node left, right;
   Node(char ch, int data){
	   this.ch=ch;
	   this.data=data;
	   left=null; 
	   right=null;
   }
}

public class HuffmanCodingClass {
	
	   private Map<Character, Integer> freq;
	   private Map<Character, String> huffmanEncode;
	   private char[] message;
	   private Node root;
	   private PriorityQueue<Node>pq;
	   private int treeSize=0;
	   private int messageLength=0;
	   
	   public HuffmanCodingClass(char[] message) {
		   freq=new LinkedHashMap<>();
		   huffmanEncode=new HashMap<>();
		   this.message=message.clone();
		   root=null;
		   
		   for(char c:message) {
			   freq.put(c, freq.getOrDefault(c, 0)+1);      // adding char to map
		   }
		   
		   pq=new PriorityQueue<>((a,b)-> ((a.data == b.data) ? a.ch-b.ch: a.data-b.data ));
		   
		
	   }
	
       public void printMessageDetails() {
    	   System.out.println("\ncharacters \t frequency ");
    	   for(Entry<Character, Integer> map: freq.entrySet()) {
    		   System.out.println("   "+ map.getKey()+"  \t\t  "+ map.getValue());
    	   }
    	   
    	   System.out.println("Length of the message: "+ message.length);
    	   System.out.println("Using normal Compression, size of the message(in bits): "+ 8*message.length);
       }
       
       public void huffmanCompressor() {
    	   System.out.println("\n\n    ------Using Huffman Compression Technique------ \n");
    	   System.out.println("characters \t Code ");
    	   generateHuffmanTree();
    	  huffmanCompressor(root, "");
       }
       
       private void huffmanCompressor(Node root, String str) {
		   if(root.left==null && root.right==null) {
			   System.out.println("   "+root.ch +"  \t\t  "+ str);
			   huffmanEncode.put(root.ch, str);
			   treeSize+=8+str.length();
			   messageLength+=(freq.get(root.ch)*str.length());
			   return;
		   }
    	 
		   huffmanCompressor(root.left, str+"0"); 
		   huffmanCompressor(root.right, str+"1");
		   
	   }
       
       public StringBuffer encoding() {
    	   StringBuffer sb=new StringBuffer();
    	  for(char c: message) {
    		  sb.append(huffmanEncode.get(c));
    	  }
    	  System.out.println("\nOriginal message is: "+ String.valueOf(message));
    	  System.out.println("\nHuffman Encoded message is: "+ sb);
    	  return sb;
       }
       
       public void decoding(StringBuffer sb) {
    	   StringBuffer decode=new StringBuffer();
    	    if(root.left==null && root.right==null) {
    	    	while(root.data-->0) {
    	    		decode.append(root.ch);
    	    	}
    	    }
    	    else {
    	    	int ind=-1;
    	    	while(ind< sb.length()-1) {
    	    		ind= decoding(root, ind, sb, decode);
    	    	}
    	    }
    	    System.out.println("\nHuffman Decoded message is :" + decode);
       }
       
	  private int decoding(Node root, int ind, StringBuffer sb, StringBuffer decode) {
		   if(root==null) return ind;
		   if(root.left==null && root.right==null) {
			   decode.append(root.ch);
			   return ind;
		   }
		   ind++;
		   root = sb.charAt(ind)=='0' ? root.left : root.right;
		   return ind = decoding(root, ind, sb, decode);
	  }

	private void generateHuffmanTree() {
    	   
		  for(Entry<Character, Integer>map : freq.entrySet()) {
			   Node node=new Node(map.getKey(),map.getValue());           //creating node
			   pq.add(node);                                             // adding node in PriorityQueue;
		   }
		  
		  
    	   while(pq.size()>1) {
    		   Node minNode1=pq.poll();
    		   Node minNode2=pq.poll();
    		   
    		   int newRootData = minNode1.data + minNode2.data;
    		   Node newRoot=new Node(' ', newRootData);
    		   newRoot.left=minNode1;
    		   newRoot.right=minNode2;
    		   
    		   root=newRoot;
    		   pq.add(newRoot);
    	   }
       }
       
	  public void CompressionDetails() {
		  System.out.println("\ncompressed message length(in bits): "+ messageLength);
		  System.out.println("Size of the tree pattern for encoding and decoding(in bits) : "+ treeSize);
		  int total= messageLength+treeSize;
		  System.out.println("Total size(in bits): "+ total);
		  int OriginalSize=8*message.length;
		  double reduction= (double)(OriginalSize -messageLength)/OriginalSize; 
		  System.out.println("Data(message) Compression Achieved by Huffman Coding: "+ String.format("%.2f",reduction*100) + "%");
		  
		  reduction= (double)(OriginalSize -total)/OriginalSize;
		  System.out.println("Overall(message + TreeEncoded Table) Compression Achieved by Huffman Coding: "+ String.format("%.2f",reduction*100) + "%");
		  
		  System.out.println("--------------------End------------------");
	  }
       
}
