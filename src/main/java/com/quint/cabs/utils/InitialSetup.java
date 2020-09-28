package com.quint.cabs.utils;

import java.util.ArrayList;
import java.util.List;

import com.quint.cabs.beans.Cab;


/**
 * @author Simran Talda
 *
 */
public class InitialSetup {

	/**
	 * loadCabs - fetch records from db (for now adding adding stubs)
	 */
	public static List<Cab> loadCabs() {
		final List<Cab> cabs = new ArrayList<>();
		cabs.add(new Cab(1, 9.07, 5.0, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 8.999, 7.90, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 5.07, 8.90, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 7.07, 6.90, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 8.07, 9.0, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 9.07, 8.90, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 5.07, 6.50, CabStatus.NOT_AVAILABLE.getCabStatus(),
				0, RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 6.07, 4.40, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.PINK.getRideType()));
		cabs.add(new Cab(1, 4.07, 3.05, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 9.07, 7.07, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));
		cabs.add(new Cab(1, 8.07, 9.07, CabStatus.RIDING.getCabStatus(), 0,
				RideType.PINK.getRideType()));
		cabs.add(new Cab(1, 2.07, 8.08, CabStatus.AVAILABLE.getCabStatus(), 0,
				RideType.NORMAL.getRideType()));

		return cabs;
	}



}
