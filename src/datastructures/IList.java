package datastructures;

public interface IList<T> {
	/**
	 * Legg til et element på slutten av listen.
	 *
	 * @param s
	 *            Elementet som skal legges til
	 *
	 *            Etterpå vil size() øke med én, og get(size()-1) vil returnere
	 *            elementet.
	 */
	void add(T s);

	/**
	 * Fjern alle elementer fra listen.
	 *
	 * Etterpå vil size() == 0
	 */
	void clear();

	/**
	 * Returner elementet på posisjon i
	 *
	 * @param i
	 * @return
	 */
	T get(int i);

	/**
	 * @return True hvis listen er tom
	 */
	boolean isEmpty();

	/**
	 * Fjern et element fra listen
	 *
	 * @param i
	 *            Indeksen til elementet
	 * @return elementet som ble fjernet
	 *
	 *         Etterpå vil alle senere indekser i listen flyttes én posisjon
	 *         frem.
	 */
	T remove(int i);

	/**
	 * Sett elementet på indeks i til s
	 *
	 * @param i
	 *            Index
	 * @param s
	 *            Nytt element
	 *
	 *            Etterpå vil get(i) == s
	 */
	void set(int i, T s);

	/**
	 * @return Antall elementer i listen
	 */
	int size();
}
