import java.util.ArrayList;

public class MyAVL {
	public String s;
	public Node root;
	private int height(Node n) {
		if(n!=null) {
			return n.height;
		}
		else {
			return -1;
		}
	}
	
	public MyAVL() {
		this.s = "";
		this.root = null;
	}

	private void updateHeight(Node n) {
		int leftChildHeight = height(n.getLeft());
		int rightChildHeight = height(n.getRight());
		n.height = max(leftChildHeight, rightChildHeight) + 1;
	}

	private int max(int n1, int n2) {
		if(n1 < n2) {
			return n2;
		}
		return n1;
	}
	
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
	
	
	private int balanceFactor(Node n) {
		return height(n.getRight()) - height(n.getLeft());
	}
	/**
	 * right rotation
	 * @param n
	 */
	public Node rotateRight(Node n) {
		Node leftChild = n.getLeft();
		if(n.getKey().equals(root.getKey())) {
			this.root=leftChild;
		}
		n.setLeft(leftChild.getRight());
		leftChild.setRight(n);
		
		updateHeight(n);
		updateHeight(leftChild);
		
		
		return leftChild;
	}
	/**
	 * left rotation
	 * @param n
	 */
	public Node rotateLeft(Node n) {
		Node rightChild = n.getRight();
		if(n.getKey().equals(root.getKey())) {
			this.root=rightChild;
		}
		n.setRight(rightChild.getLeft());
		rightChild.setLeft(n);
		
		updateHeight(n);
		updateHeight(rightChild);
		
		
		return rightChild;
	}
	
	public Node rebalance(Node n) {
		int balanceFactor = balanceFactor(n);

		// if it is left heavy
		if(balanceFactor < -1) {
			// if left of n is leaf or a single node
			if(balanceFactor(n.getLeft()) <= 0) {
				// rotate right
				n = rotateRight(n);
				s = s + "Rebalancing: right rotation\n";
			}else {
				// rotate left-right
				n.setLeft(rotateLeft(n.getLeft()));
				n = rotateRight(n);
				s = s + "Rebalancing: left-right rotation\n";
			}

		}
		// if it is right heavy
		if(balanceFactor > 1) {
			// if right of n is leaf or a single node
			if(balanceFactor(n.getRight()) >= 0) {
				// rotate left
				n = rotateLeft(n);
				s = s + "Rebalancing: left rotation\n";
			}else {
				// rotate right - left
				n.setRight(rotateRight(n.getRight()));
				n = rotateLeft(n);
				s = s + "Rebalancing: right-left rotation\n";
			}

		}
		return n;
		
	}
	public Node addNode2(String ip, Node n) {
		
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


	public Node addNode(String ip, Node n) {

		n=this.addNode2(ip, n);
		updateHeight(n);
//		ArrayList<Node> arr= new ArrayList<Node>();
//		ArrayList<Node> arr1=rootPathNode(root,ip,arr);
//		for(int i=arr1.size()-1;i>-1;i--) {
//			rebalance(arr1.get(i));
//		}
		return rebalance(n);
	}

	public Node delete2(String ip,Node n,Node parent) {
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
				return n=null;
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
					return n;
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

	public Node delete(String ip, Node n, Node parent) {

		n=this.delete2(ip, n, parent);
		// if node is null then the tree does not contain the key
		if(n == null) {
			return null;
		}
		updateHeight(n);
//		ArrayList<Node> arr= new ArrayList<Node>();
//		ArrayList<Node> arr1=rootPathNode(root,ip,arr);
//		for(int i=arr1.size()-1;i>-1;i--) {
//			rebalance(arr1.get(i));
//		}
		return rebalance(n);
	}
	
	public ArrayList<Node> rootPathNode(Node n, String ip,ArrayList<Node> arr) {
		if(n == null) {
			return null;
		}
		arr.add(n);
		int compareResult = ip.compareTo( n.key);
		if(compareResult < 0) {
			rootPathNode(n.getLeft(),ip,arr);

		}else if(compareResult > 0){
			rootPathNode(n.getRight(),ip,arr);

		}
		else {
			return arr;
		}
		return arr;
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
