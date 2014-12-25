package schedule.timing;

/**
 * A 24-hour representation of time
 * higher bits: hour, lower bits: minutes
 * @author Ian Christian Fernandez
 *
 */
public class Time {
	private int time;
	
	public Time(int time)
	{
		this.time = time;
	}
	
	public Time(int hour, int minute)
	{
		this.time = (hour & 0xff) << 8 | (minute & 0xff);
	}
	
	public int getHour()
	{
		return time >> 8;
	}
	
	public void setHour(int hour)
	{
		this.time = (hour & 0xff) << 8 | (getMinute() & 0xff);
	}
	
	public int getMinute()
	{
		return time & 0xff;
	}
	
	public void setMinute(int minute)
	{
		this.time = (getHour() & 0xff) << 8 | (minute & 0xff);
	}
	
	protected int getValue()
	{
		return time;
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d", getHour(), getMinute());
	}
}
