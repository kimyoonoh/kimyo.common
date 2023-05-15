package kr.triniti.common.util.filter;

public enum CompareOperationType {
	Under,        // 미만
	Below,        // 이하
	Equal,        // 동급
	MoreThan,     // 이상
	Over,         // 초과
	// 범위값이라 미구현됨. 범위값은 2개의 변수가 필요함.
	//Contain,      // 포함
	//UnderOverlap, // 하한 겹침
	//OverOverlap   // 상한 겹침
}