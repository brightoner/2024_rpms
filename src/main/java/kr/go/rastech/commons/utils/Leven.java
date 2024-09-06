package kr.go.rastech.commons.utils;

public class Leven {
    private static int minimum(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
    }

    //GST_kdh_20120829 기준 길이를 JSP단에서 처리
    public static int computeLevenshteinD(CharSequence str1,
            CharSequence str2, int baseLength) {

    int[][] distance = new int[baseLength + 1][baseLength + 1];

    for (int i = 0; i <= baseLength; i++) {
            distance[i][0] = i;
    }
    for (int j = 1; j <= baseLength; j++) {
            distance[0][j] = j;
    }

    for (int i = 1; i <= baseLength; i++) {
            for (int j = 1; j <= baseLength; j++) {
                    distance[i][j] = minimum(
                                    distance[i - 1][j] + 1,
                                    distance[i][j - 1] + 1,
                                    distance[i - 1][j - 1]
                                                    + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                                                    : 1));
            }
    }
    return distance[baseLength][baseLength];
    }    
    
    
    public static int computeLevenshteinDistance(CharSequence str1,
                    CharSequence str2) {
    	
    		//GST_kdh_20120829 타이틀 길이에 따른 오차 최소화
    		int baseLength = 0;
    		if(str1.length()>0 && str2.length()>0)
    		{
	    		if(str1.length()>str2.length()) {
	    			baseLength = str2.length();
	    		} else {
	    			baseLength = str1.length();}
    		}
            int[][] distance = new int[baseLength + 1][baseLength + 1];

            for (int i = 0; i <= baseLength; i++) {
                    distance[i][0] = i;
            }
            for (int j = 1; j <= baseLength; j++) {
                    distance[0][j] = j;
            }

            for (int i = 1; i <= baseLength; i++) {
                    for (int j = 1; j <= baseLength; j++) {
                            distance[i][j] = minimum(
                                            distance[i - 1][j] + 1,
                                            distance[i][j - 1] + 1,
                                            distance[i - 1][j - 1]
                                                            + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                                                            : 1));
                    }
            }

            return distance[baseLength][baseLength];

    		/*
            int[][] distance = new int[str1.length() + 1][str2.length() + 1];

            for (int i = 0; i <= str1.length(); i++)
                    distance[i][0] = i;
            for (int j = 1; j <= str2.length(); j++)
                    distance[0][j] = j;

            for (int i = 1; i <= str1.length(); i++)
                    for (int j = 1; j <= str2.length(); j++)
                            distance[i][j] = minimum(
                                            distance[i - 1][j] + 1,
                                            distance[i][j - 1] + 1,
                                            distance[i - 1][j - 1]
                                                            + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                                                            : 1));

            return distance[str1.length()][str2.length()];
            */
    }
}

