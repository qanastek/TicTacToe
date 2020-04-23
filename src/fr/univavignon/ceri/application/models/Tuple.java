/**
 * 
 */
package fr.univavignon.ceri.application.models;

/**
 * Tuple
 * @author Yanis Labrak
 */
public class Tuple<type1, type2> {
	
	/**
	 * Key
	 */
	public final type1 x;
	
	/**
	 * Value
	 */
	public final type2 y; 
	
	/**
	 * Constructor
	 * @param x {@code Type1} Key
	 * @param y {@code type2} Value
	 */
    public Tuple(type1 x, type2 y) {
    	this.x = x;
    	this.y = y;
    } 

}
