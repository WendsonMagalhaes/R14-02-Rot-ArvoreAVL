package adt.bst;

import java.util.LinkedList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	public int height(BSTNode<T> node) {

		if (node.isEmpty()) {
			return -1;
		} else {
			return 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(this.root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {

		BSTNode<T> result = new BSTNode<>();
		if (element != null) {
			if (!node.isEmpty()) {
				if (node.getData().equals(element)) {
					result = node;
				} else if (element.compareTo(node.getData()) < 0) {
					result = search((BSTNode<T>) node.getLeft(), element);
				} else if (element.compareTo(node.getData()) > 0) {
					result = search((BSTNode<T>) node.getRight(), element);
				}
			}
		}

		return result;
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
				node.setLeft(getNilNode());
				node.setRight(getNilNode());
			} else if (node.getData().compareTo(element) > 0) {
				insert((BSTNode<T>) node.getLeft(), element, node);
			} else if (node.getData().compareTo(element) < 0) {
				insert((BSTNode<T>) node.getRight(), element, node);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {

		BSTNode<T> result = null;
		if (!this.isEmpty()) {
			result = maximum(this.root);
		}
		return result;
	}

	private BSTNode<T> maximum(BSTNode<T> node) {

		BSTNode<T> result;
		if (node.getRight().isEmpty()) {
			result = node;
		} else {
			result = maximum((BSTNode<T>) node.getRight());
		}

		return result;
	}

	@Override
	public BSTNode<T> minimum() {

		BSTNode<T> result = null;
		if (!this.isEmpty()) {
			result = minimum(this.root);
		}
		return result;
	}

	private BSTNode<T> minimum(BSTNode<T> node) {

		BSTNode<T> result;
		if (node.getLeft().isEmpty()) {
			result = node;
		} else {
			result = minimum((BSTNode<T>) node.getLeft());
		}

		return result;
	}

	@Override
	public BSTNode<T> sucessor(T element) {

		BSTNode<T> node = search(element);
		BSTNode<T> result = null;
		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				result = minimum((BSTNode<T>) node.getRight());
			} else {
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				BSTNode<T> actual = node;
				while (parent != null && actual.equals(parent.getRight())) {
					actual = parent;
					parent = (BSTNode<T>) parent.getParent();
				}

				result = parent;
			}
		}

		return result;
	}

	@Override
	public BSTNode<T> predecessor(T element) {

		BSTNode<T> result = null;
		if (element != null) {
			BSTNode<T> node = search(element);
			if (!node.isEmpty()) {
				if (!node.getLeft().isEmpty()) {
					result = maximum((BSTNode<T>) node.getLeft());
				} else {
					BSTNode<T> parent = (BSTNode<T>) node.getParent();
					BSTNode<T> actual = node;
					while (parent != null && actual.equals(parent.getLeft())) {
						actual = parent;
						parent = (BSTNode<T>) parent.getParent();
					}

					result = parent;
				}
			}
		}

		return result;
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
			} else {
				BSTNode<T> sucessor = sucessor(node.getData());
				node.setData(sucessor.getData());
				remove(sucessor);
			}
		}
	}

	@Override
	public T[] preOrder() {

		T[] result = (T[]) new Comparable[0];

		if (!isEmpty()) {
			result = (T[]) preOrder(root, new LinkedList<>()).toArray(new Comparable[size()]);
		}
		return result;
	}

	private List<T> preOrder(BSTNode<T> node, List<T> list) {

		list.add(node.getData());

		if (!node.getLeft().isEmpty()) {
			preOrder((BSTNode<T>) node.getLeft(), list);
		}
		if (!node.getRight().isEmpty()) {
			preOrder((BSTNode<T>) node.getRight(), list);
		}

		return list;
	}

	@Override
	public T[] order() {
		T[] result = (T[]) new Comparable[0];

		if (!isEmpty()) {
			result = (T[]) order(root, new LinkedList<>()).toArray(new Comparable[size()]);
		}
		return result;
	}

	private List<T> order(BSTNode<T> node, List<T> list) {
		if (!node.getLeft().isEmpty()) {
			order((BSTNode<T>) node.getLeft(), list);
		}

		list.add(node.getData());

		if (!node.getRight().isEmpty()) {
			order((BSTNode<T>) node.getRight(), list);
		}
		return list;
	}

	@Override
	public T[] postOrder() {
		T[] result = (T[]) new Comparable[0];

		if (!isEmpty()) {
			result = (T[]) postOrder(root, new LinkedList<>()).toArray(new Comparable[size()]);
		}

		return result;
	}

	private List<T> postOrder(BSTNode<T> node, List<T> list) {
		if (!node.getLeft().isEmpty()) {
			postOrder((BSTNode<T>) node.getLeft(), list);
		}
		if (!node.getRight().isEmpty()) {
			postOrder((BSTNode<T>) node.getRight(), list);
		}

		list.add(node.getData());
		return list;
	}

	/**
	 * This method is already implemented using recursion. You must understand how
	 * it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

	private BSTNode getNilNode() {
		return new BSTNode.Builder<T>().data(null).parent(null).left(null).right(null).build();
	}

}