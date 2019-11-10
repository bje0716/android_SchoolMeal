package com.grapefruit.schoolmeal;

public class Util {

    private static final String SEOUL = "서울특별시교육청";
    private static final String INCHEON = "인천광역시교육청";
    private static final String BUSAN = "부산광역시교육청";
    private static final String ULSAN = "울산광역시교육청";
    private static final String DAEGU = "대구광역시교육청";
    private static final String DAEJEON = "대전광역시교육청";
    private static final String GWANGJU = "광주광역시교육청";
    private static final String SEJONG = "세종특별자치시교육청";
    private static final String JEJU = "제주특별자치도교육청";
    private static final String KWANGWON = "강원도교육청";
    private static final String GYEONGGI = "경기도교육청";
    private static final String CHUNGNAM = "충청남도교육청";
    private static final String CHUNGBUK = "충청북도교육청";
    private static final String JEONNAM = "전라남도교육청";
    private static final String JEONBUK = "전라북도교육청";
    private static final String GYEONGNAM = "경상남도교육청";
    private static final String GYEONGBUK = "경상북도교육청";

    private static String location;

    public static String selectEduLocation(String eduLocation) {
        switch (eduLocation) {
            case SEOUL: return Constants.SEOUL; // 서울
            case BUSAN: return Constants.BUSAN; // 부산
            case INCHEON: return location = Constants.INCHEON; // 인천
            case ULSAN: return location = Constants.ULSAN; // 울산
            case DAEGU: return location = Constants.DAEGU; // 대구
            case DAEJEON: return location = Constants.DAEJEON; // 대전
            case GWANGJU: return location = Constants.GWANGJU; // 광주
            case SEJONG: return location = Constants.SEJONG; // 세종
            case JEJU: return location = Constants.JEJU; // 제주
            case KWANGWON: return location = Constants.KWANGWON; // 강원
            case GYEONGGI: return location = Constants.GYEONGGI; // 경기
            case CHUNGNAM: return location = Constants.CHUNGNAM; // 충남
            case CHUNGBUK: return location = Constants.CHUNGBUK; // 충북
            case JEONNAM: return location = Constants.JEONNAM; // 전남
            case JEONBUK: return location = Constants.JEONBUK; // 전북
            case GYEONGNAM: return location = Constants.GYEONGNAM; // 경남
            case GYEONGBUK: return location = Constants.GYEONGBUK; // 경북
            default: return null;
        }
    }
}
