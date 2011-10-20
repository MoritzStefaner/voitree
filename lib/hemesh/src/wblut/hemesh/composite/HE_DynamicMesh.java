/**
 * 
 */
package wblut.hemesh.composite;

import java.util.ArrayList;

import wblut.hemesh.core.HE_Manipulator;
import wblut.hemesh.core.HE_Mesh;

/**
 * @author Frederik Vanhoutte, W:Blut
 *
 */
public class HE_DynamicMesh extends HE_Mesh {
	private final ArrayList<HE_Manipulator>	modifierStack;
	private HE_Mesh							bkp;

	public HE_DynamicMesh(final HE_Mesh baseMesh) {
		this.set(baseMesh);
		bkp = get();
		modifierStack = new ArrayList<HE_Manipulator>();
	}

	public void update() {
		this.set(bkp);
		applyStack();
	}

	private void applyStack() {
		for (int i = 0; i < modifierStack.size(); i++) {
			modifierStack.get(i).apply(this);
		}

	}

	public void add(final HE_Manipulator mod) {
		modifierStack.add(mod);
	}

	public void remove(final HE_Manipulator mod) {
		modifierStack.remove(mod);
	}

	@Override
	public void clear() {
		modifierStack.clear();
		set(bkp);
	}

	public HE_DynamicMesh setBaseMesh(final HE_Mesh baseMesh) {
		set(baseMesh);
		bkp = get();
		applyStack();
		return this;
	}

}
