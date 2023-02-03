import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {


		File inputFile = new File(args[0]);
		
		File output_AVL = new File(args[1] + "_avl.txt");
		File output_BST = new File(args[1] + "_bst.txt");
		PrintStream outputStream_avl;
		PrintStream outputStream_bst;

		try {
			outputStream_avl = new PrintStream(output_AVL);
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
			return;
		}
		
		try {
			outputStream_bst = new PrintStream(output_BST);
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
			return;
		}
		
		Scanner sc;
		try {
			sc = new Scanner(inputFile);

		}catch(FileNotFoundException ex) {
			ex.printStackTrace(); 
			return;
		}
		
		MyBST BSTTree = new MyBST();
		MyAVL AVLTree = new MyAVL();
		
		String root_ip = sc.nextLine();
		Node root = new Node(root_ip, null, null);
		BSTTree.root = root;
		Node avlroot = new Node(root_ip, null, null);
		AVLTree.root = avlroot;
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] splitLine = line.split(" ");
			
			
			if(splitLine[0].equals("ADDNODE")) {
				BSTTree.addNode(splitLine[1],BSTTree.root);
				AVLTree.addNode(splitLine[1],AVLTree.root);
				
			}
			
			else if(splitLine[0].equals("DELETE")) {
				BSTTree.delete(splitLine[1], BSTTree.root, null);
				AVLTree.delete(splitLine[1],AVLTree.root, null);
			}
			else if(splitLine[0].equals("SEND")) {
				BSTTree.send(splitLine[1], splitLine[2]);
				AVLTree.send(splitLine[1], splitLine[2]);
			}
			
			
		
		
		}
		
		outputStream_bst.print(BSTTree.s);
		outputStream_avl.print(AVLTree.s);
		sc.close();
		outputStream_avl.close();
		outputStream_bst.close();
	
	}
	
	
	

}
