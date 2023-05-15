package kr.triniti.common.util.filter;

public class LongNumberFilter extends AbstractCompareFilter<Long> {
	public int compareTo(Long longValue) {
		return this.value.compareTo(longValue);
	}
}
