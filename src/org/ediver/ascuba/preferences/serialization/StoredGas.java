package org.ediver.ascuba.preferences.serialization;

import mvplan.gas.Gas;

public class StoredGas {
	boolean enable;

	private double fHe, fO2;

	private double mod;

	public StoredGas(Gas g) {
		enable = g.getEnable();
		fHe = g.getFHe();
		fO2 = g.getFO2();
		mod = g.getMod();

	}

	public double getfHe() {
		return fHe;
	}

	public double getfO2() {
		return fO2;
	}

	public double getMod() {
		return mod;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public void setfHe(double fHe) {
		this.fHe = fHe;
	}
	public void setfO2(double fO2) {
		this.fO2 = fO2;
	}

	public void setMod(double mod) {
		this.mod = mod;
	}

	public Gas toGas() {
		Gas ret = new Gas(fHe, fO2, mod);
		ret.setEnable(enable);
		return ret;
	}
}
