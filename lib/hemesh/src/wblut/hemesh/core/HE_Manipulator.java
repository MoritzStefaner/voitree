/**
 * 
 */
package wblut.hemesh.core;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public interface HE_Manipulator {
	public HE_Mesh apply(HE_Mesh mesh);

	public HE_Mesh apply(HE_Selection selection);
}
