package com.codeblock.entity;

public enum Language {

	angularJS, JAVA, C {
		public String toString() {
			return "C#";
		}
	},
	
	jQuery,XML,HTML{
		public String toString() {
			return "HTML AND CSS";
		}
	},
SCRIPT{
		public String toString() {
			return "JAVA SCRIPT";
		}
	},

}
