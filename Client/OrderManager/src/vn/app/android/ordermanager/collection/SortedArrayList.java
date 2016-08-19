package vn.app.android.ordermanager.collection;

import java.util.ArrayList;
import java.util.Collections;

/**
 * SortedArrayList
 * @author thaonp
 * @param <T>
 */
public class SortedArrayList<T> extends ArrayList<T> {
	//-------------------------------------------------------------------------------------------------------------------
    /**
	 * SUID
	 */
	private static final long serialVersionUID = -7456924926613563944L;
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public SortedArrayList() {
		super();
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor with specific array list to clone.
	 * @param anotherArrayList
	 */
	public SortedArrayList(ArrayList<? extends T> anotherArrayList) {
		super(anotherArrayList);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Insert T value to array list and sorted it into suitable order depends on T.CompareTo() function.
	 * @param value
	 */
	@SuppressWarnings("unchecked")
    public void insertSorted(T value) {
        add(value);
        Comparable<T> cmp = (Comparable<T>) value;
        //Sort from tail to head list for performance
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1)) < 0; i--)
            Collections.swap(this, i, i-1);
    }
	//-------------------------------------------------------------------------------------------------------------------
}