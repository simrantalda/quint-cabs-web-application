/**
 * 
 */
package com.quint.cabs.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.quint.cabs.beans.Cab;
import com.quint.cabs.beans.RideDetails;
import com.quint.cabs.utils.CabStatus;

/**
 * @author Simran Talda
 *
 */
@Service
public class RideExecutor {

	/**
	 * @param estimatedTime
	 * @param cab
	 * @param cost
	 */
	public void startRide(RideDetails ride, Cab cab) {
		final ScheduledExecutorService scheduler = Executors
				.newSingleThreadScheduledExecutor();
		final Runnable task = new Runnable() {
			public void run() {
				cab.setStatus(CabStatus.AVAILABLE.getCabStatus());
				cab.setPointsEarned(cab.getPointsEarned() + ride.getCost());
				cab.setX(ride.getDestinationX());
				cab.setY(ride.getDestinationY());
			}
		};
		scheduler.schedule(task, (int) Math.round(ride.getTimeTaken()), TimeUnit.MICROSECONDS);
		scheduler.shutdown();
	}

}
