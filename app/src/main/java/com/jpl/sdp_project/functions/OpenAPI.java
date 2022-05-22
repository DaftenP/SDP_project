package com.jpl.sdp_project.functions;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OpenAPI {

    private static final String key = "FbibLpOcunqY9iK%2B0S4zn5XYmhAwfQ5yzZL74ykXbEq%2BQrw9w%2Fu0T0y9tu9I5cAUXWq4%2FpiRbRELSr01pbZWcA%3D%3D";

    public static ArrayList<ArrayList<String>> getXML(String word) {
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        //String en_word = URLEncoder.encode(word);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding     //지역 검색 위한 변수


        String queryUrl = "https://apis.data.go.kr/1471000/MdcCompDrugInfoService02/getMdcCompDrugList02?serviceKey=" + key + "&item_name=" + word + "&type=xml";

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, StandardCharsets.UTF_8)); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            ArrayList<String> tmp_arr = new ArrayList<>();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("INGR_NAME")) {
                            xpp.next();
                            tmp_arr.add(xpp.getText());
                        } else if (tag.equals("ITEM_NAME")) {
                            xpp.next();
                            tmp_arr.add(xpp.getText());
                        } else if (tag.equals("ENTP_NAME")) {
                            xpp.next();
                            tmp_arr.add(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if (tag.equals("item")) {
                            arr.add(tmp_arr);
                            tmp_arr.clear();
                        }

                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }


        return arr;
    }


}