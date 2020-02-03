import java.util.ArrayList;

public class AVLTree implements AVLTreeADT {
	//variable size stores the # of records
	private int size; 
	private AVLTreeNode root;

	//returns a new tree object
	public AVLTree() {
		size = 0;
		root = new AVLTreeNode();
	}
	
	//sets given node as the root of the tree
	public void setRoot(AVLTreeNode node) { 
		this.root = node;
	}

	//returns the root of the tree
	public AVLTreeNode root() {
		return root;
	}
	
	//returns true if the node is the root else false
	public boolean isRoot(AVLTreeNode node) {
		if (node == root) {
			return true;
		}
		else {
			return false;
		}
	}

	//returns the # of elements in the tree
	public int getSize() {
		return this.size;
	}

	//returns the node with a given key
	//else it will return where the leaf node where k should have been
	public AVLTreeNode get(AVLTreeNode node, int key) {
		if (node.isLeaf() || node.getKey() == key) {
			return node;
			
		} else if (key < node.getKey()) {
				return get(node.getLeft(), key);
		}else {
				return get(node.getRight(), key);
			}
		}
	
	//if the node that has the smallest key exists we get it else we return null
	public AVLTreeNode smallest(AVLTreeNode node) {
		if (node == null) {
			return null;
			
		} else if (node.getLeft().isLeaf()){
				return node;
			} else
				return smallest(node.getLeft());
		}
	
	//returns the node that contains the new record
	public AVLTreeNode put(AVLTreeNode node, int key, int data) throws TreeException {
		AVLTreeNode Node = get(node, key);
		if (Node.isLeaf()) {
			Node.setData(data);
			Node.setKey(key);
			Node.setLeft(new AVLTreeNode(Node));
			Node.setRight(new AVLTreeNode(Node));
			recomputeHeight(Node);
			size++;
			return Node;
		} else
			throw new TreeException("Node already exists inside the tree");
	}

	//to remove a record using a key. returns the parent of the node
	public AVLTreeNode remove(AVLTreeNode node, int key) throws TreeException {
		AVLTreeNode Node = get(node, key);

		//if node has no children
		if (Node.isLeaf() == true) {
			throw new TreeException("Node doesn't exist inside the");

		//if the node isn't in the tree
		} else if (Node.getLeft().isLeaf() && !Node.getRight().isLeaf()) {
			AVLTreeNode Rchild = Node.getRight();
			if (Node.isRoot()) {
				root = Rchild;
				Rchild.setParent(null);
			} else {
				Node.getParent().setRight(Rchild);
				Rchild.setParent(Node.getParent());
			}
		}

		else if (!Node.getLeft().isLeaf() && Node.getRight().isLeaf()) {
			AVLTreeNode Lchild = Node.getLeft();
			if (Node.isRoot()) {
				root = Lchild;
				Lchild.setParent(null);
			} else {
				Node.getParent().setLeft(Lchild);
				Lchild .setParent(Node.getParent());
			}
		}

		//if node has no children
		else if (Node.getLeft().isLeaf() && Node.getRight().isLeaf()) {
			if (Node.getParent().getLeft() == Node) {
				Node.getParent().setLeft(Node.getRight());
			} else {
				Node.getParent().setRight(Node.getRight());
			}
		}

		//if node is an internal node
		else {
			AVLTreeNode SRchild = smallest(Node.getRight());
			Node.setKey(SRchild.getKey());
			Node.setData(Node.getData());
			if (SRchild == Node.getRight()) {
				Node.setRight(SRchild.getRight());
				SRchild.getRight().setParent(Node);
			} else {
				SRchild.getParent().setLeft(SRchild.getRight());
				SRchild.getRight().setParent(SRchild.getParent());
			}
		}
		recomputeHeight(Node);
		size--;
		return Node;
	}

	//returns a list that has the nodes in it
	public ArrayList<AVLTreeNode> inorder(AVLTreeNode node) {
		ArrayList<AVLTreeNode> list = new ArrayList<AVLTreeNode>();
		inorderRec(node, list);
		return list;
	}

	public void inorderRec(AVLTreeNode node, ArrayList<AVLTreeNode> list) {
		if (node.isLeaf() == true) {
			return;
		} else {
			inorderRec(node.getLeft(), list);
			list.add(node);
			inorderRec(node.getRight(), list);
		}

	}

