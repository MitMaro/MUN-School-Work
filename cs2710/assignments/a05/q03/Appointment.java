import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Describes a Appointment with a start and end time and a description. Also
 * contains various methods of dealing with Appointments.
 * 
 * @author Tim Oram (#########)
 */
public class Appointment {
	
	/**
	 * The description of the Appointment.
	 */
	private String decript;
	
	// I know we were supposed to have separate fields for day, start and end
	// time but its easier to just have the included with the time. This also
	// allows an appointment to go past midnight into the next day.
	
	/**
	 * The start time (and date) of the Appointment.
	 */
	private Calendar startDateTime;
	
	/**
	 * The end time (and date) of the Appointment.
	 */
	private Calendar endDateTime;
	
	
	/**
	 * Creates a new appointment with a start time, end time and a description.
	 * <p>
	 * If the given start time is after the end time then the two times are
	 * switched.
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * if start time is after end time
	 *   assign this start time to end time
	 *   assign this end time to start time
	 * else
	 *   assign this start time to start time
	 *   assign this end time to end time
	 * assign this description to description
	 * </pre></code>
	 * 
	 * @param startTime The start time of the appointment.
	 * @param endTime The end time of the appointment.
	 * @param desc A description of the appointment.
	 */
	public Appointment(Calendar startTime, Calendar endTime, String desc){
		
		//check for a start time before a end time and switch values when needed
		if(startTime.after(endTime)){
			this.startDateTime = endTime;
			this.endDateTime = startTime;			
		}
		else{
			this.startDateTime = startTime;
			this.endDateTime = endTime;
		}
		this.decript = desc;
	}
	
	/**
	 * Checks if there is an overlap between two Appointments.
	 * <p>
	 * Assumes that if an Appointment start time is equal to the other
	 * Appointments end time then there is no overlap. The reason for this is
	 * that if an appointment is given to be from 3:00-3:30 it is normally from
	 * 3:00-3:29:59.
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * if a1 start time is before a2 end time
	 *   return true
	 * if a1 end time is after a2 start time
	 *   return false
	 * return true
	 * </pre></code>
	 * @param a Another appointment
	 * @return True if there is an overlap, false otherwise
	 */
	public boolean isOverlap(Appointment a){
		
		// check for start or end of a inside of this Appointment
		if(this.startDateTime.before(a.endDateTime) && this.endDateTime.after(a.endDateTime)){
			return true;
		}
		if(this.endDateTime.after(a.startDateTime) && this.startDateTime.before(a.startDateTime)){
			return true;
		}
		
		// the two appointments don't conflict so lets return false.
		return false;
	}
		
	/**
	 * Checks if a Appointment is before this Appointment.
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * return true if this end time is before a start time
	 * </pre></code>
	 * 
	 * @param a The Appointment to check
	 * @return True if Appointment is before else returns False.
	 */
	public boolean before(Appointment a){
		return this.endDateTime.before(a.startDateTime);
	}
	
	/**
	 * Checks if a Appointment is after this Appointment
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * return true if this start time is before a end time
	 * </pre></code>
	 * 
	 * @param a The Appointment to check
	 * @return True is Appointment is after else returns False
	 */
	public boolean after(Appointment a){
		return this.startDateTime.after(a.endDateTime);
	}
	
	/**
	 * Checks is this Appointments start date is the same day as the given date.
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * if this date equals the given start date
	 *   return true
	 * else
	 * 	return false
	 * </pre></code>
	 * 
	 * @param c A calendar that represents the date to be checked against.
	 * @return True if the Appointment is on the date else False. 
	 */
	public boolean isDate(Calendar c){		
		if(	this.startDateTime.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
			this.startDateTime.get(Calendar.MONTH ) == c.get(Calendar.MONTH ) &&
			this.startDateTime.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH )){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a string containing the start and end date and the description in
	 * the following format "startTime - endTime: description"
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * return formatted string of the Appointment information
	 * </pre></code>
	 */
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy hh:mm:ss aa");
		return sdf.format(this.startDateTime.getTime()) + " - " + sdf.format(this.endDateTime.getTime()) +
			": " + this.decript;
	}
	
}
