package com.space.sample;

import java.util.Comparator;
import java.util.Date;

public class SpaceData implements Comparable<SpaceData>{

	private String title = "";
	private String text = "";
	private Date createdDate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public int compareTo(SpaceData another) {
		
		return 0;
	}
	
	public static Comparator<SpaceData> titleComparator = new Comparator<SpaceData>() {
		@Override
		public int compare(SpaceData lhs, SpaceData rhs) {
		      return lhs.getTitle().compareTo(rhs.getTitle());
		}
	};
	
	public static Comparator<SpaceData> dateComparatorLatest = new Comparator<SpaceData>() {

		@Override
		public int compare(SpaceData lhs, SpaceData rhs) {
			return rhs.getCreatedDate().compareTo(lhs.getCreatedDate());     	
		}
	};
	
	public static Comparator<SpaceData> dateComparatorOldest = new Comparator<SpaceData>() {
		@Override
		public int compare(SpaceData lhs, SpaceData rhs) {
			return lhs.getCreatedDate().compareTo(rhs.getCreatedDate());
		}
	};
	
	
}
