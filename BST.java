
public class BST {

	public class Node {
		private int key;
		private Node parent;
		private Node leftChild;
		private Node rightChild;

		public Node() {
			this.key = -1;
			this.parent = null;
			this.leftChild = null;
			this.rightChild = null;
		}

		Node(int key) {
			this.key = key;
			this.parent = null;
			this.leftChild = null;
			this.rightChild = null;
		}

		Node(int key, Node parent, Node leftChild, Node rightChild) {
			this.key = key;
			this.parent = parent;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public int getKey() {
			return this.key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public Node getParent() {
			return this.parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getLeftChild() {
			return this.leftChild;
		}

		public void setLeftChild(Node leftChild) {
			this.leftChild = leftChild;
		}

		public Node getRightChild() {
			return this.rightChild;
		}

		public void setRightChild(Node rightChild) {
			this.rightChild = rightChild;
		}

	}

	private Node root;
	private int counter;

	BST() {
		this.root = null;
		this.counter = 0;
	}

	// This method should return the number of elements stored within the tree
	int size() {
		return counter;
	}

	// Inserts a new element into the tree. Duplicates are not allowed. If the
	// element is already in the tree, print “Element is already in the tree!
	void insert(int element) {
		boolean done = false;

		if (element < 0) {
			System.out.println("this tree only accepts positive numbers");
			return;
		}
		if (counter == 0) {
			root = new Node(element);
			//System.out.println ("Inserted "+ root.getKey());
			//System.out.println("Added " + element + " to the tree");
			counter++;
			done = true;
			return;
		}
		Node current = root;
		Node insertNode = new Node(element);
		while (done == false) {
			if (element < current.getKey()) {
				if (element == current.getKey()) {
					System.out.println(element + " is already in the tree!");
					return;
				}
				if (current.getLeftChild() != null) {
					current = current.getLeftChild();
				} else {
					current.setLeftChild(insertNode);
					insertNode.setParent(current);
					counter++;
					//System.out.println("Added " + element + " to the tree");
					//System.out.println("Inserted " + current.getLeftChild().getKey());
					done = true;
				}
			} else {
				if (element == current.getKey()) {
					System.out.println(element + " is already in the tree!");
					return;
				}
				if (current.getRightChild() != null) {
					current = current.getRightChild();
				} else {
					current.setRightChild(insertNode);
					insertNode.setParent(current);
					//System.out.println("Inserted " + current.getRightChild().getKey());
					//System.out.println("Added " + element + " to the tree");
					counter++;
					done = true;
				}
			}
		}
	}

	// Removes the specified element from the tree. If the element is not
	// currently stored in the tree, print “Element not found!"
	void delete(int element) {
		if (counter == 0) {
			System.out.println("Tree is empty");
			return;
		}
		if (counter == 1) {
			root = null;
			counter = 0;
			return;
		}
		boolean found = false;
		Node current = root;
		boolean done = false;
		while (done == false) {
			if (element > current.getKey()) {
				if (current.getRightChild() != null) {
					current = current.getRightChild();
				}
			}
			if (element < current.getKey()) {
				if (current.getLeftChild() != null) {
					current = current.getLeftChild();
				}
			} 
			if (current.getKey() != element) {
				System.out.println("element not found");
				return;
			}
			if (current.getKey() == element) {
				//System.out.println("found element");
				found = true;
			
				//deleting a node that is a leaf
				if (current.getRightChild() == null && current.getLeftChild() == null) {
					//determine whether leaf is left or right child of parent node
					if (current.getKey() > current.getParent().getKey()) {
						current.getParent().setRightChild(null);
						//System.out.println("deleted " + current.getKey());
						counter--;
						done = true;
					}
					if (current.getKey() < current.getParent().getKey()) {
						current.getParent().setLeftChild(null);
						//System.out.println("deleted " + current.getKey());
						counter--;
						done = true;
					}
				//deleting a node with 1 child
				//the child is smaller than current
				} if (current.getRightChild() == null && current.getLeftChild() != null) {
					//currents parents > currents left child... set parents left child to current left child
					if (current.getParent().getKey() > current.getLeftChild().getKey()) {
						current.getParent().setLeftChild(current.getLeftChild());
						counter--;
						done = true;
					}
					//currents parent < currents left child... set parents right child to currents left child
					if (current.getParent().getKey() < current.getLeftChild().getKey()) {
						current.getParent().setRightChild(current.getLeftChild());
						counter--;
						done = true;
					}
				}
				//the child is bigger than current
				if (current.getRightChild() != null && current.getLeftChild() == null) {
					//currents parent > currents right child... set parents left child to currents right child
					if (current.getParent().getKey() > current.getRightChild().getKey()) {
						current.getParent().setLeftChild(current.getRightChild());
						counter--;
						done = true;
					}
					//currents parent < currents right child... set parents right child to currents right child
					if (current.getParent().getKey() < current.getRightChild().getKey()) {
						current.getParent().setRightChild(current.getRightChild());
						counter--;
						done = true;
					}
					
				//deleting a node with 2 children
				} if (current.getLeftChild() != null && current.getRightChild() != null) {
					Node deleteNode = current;
					//find minimum node from right subtree, and replace the deleted node with it
					int smallestVal = minVal(current);
					//System.out.println("SMALL: " + smallestVal);
					boolean foundNode = false;
					while(foundNode == false) {
						if (current.getKey() > smallestVal) {
							current = current.getLeftChild();
						}
						if (current.getKey() == smallestVal) {
							//System.out.println("found smallest node");
							deleteNode.setKey(smallestVal);
							//if the replacement node is a leaf
							if (current.getLeftChild() == null && current.getRightChild() == null) {
								//making sure it is not the root
								if (current.getParent() != null) {
									if (current.getParent().getLeftChild() == current) {
										current.getParent().setLeftChild(null);
										counter--;
										foundNode = true;
										done = true;
									}
									if (current.getParent().getRightChild() == current) {
										current.getParent().setRightChild(null);
										counter--;
										foundNode = true;
										done = true;
									}
									
								} else {
									root = null;
									counter--;
									foundNode = true;
									done = true;
								}
							}	
						}
					}
					done = true;
					return;
				}
			} 
		}
		if (found == false) {
			System.out.println("element not found");
		}
	}
	
	int minVal (Node node) {
		if (node.getLeftChild() == null) {
			return node.getKey();
		}
		return minVal(node.getLeftChild());
	}

	// Prints out all of the elements in the tree according to their order in a
	// preorder traversal
	void preorder() {
		preorderHelperMethod(root);
	}

	private void preorderHelperMethod(Node node) {
		if (node!= null) {
			System.out.print(node.getKey() + " ");
			preorderHelperMethod(node.getLeftChild());
			preorderHelperMethod(node.getRightChild());
		}
	}
	
	// Prints out all of the elements in the tree according to their order in a
	// postorder traversal
	void postorder() {
		postorderHelperMethod(root);
	}
	
	private void postorderHelperMethod(Node node) {
		if (node == null) {
			return;
		}
		postorderHelperMethod(node.getLeftChild());
		postorderHelperMethod(node.getRightChild());
		System.out.print(node.getKey() + " ");
		
	}

	// Prints out all of the elements in the tree according to their order in an
	// inorder traversal
	void inorder() {
		//System.out.println("Method not yet implemented");
		inorderHelperMethod(root);	
	}
	
	private void inorderHelperMethod(Node node) {
		if (node == null) {
			return;
		}
		inorderHelperMethod(node.getLeftChild());
		System.out.print(node.getKey() + " ");
		inorderHelperMethod(node.getRightChild());
	}
	

}
