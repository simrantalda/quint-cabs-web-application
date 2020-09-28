/**
 * 
 */
package com.quint.cabs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.quint.cabs.async.RideExecutor;
import com.quint.cabs.beans.Cab;
import com.quint.cabs.beans.Client;
import com.quint.cabs.beans.RideDetails;
import com.quint.cabs.utils.CabStatus;
import com.quint.cabs.utils.InitialSetup;
import com.quint.cabs.utils.RideType;

/**
 * @author Simran Talda
 *
 */
@Service
public class QuintCabsService {
	@Value("${quint.cabs.cost.per.minute}")
	private Integer costPerMinute;

	@Value("${quint.cabs.cost.per.km}")
	private Integer costPerKm;

	@Value("${quint.cabs.cost.special.service}")
	private Integer speed;

	@Value("${quint.cabs.speed}")
	private Integer specialAllowance;

	@Autowired
	private RideExecutor rideExecutor;
	/**
	 * processRequest - process client request
	 * 
	 * @param c
	 * @return
	 */
	public RideDetails processRequest(Client c) {

		// load cabs
		List<Cab> cabs = InitialSetup.loadCabs();
		// filter only avaiable cabs
		// for future scaling we can filter cabs avaiable in particular radius
		cabs = cabs.stream()
				.filter(cab -> cab.getStatus()
						.equals(CabStatus.AVAILABLE.getCabStatus())
						&& cab.getCabType().equals(c.getCabType()))
				.collect(Collectors.toList());
		// find nearest cab
		return findNearestCab(cabs, c.getX(), c.getY());
	}

	/**
	 * findNearestCab- pre-processing and creating data sets for knn algo
	 * 
	 * @param cabs
	 * @param x
	 * @param y
	 * @return
	 */
	public RideDetails findNearestCab(List<Cab> cabs, double x, double y) {
		// Cab cab = new Cab();
		double minDistance = Double.MAX_VALUE;
		Cab nearestCab = null;
		for (Cab cab : cabs) {
			double distance = knnAlgorithm(x, y, cab.getX(), cab.getY());
			if (minDistance > distance || distance == 0) {
				nearestCab = cab;
				minDistance = distance;
			}
		}

		return (nearestCab != null)
				? setRideDetails(nearestCab, minDistance)
				: null;

	}

	/**
	 * setRideDetails - calculate time and cost for ride
	 * 
	 * @param cab
	 * @param minDistance
	 * @return
	 */
	public RideDetails setRideDetails(Cab cab, double minDistance) {
		// set ride details
		final RideDetails ride = new RideDetails();
		ride.setCabId(cab.getCabId());
		double cost = 0;
		ride.setTimeTaken(minDistance / this.speed);
		if (ride.getTimeTaken() <= 1) {
			cost = this.costPerMinute;
		} else {
			cost = this.costPerKm;
		}
		if (cab.getCabType() == RideType.PINK.getRideType()) {
			cost += this.specialAllowance;
		}

		ride.setCost(cost);

		return ride;
	}

	/**
	 * knnAlgorithm - Getting the distance between two data elements bu using
	 * The K Nearest Neighbor Classifier
	 * 
	 * @param clientX
	 * @param clientY
	 * @param cabX
	 * @param cabY
	 * @return
	 */
	public double knnAlgorithm(double clientX, double clientY, double cabX,
			double cabY) {
		final double term1 = (cabX - clientX) * (cabX - clientX);
		final double term2 = (cabY - clientY) * (cabY - clientY);
		final double sum = term1 + term2;
		return Math.abs(Math.sqrt(sum));
	}

	/**
	 * startRide - pre-ride process
	 * 
	 * @param ride
	 */
	public void startRide(RideDetails ride) {
		List<Cab> cabs = InitialSetup.loadCabs();
		cabs = cabs.stream().filter(cab -> cab.getCabId() == ride.getCabId())
				.collect(Collectors.toList());
		if (!cabs.isEmpty()) {
			final Cab selectedCab = cabs.get(0);
			selectedCab.setStatus(CabStatus.RIDING.getCabStatus());
			rideExecutor.startRide(ride, selectedCab);
		}

	}

}
