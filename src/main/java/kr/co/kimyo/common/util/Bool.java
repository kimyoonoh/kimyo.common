package kr.triniti.common.util;

public class Bool {

		private Boolean solve;
		
		public boolean solve() {
			return this.solve();
		}
		
		private Bool(boolean b) {
			this.solve = b;
		}
		
		public static Bool calc(Bool r) {
			return calc(r.solve());
		}
		
		public static Bool calc(boolean b) {
			return new Bool(b);
		}
		
		public Bool and(Bool r) {
			return and(r.solve());
		}
		
		public Bool and(boolean b) {
			this.solve = this.solve && b;
			
			return this;
		}
		
		public Bool or(Bool r) {
			return or(r.solve());
		}
		
		public Bool or(boolean b) {
			this.solve = this.solve || b;
			
			return this;
		}
		
		public Bool not() {
			return not(this.solve());
		}
		
		public Bool not(Bool r) {
			return not(r.solve());
		}
		
		public Bool not(boolean b) {
			this.solve = !b;
			
			return this;
		}
		
		public Bool nand(Bool r) {
			return nand(r.solve());
		}
		
		public Bool nand(boolean b) {
			this.solve = not(and(b)).solve();
			
			return this;
		}
		
		public Bool nor(Bool r) {
			return nor(r.solve());
		}
		
		public Bool nor(boolean b) {
			this.solve = nor(or(b)).solve();
			
			return this;
		}
		
		public Bool xor(Bool r) {
			return xor(r.solve());
		}
		
		public Bool xor(boolean b) {
			this.solve = !(this.solve == b);
			
			return this;
		}
		
		public static boolean and(boolean ... bools) {
			if (bools.length == 0) return false;
			
			boolean result = bools[0];
			
			// 맨처음이 false이면 무조건 false다
			if (result == false) return false;
			
			for (int i = 1; i < bools.length; i++) {
				result &= bools[i];
			}
			
			return result;
		}
		
		public static boolean or(boolean ... bools) {
			if (bools.length == 0) return false;
			
			boolean result = bools[0];
			
			// 맨처음이 true이면 무조건 true다
			if (result == true) return true;
			
			for (int i = 1; i < bools.length; i++) {
				result |= bools[i];
			}
			
			return result;
		}
		
		
}
