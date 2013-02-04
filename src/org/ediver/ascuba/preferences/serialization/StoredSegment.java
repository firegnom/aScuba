package org.ediver.ascuba.preferences.serialization;

import java.util.List;

import mvplan.gas.Gas;
import mvplan.segments.SegmentAbstract;
import mvplan.segments.SegmentDive;

public class StoredSegment {

	double depth = 0.0;
	Boolean enable = true;
	int gas = -1;
	double setpoint = 0.0;
	double time = 0.0;

	public double getDepth() {
		return depth;
	}

	public Boolean getEnable() {
		return enable;
	}

	public int getGas() {
		return gas;
	}

	public double getSetpoint() {
		return setpoint;
	}

	public double getTime() {
		return time;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public void setGas(int gas) {
		this.gas = gas;
	}

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public StoredSegment(SegmentAbstract s ,List<Gas> knownGases) {
		depth = s.getDepth();
		enable = s.getEnable();
		setpoint = s.getSetpoint();
		time = s.getTime();
		Gas g = s.getGas();
		for(int i = 0 ; i < knownGases.size(); i++){
			if (0 == knownGases.get(i).compareTo(g)) {
				gas = i;
				break;
			}
		}
	}
	public SegmentAbstract toSegment(List<Gas> knownGases){
		SegmentDive ret = new SegmentDive(depth, time,knownGases.get(gas), setpoint);
		ret.setEnable(enable);
		return ret;
		
	}
}
