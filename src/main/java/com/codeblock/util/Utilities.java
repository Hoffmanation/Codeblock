package com.codeblock.util;

import com.codeblock.entity.Language;

public abstract class Utilities {
	
	
	
	public static String getImgPath(Language len){
		String language = String.valueOf(len);
	    switch(language) {
        case "C#" :
       return "resources/img/c_sharp.png" ;
           case "JAVA" :
            return "resources/img/java.png" ;
        case "XML" :
            return "resources/img/xml.png" ;
        case "JAVA SCRIPT" :
            return "resources/img/java_script.jpg" ;
        case "angularJS" :
            return "resources/img/angular.png" ;
        case "jQuery" :
            return "resources/img/jquery.png" ;
        case "HTML AND CSS" :
            return "resources/img/html_css.png" ;

     }
		return language;
		
	}

}
