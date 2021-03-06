package com.gmail.icbfernandez2012.scheduler.schedule.database;

import java.util.ArrayList;
import java.util.Iterator;

import com.gmail.icbfernandez2012.scheduler.schedule.classes.Section;
import com.gmail.icbfernandez2012.scheduler.schedule.database.aggregator.ClassAggregator;
import com.gmail.icbfernandez2012.scheduler.schedule.database.aggregator.OnlineClassAggregator;

public class ClassList
{
	private ArrayList<ClassAggregator>	aggregators;
	protected ArrayList<Section>		sections;
	protected String[]					shortNames;

	public ClassList(String[] shortNames)
	{
		aggregators = new ArrayList<ClassAggregator>();
		// aggregators.add(new LocalClassAggregator(
		// new String[] { "cache/coe.html" }));
		aggregators.add(new OnlineClassAggregator());

		sections = new ArrayList<Section>();

		setList(shortNames);
	}

	public void update()
	{
		for (ClassAggregator agg : aggregators)
		{
			agg.update();
		}
		setList(this.shortNames);
	}

	public void setList(String[] shortNames)
	{
		this.shortNames = shortNames;
		sections.clear();

		for (String subject : shortNames)
		{
			for (ClassAggregator agg : aggregators)
			{
				sections.addAll(agg.getCourseEnlistables(subject));
			}
		}
	}

	public void removeUseless()
	{
		Iterator<Section> i = sections.iterator();
		while (i.hasNext())
		{
			Section current = i.next();
			if (current.getProbability() <= 0.0f)
				i.remove();
			else if (current.isDissolved()) i.remove();
		}
	}

	public ArrayList<Section> getList()
	{
		return sections;
	}

	public Section getSection(String title)
	{
		String[] strings = title.split(" ");
		if (strings.length < 3) throw new IllegalArgumentException("Section of incorrect format");
		return getSection(strings[0] + " " + strings[1], strings[2]);
	}

	public Section getSection(String subject, String section)
	{
		for (Section s : sections)
		{
			if (s.getCourse().equalsIgnoreCase(subject) && s.getSection().equalsIgnoreCase(section)) return s;
		}
		return null;
	}

	// Testing
	public static void main(String[] args) throws Exception
	{
		ClassList list = new ClassList(new String[] { "CoE 141" });
		list.removeUseless();

		for (Section finalSections : list.sections)
		{
			System.out.println(finalSections);
		}
	}
}