	//needed when there is a physical change in the tree so that we are always aware of the height
	public void recomputeHeight(AVLTreeNode node) {
		node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
		if (node.isRoot()) {
			return;
		} else {
			recomputeHeight(node.getParent());
		}
	}
	
	// to be used in some of the methods below
    private int num(AVLTreeNode node) {
        return node.getLeft().getHeight() - node.getRight().getHeight();
    }
    
	public void rebalanceAVL(AVLTreeNode r, AVLTreeNode v) {
		int com = num(v);
		if (com < -1 || com > 1) {
			Boolean left;
			AVLTreeNode y = taller(v, true);
			if (y.getParent().getLeft() == y) {
				left = true;
			} else {
				left = false;
			}
			AVLTreeNode z = taller(y, left);
			rotation(v, y, z);
		}
		if (v == r) {
			return;
		} else {
			rebalanceAVL(root, v.getParent());
		}
	}

	public void putAVL(AVLTreeNode node, int key, int data) throws TreeException {
		AVLTreeNode Node = put(node, key, data);
		rebalanceAVL(root, Node);
	}

	public void removeAVL(AVLTreeNode node, int key) throws TreeException {
		AVLTreeNode rebalanceNode = get(node, key);
		remove(node, key);
		rebalanceAVL(root, rebalanceNode);
	}


	//returns the tallest child or the left one if they are equal
	public AVLTreeNode taller(AVLTreeNode node, boolean onLeft) {
		int com = num(node);
		if (com > 0) {
			return node.getLeft();
		} else if (com < 0) {
			return node.getRight();
		} else if (onLeft) {
			return node.getRight();
		} else
			return node.getLeft();

	}
	// this is the method for the left rotation to be used for the optional methods
	private void LeftRoatation(AVLTreeNode node) {
		AVLTreeNode LChild = node.getRight().getLeft();
		AVLTreeNode PNode = node.getParent();
		AVLTreeNode RNode = node.getRight();
		if (PNode != null) {
			RNode.setParent(PNode);
			if (PNode.getLeft() == node) {
				PNode.setLeft(RNode);
			} else {
				PNode.setRight(RNode);
			}
		} else {
			RNode.setParent(null);
			root = RNode;
		}
		node.setRight(LChild);
		LChild.setParent(node);
		RNode.setLeft(node);
		node.setParent(RNode);
		recomputeHeight(node);

	}

	// this is the method for the right rotation to be used for the optional methods
	private void RightRotation(AVLTreeNode node) {
		AVLTreeNode Rchild = node.getLeft().getRight();
		AVLTreeNode PNode = node.getParent();
		AVLTreeNode LNode = node.getLeft();
		if (PNode != null) {
			LNode.setParent(PNode);
			if (PNode.getLeft() == node) {
				PNode.setLeft(LNode);
			} else {
				PNode.setRight(LNode);
			}
		} else {
			LNode.setParent(null);
			root = LNode;
		}
		node.setLeft(Rchild);
		Rchild.setParent(node);
		LNode.setRight(node);
		node.setParent(LNode);
		recomputeHeight(node);
	}
	//the rotation makes a node the new parent of a subtree
	public AVLTreeNode rotate(AVLTreeNode node) {
		int com = num(node);

		//checks to see if the right tree is higher and if it is, a left rotation is performed
		if (com < -1) {
			LeftRoatation(node);

		//checks to see if the left tree is higher and if it is, a right rotation is performed
		} else if (com > -1) {
			RightRotation(node);
		}

		//recompute height after re-balancing
		recomputeHeight(node);
		return node;
	}

	public AVLTreeNode rotation(AVLTreeNode z, AVLTreeNode y, AVLTreeNode x) {

		//z is the location of the imbalance. y is the bigger child of z and x is the bigger child of y like we do in class
		int comZ = num(z);
		int comY = num(y);
		if (comZ > 1 && comY < 0) {
			LeftRoatation(y);
			RightRotation(z);
			return x;
		}
		if (comZ < 1 && comY > 0) {
			RightRotation(y);
			LeftRoatation(z);
			return x;
		} else {
			rotate(z);
			return x;
		}
	}
}
