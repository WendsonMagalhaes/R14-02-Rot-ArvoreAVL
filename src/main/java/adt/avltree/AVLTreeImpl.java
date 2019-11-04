package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;

/**
 * 
 * Implementacao de uma arvore AVL
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		
		int balance = 0;
		if(!node.isEmpty()) {
			balance = this.height((BSTNode<T>) node.getLeft()) - this.height((BSTNode<T>) node.getRight());
		
		}
		return balance;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		
		BTNode<T> parent = node.getParent();
		while(!parent.isEmpty()) {
			this.rebalance((BSTNode<T>) parent);
			parent = parent.getParent();
		}
	}

	@Override
	public void insert(T element) {

		this.insert(this.root, element, null);
	}

	private void insert(BSTNode<T> node, T element, BSTNode<T> parent) {

		if (element != null) {
			if (node.isEmpty()) {
				node.setParent(parent);
				node.setData(element);
				node.setLeft(null);
				node.setRight(null);
			} else {
				if (node.getData().compareTo(element) > 0) {

					insert((BSTNode<T>) node.getLeft(), element, node);
				} else if (node.getData().compareTo(element) < 0) {
					insert((BSTNode<T>) node.getRight(), element, node);
				}

				this.rebalance(node);
			}
		}
	}
	@Override
	public void remove(T element) {

		BSTNode<T> node = search(element);
		if (!node.isEmpty()) {
			remove(node);
		}
	}

	private void remove(BSTNode<T> node) {
		
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				this.rebalanceUp(node);
			} else if ((node.getLeft().isEmpty() && !node.getRight().isEmpty())
					|| (!node.getLeft().isEmpty() && node.getRight().isEmpty())) {
				if (node != root) {
					if (node.getParent().getLeft().equals(node)) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}
				} else {
					if (node.getRight().isEmpty()) {
						root = (BSTNode<T>) node.getLeft();
					} else {
						root = (BSTNode<T>) node.getRight();
					}
				}
				this.rebalance(node);
			} else {
				BSTNode<T> sucessor = sucessor(node.getData());
				node.setData(sucessor.getData());
				remove(sucessor);
			}
		}
	}

}
