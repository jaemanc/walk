package com.org.walk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseLatParseTest {

    public static void main (String[] args) throws ParseException, JsonProcessingException {

        String response = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"FeatureHHHHHHHHH\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          14126476,\n" +
                "          4519670\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"totalDistance\": 725,\n" +
                "        \"totalTime\": 576,\n" +
                "        \"index\": 0,\n" +
                "        \"pointIndex\": 0,\n" +
                "        \"name\": \"\",\n" +
                "        \"description\": \"성암로 을 따라 410m 이동\",\n" +
                "        \"direction\": \"\",\n" +
                "        \"nearPoiName\": \"\",\n" +
                "        \"nearPoiX\": \"0.0\",\n" +
                "        \"nearPoiY\": \"0.0\",\n" +
                "        \"intersectionName\": \"\",\n" +
                "        \"facilityType\": \"11\",\n" +
                "        \"facilityName\": \"\",\n" +
                "        \"turnType\": 200,\n" +
                "        \"pointType\": \"SP\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            14126476,\n" +
                "            4519670\n" +
                "          ],\n" +
                "          [\n" +
                "            14126466,\n" +
                "            4519678\n" +
                "          ],\n" +
                "          [\n" +
                "            14126448,\n" +
                "            4519694\n" +
                "          ],\n" +
                "          [\n" +
                "            14126409,\n" +
                "            4519720\n" +
                "          ],\n" +
                "          [\n" +
                "            14126401,\n" +
                "            4519726\n" +
                "          ],\n" +
                "          [\n" +
                "            14126374,\n" +
                "            4519749\n" +
                "          ],\n" +
                "          [\n" +
                "            14126307,\n" +
                "            4519806\n" +
                "          ],\n" +
                "          [\n" +
                "            14126205,\n" +
                "            4519891\n" +
                "          ],\n" +
                "          [\n" +
                "            14126089,\n" +
                "            4519994\n" +
                "          ],\n" +
                "          [\n" +
                "            14126080,\n" +
                "            4520002\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 1,\n" +
                "        \"lineIndex\": 0,\n" +
                "        \"name\": \"성암로\",\n" +
                "        \"description\": \"성암로, 410m\",\n" +
                "        \"distance\": 410,\n" +
                "        \"time\": 322,\n" +
                "        \"roadType\": 21,\n" +
                "        \"categoryRoadType\": 0,\n" +
                "        \"facilityType\": \"11\",\n" +
                "        \"facilityName\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          14126080,\n" +
                "          4520002\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 2,\n" +
                "        \"pointIndex\": 1,\n" +
                "        \"name\": \"\",\n" +
                "        \"description\": \"좌측 횡단보도 후 보행자도로 을 따라 14m 이동 \",\n" +
                "        \"direction\": \"\",\n" +
                "        \"nearPoiName\": \"\",\n" +
                "        \"nearPoiX\": \"0.0\",\n" +
                "        \"nearPoiY\": \"0.0\",\n" +
                "        \"intersectionName\": \"\",\n" +
                "        \"facilityType\": \"15\",\n" +
                "        \"facilityName\": \"\",\n" +
                "        \"turnType\": 212,\n" +
                "        \"pointType\": \"GP\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            14126080,\n" +
                "            4520002\n" +
                "          ],\n" +
                "          [\n" +
                "            14126068,\n" +
                "            4519988\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 3,\n" +
                "        \"lineIndex\": 1,\n" +
                "        \"name\": \"보행자도로\",\n" +
                "        \"description\": \"보행자도로, 14m\",\n" +
                "        \"distance\": 14,\n" +
                "        \"time\": 9,\n" +
                "        \"roadType\": 21,\n" +
                "        \"categoryRoadType\": 0,\n" +
                "        \"facilityType\": \"15\",\n" +
                "        \"facilityName\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          14126068,\n" +
                "          4519988\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 4,\n" +
                "        \"pointIndex\": 2,\n" +
                "        \"name\": \"매일경제\",\n" +
                "        \"description\": \"매일경제 에서 우회전 후 성암로 을 따라 232m 이동 \",\n" +
                "        \"direction\": \"\",\n" +
                "        \"nearPoiName\": \"매일경제\",\n" +
                "        \"nearPoiX\": \"0.0\",\n" +
                "        \"nearPoiY\": \"0.0\",\n" +
                "        \"intersectionName\": \"\",\n" +
                "        \"facilityType\": \"11\",\n" +
                "        \"facilityName\": \"\",\n" +
                "        \"turnType\": 13,\n" +
                "        \"pointType\": \"GP\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            14126068,\n" +
                "            4519988\n" +
                "          ],\n" +
                "          [\n" +
                "            14126024,\n" +
                "            4520024\n" +
                "          ],\n" +
                "          [\n" +
                "            14125980,\n" +
                "            4520058\n" +
                "          ],\n" +
                "          [\n" +
                "            14125948,\n" +
                "            4520082\n" +
                "          ],\n" +
                "          [\n" +
                "            14125935,\n" +
                "            4520092\n" +
                "          ],\n" +
                "          [\n" +
                "            14125854,\n" +
                "            4520154\n" +
                "          ],\n" +
                "          [\n" +
                "            14125846,\n" +
                "            4520160\n" +
                "          ],\n" +
                "          [\n" +
                "            14125838,\n" +
                "            4520151\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 5,\n" +
                "        \"lineIndex\": 2,\n" +
                "        \"name\": \"성암로\",\n" +
                "        \"description\": \"성암로, 232m\",\n" +
                "        \"distance\": 232,\n" +
                "        \"time\": 196,\n" +
                "        \"roadType\": 21,\n" +
                "        \"categoryRoadType\": 0,\n" +
                "        \"facilityType\": \"11\",\n" +
                "        \"facilityName\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          14125838,\n" +
                "          4520151\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 6,\n" +
                "        \"pointIndex\": 3,\n" +
                "        \"name\": \"청진\",\n" +
                "        \"description\": \"청진 에서 횡단보도 후 보행자도로 을 따라 22m 이동 \",\n" +
                "        \"direction\": \"\",\n" +
                "        \"nearPoiName\": \"청진\",\n" +
                "        \"nearPoiX\": \"0.0\",\n" +
                "        \"nearPoiY\": \"0.0\",\n" +
                "        \"intersectionName\": \"성암삼거리\",\n" +
                "        \"facilityType\": \"15\",\n" +
                "        \"facilityName\": \"\",\n" +
                "        \"turnType\": 211,\n" +
                "        \"pointType\": \"GP\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            14125838,\n" +
                "            4520151\n" +
                "          ],\n" +
                "          [\n" +
                "            14125816,\n" +
                "            4520169\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 7,\n" +
                "        \"lineIndex\": 3,\n" +
                "        \"name\": \"보행자도로\",\n" +
                "        \"description\": \"보행자도로, 22m\",\n" +
                "        \"distance\": 22,\n" +
                "        \"time\": 15,\n" +
                "        \"roadType\": 21,\n" +
                "        \"categoryRoadType\": 0,\n" +
                "        \"facilityType\": \"15\",\n" +
                "        \"facilityName\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          14125816,\n" +
                "          4520169\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 8,\n" +
                "        \"pointIndex\": 4,\n" +
                "        \"name\": \"\",\n" +
                "        \"description\": \"좌회전 후 매봉산로 을 따라 47m 이동 \",\n" +
                "        \"direction\": \"\",\n" +
                "        \"nearPoiName\": \"\",\n" +
                "        \"nearPoiX\": \"0.0\",\n" +
                "        \"nearPoiY\": \"0.0\",\n" +
                "        \"intersectionName\": \"성암삼거리\",\n" +
                "        \"facilityType\": \"11\",\n" +
                "        \"facilityName\": \"\",\n" +
                "        \"turnType\": 12,\n" +
                "        \"pointType\": \"GP\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            14125816,\n" +
                "            4520169\n" +
                "          ],\n" +
                "          [\n" +
                "            14125779,\n" +
                "            4520122\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 9,\n" +
                "        \"lineIndex\": 4,\n" +
                "        \"name\": \"매봉산로\",\n" +
                "        \"description\": \"매봉산로, 47m\",\n" +
                "        \"distance\": 47,\n" +
                "        \"time\": 34,\n" +
                "        \"roadType\": 21,\n" +
                "        \"categoryRoadType\": 0,\n" +
                "        \"facilityType\": \"11\",\n" +
                "        \"facilityName\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          14125779,\n" +
                "          4520122\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"index\": 10,\n" +
                "        \"pointIndex\": 5,\n" +
                "        \"name\": \"도착지\",\n" +
                "        \"description\": \"도착\",\n" +
                "        \"direction\": \"\",\n" +
                "        \"nearPoiName\": \"도착지\",\n" +
                "        \"nearPoiX\": \"0.0\",\n" +
                "        \"nearPoiY\": \"0.0\",\n" +
                "        \"intersectionName\": \"도착지\",\n" +
                "        \"facilityType\": \"\",\n" +
                "        \"facilityName\": \"\",\n" +
                "        \"turnType\": 201,\n" +
                "        \"pointType\": \"EP\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

        JSONArray jsonVo = (JSONArray) jsonObject.get("features");
        System.out.println(jsonVo.get(0));


        List<Integer> arrays = new ArrayList<>();
        for (int i = 0; i < jsonVo.size(); i++) {

            JSONObject geometrys = (JSONObject) jsonVo.get(i);
            JSONObject geos = (JSONObject) jsonParser.parse(geometrys.toJSONString());
            JSONObject coordinatess = (JSONObject) geos.get("geometry");
            JSONObject coordis = (JSONObject) jsonParser.parse(coordinatess.toJSONString());
            // System.out.println(tttttt.toJSONString());
            // ==> {"coordinates":[14126068,4519988],"type":"Point"} 형식으로 나옴.
            // System.out.println(tttttt.toJSONString());
            // ==> {"coordinates":[[14126080,4520002],[14126068,4519988]],"type":"LineString"} 형식으로 나옴.


            String temp = coordis.get("coordinates").toString();

            System.out.println(temp);
        }







    }

}
