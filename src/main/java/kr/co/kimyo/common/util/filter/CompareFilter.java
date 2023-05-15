package kr.triniti.common.util.filter;

public interface CompareFilter<T> extends Comparable<T> {
	
	public boolean isCorrect(T value);
}
