import java.util.ArrayList;
import java.util.Arrays;

public class MyBST {
	public Node root;
	public String s;
	
	
	public MyBST() {
		this.root = null;
		this.s="";
	}
	/**
	 * 
	 * @param ip
	 * @param n
	 * @return
	 */
	public Node addNode(String ip, Node n) {
		
		if( n == null ) {
            return new Node( ip, null, null);
		}
		
        int compareResult = ip.compareTo( n.key);
        
        if( compareResult < 0 ) {
        	s = s +n.getKey() +": New node being added with IP:" + ip +"\n";  
        	n.setLeft(addNode(ip, n.getLeft()));
        	  	
        }
          
        else if( compareResult > 0 ) {
        	s = s +n.getKey() +": New node being added with IP:" + ip +"\n";
        	n.setRight(addNode( ip, n.getRight()));
        	
        	
        }
        else;  // Duplicate; do nothing
        
        return n;
        
	}
	
    /**
     * it is finding the parent of the minimum
     * @param t
     * @return
     */
	public Node findMin(Node t){
		if(t == null || t.getLeft() == null)
			return null;
		
		else if(t.getLeft().getLeft() == null) 
			return t;
		
		return findMin(t.getLeft());	
	}
	public boolean LR(String ip, Node n) {
		
		int compareResult = ip.compareTo( n.key);
		if(compareResult < 0) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public Node delete(String ip, Node n, Node parent) {
		if(this.root.getKey().equals(ip)) {
			return null;
		}
		if(n == null) {
			return n;
		}

		int compareResult = ip.compareTo( n.key);
//		
		if(compareResult == 0) {
			//leaf node 
			if(n.getLeft() == null && n.getRight() == null) {
				s = s + parent.getKey() + ": Leaf Node Deleted: " + ip + "\n";
				if(LR(ip,parent)) {
					parent.setRight(null);
				}
				else {
					parent.setLeft(null);
				}
				return n = null;
			}
			// only left child 
			else if(n.getLeft() == null && n.getRight() != null) {
				if(!LR(ip, parent)) {
					parent.setLeft(n.getRight());
					s = s + parent.getKey() + ": Node with single child Deleted: " + ip + "\n";
//					
				}else {
					parent.setRight(n.getRight());
					s = s + parent.getKey() + ": Node with single child Deleted: " + ip + "\n";
				}
				return n.getRight();
			
			}
			// only right child
			else if(n.getLeft() != null && n.getRight() == null) {
				if(!LR(ip, parent)) {
					parent.setLeft(n.getLeft());
					s = s + parent.getKey() + ": Node with single child Deleted: " + ip + "\n";
					
				}else {
					parent.setRight(n.getLeft());
					s = s + parent.getKey() + ": Node with single child Deleted: " + ip + "\n";
				}
				return n.getLeft();
			}
			// two children
			else if(n.getLeft() != null && n.getRight() != null) {
				
				Node replaced = findMin(n.getRight());
				// removed node has only 1 right 
				if(replaced == null) {
					s = s + parent.getKey() + ": Non Leaf Node Deleted; removed: " + n.getKey()+ " replaced: " + n.getRight().getKey() + "\n";
					n.setKey(n.getRight().getKey());
					n.setRight(n.getRight().getRight());
					
				}
				// removed element has more than 1 node in its right
				else {
					s = s + parent.getKey() + ": Non Leaf Node Deleted; removed: " + n.getKey()+ " replaced: " + replaced.getLeft().getKey() + "\n";
					n.setKey(replaced.getLeft().getKey());
					replaced.setLeft(replaced.getLeft().getRight());
				}
				
			    return n;
			}
				
		}
		
		else if(compareResult < 0) {
			n.setLeft(delete(ip, n.getLeft(), n));

		}else if(compareResult > 0){
			n.setRight(delete(ip, n.getRight(), n));

		}
		
		return n;
		
	}
	/**
	 * find the path root to ip
	 * @param n
	 * @param ip
	 * @param arr
	 * @return
	 */
	public ArrayList<String> rootPath(Node n, String ip,ArrayList<String> arr) {
		if(n == null) {
			return null;
		}
		arr.add(n.key);
		int compareResult = ip.compareTo( n.key);
		if(compareResult < 0) {
			rootPath(n.getLeft(),ip,arr);

		}else if(compareResult > 0){
			rootPath(n.getRight(),ip,arr);

		}
		else {
			return arr;
		}
		return arr;
		
		
	}
	/**
	 * find the intersection point of ip1 and ip2
	 * @param ip1
	 * @param ip2
	 */
	public void send(String ip1, String ip2) {
		s = s + ip1 + ": Sending message to: " + ip2 +"\n";
		ArrayList<String> arr=new ArrayList<String>();
		ArrayList<String> ar=new ArrayList<String>();
		ArrayList<String> last_arr=new ArrayList<String>();
		ArrayList<String> arr1=rootPath(root,ip1, arr);
		ArrayList<String> arr2=rootPath(root,ip2, ar);
		
		int index = 0;
		// find the small size
		int size = min(arr1.size(), arr2.size());
		for(int i=0; i<size; i++) {
			if(arr1.get(i) == arr2.get(i)) {
				index = i;
			}
		}
		for(int i=arr1.size()-1; i>=index; i--) {
			last_arr.add(arr1.get(i));
			
		}
		for(int i=index+1; i<arr2.size(); i++) {
			last_arr.add(arr2.get(i));
			
		}
		for(int i=1; i<last_arr.size()-1; i++) {

			s = s + last_arr.get(i) + ": Transmission from: " + last_arr.get(i-1) + " receiver: " + ip2 +" sender:" + ip1 + "\n";
		}
		s = s + ip2 + ": Received message from: " + ip1 + "\n";

	}
	private int min(int s1, int s2) {
		if(s1 > s2) {
			return s2;
		}
		return s1;
	}
	
	



		
		
		
	
	

}
