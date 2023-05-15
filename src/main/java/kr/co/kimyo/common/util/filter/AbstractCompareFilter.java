package kr.triniti.common.util.filter;

public abstract class AbstractCompareFilter<T> implements CompareFilter<T> {

	public T value;
	
	public CompareOperationType type;
	
	public boolean isGreat(T value) {
		return compareTo(value) < 0 ? true : false;
	}
	
	public boolean isGreatEquals(T value) {
		return compareTo(value) <= 0 ? true : false;
	}
	
	public boolean isLess(T value) {
		return compareTo(value) > 0 ? true : false;
	}
	
	public boolean isLessEquals(T value) {
		return compareTo(value) >= 0 ? true : false;
	}
	
	public boolean isEquals(T value) {
		return compareTo(value) == 0 ? true : false;
	}

	public abstract int compareTo(T cv);
	
	public boolean isCorrect(T value) {
		switch (type) {
			case Under    : return isLess(value);
			case Below    : return isLessEquals(value);
			case Equal    : return isEquals(value);
			case MoreThan : return isGreatEquals(value);
			case Over     : return isGreat(value);
		}
		
		return false;
	}
}
