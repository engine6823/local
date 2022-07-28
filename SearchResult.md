# 검색결과 비교

동일 결과 비교방안 고려를 위한 검색 결과 비교

* 키워드: 원앤온리
* 주소가 같으나 장소명이 다른 경우 존재(청어람 본점, 청어람 망원점)
* 주소 시.도 표현 방법이 다르다. (서울, 서울특별시)
* 네이버는 층수까지 표시하는 경우가 있다.
* 네이버는 title에의 키워드에 매칭되는 부분 볼드(<b></b>)처리
* 좌표계가 다르다. (변환이 )
  * 카카오: EPSG:5181
  * 네이버: 카텍좌표계

# 처리 정리
1. 네이버 title의 <b></b> 제거
2. 
3. 동일 비교 (네이버 결과가 좀 더 상세) : 지번 주소를 이용하여 비교
   1. 장소명 동일 체크
   2. 카카오의 시.도(첫번째) 항목이 네이버의 시작과 같음을 확인
   3. 카카오의 두번쨰 항목 부터 내용가 네이버의 주소에 포함여부 확인


# 단건

## 카카오
```
{
    "id" : "522657206",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "",
    "addressName" : "서울 강동구 둔촌동 603-4",
    "roadAddressName" : "서울 강동구 천호대로200길 54",
    "x" : "127.1485551912356",
    "y" : "37.53459408511267",
    "placeUrl" : "http://place.map.kakao.com/522657206",
    "distance" : ""
}
```

## 네이버
```
{
    "title" : "<b>원앤온리</b>",
    "link" : "https://www.instagram.com/one.n.only_gd",
    "category" : "카페,디저트>카페",
    "description" : "",
    "address" : "서울특별시 강동구 둔촌동 603-4 지하1층",
    "roadAddress" : "서울특별시 강동구 천호대로200길 54 지하1층",
    "mapx" : 324950,
    "mapy" : 548393
}
```


# 전체
## 카카오
```
{
  "meta" : {
    "totalCount" : 18,
    "pageableCount" : 18,
    "isEnd" : false,
    "regionInfo" : null
  },
  "documents" : [ {
    "id" : "217787831",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "064-794-0117",
    "addressName" : "제주특별자치도 서귀포시 안덕면 사계리 86",
    "roadAddressName" : "제주특별자치도 서귀포시 안덕면 산방로 141",
    "x" : "126.319192490757",
    "y" : "33.2392223486155",
    "placeUrl" : "http://place.map.kakao.com/217787831",
    "distance" : ""
  }, {
    "id" : "317651320",
    "placeName" : "원앤온리",
    "categoryName" : "가정,생활 > 미용 > 피부관리",
    "categoryGroupCode" : "",
    "categoryGroupName" : "",
    "phone" : "",
    "addressName" : "서울 강남구 논현동 278-6",
    "roadAddressName" : "서울 강남구 선릉로 605",
    "x" : "127.043364844447",
    "y" : "37.5108660611963",
    "placeUrl" : "http://place.map.kakao.com/317651320",
    "distance" : ""
  }, {
    "id" : "1366475864",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "",
    "addressName" : "경기 여주시 오학동 359-6",
    "roadAddressName" : "경기 여주시 학동2길 7",
    "x" : "127.643822503206",
    "y" : "37.3033171611024",
    "placeUrl" : "http://place.map.kakao.com/1366475864",
    "distance" : ""
  }, {
    "id" : "560241790",
    "placeName" : "원앤온리커피",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "",
    "addressName" : "서울 서대문구 대현동 37-7",
    "roadAddressName" : "서울 서대문구 이화여대7길 31-4",
    "x" : "126.94379352948137",
    "y" : "37.55853529908076",
    "placeUrl" : "http://place.map.kakao.com/560241790",
    "distance" : ""
  }, {
    "id" : "522657206",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "",
    "addressName" : "서울 강동구 둔촌동 603-4",
    "roadAddressName" : "서울 강동구 천호대로200길 54",
    "x" : "127.1485551912356",
    "y" : "37.53459408511267",
    "placeUrl" : "http://place.map.kakao.com/522657206",
    "distance" : ""
  }, {
    "id" : "1272985480",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "",
    "addressName" : "충북 청주시 흥덕구 봉명동 1672",
    "roadAddressName" : "충북 청주시 흥덕구 주현로41번길 13",
    "x" : "127.458295174856",
    "y" : "36.6446386388559",
    "placeUrl" : "http://place.map.kakao.com/1272985480",
    "distance" : ""
  }, {
    "id" : "1458968252",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 카페",
    "categoryGroupCode" : "CE7",
    "categoryGroupName" : "카페",
    "phone" : "",
    "addressName" : "서울 마포구 망원동 400-2",
    "roadAddressName" : "서울 마포구 월드컵로13길 79",
    "x" : "126.9058744565",
    "y" : "37.5548684273361",
    "placeUrl" : "http://place.map.kakao.com/1458968252",
    "distance" : ""
  }, {
    "id" : "15276774",
    "placeName" : "원앤온리",
    "categoryName" : "음식점 > 간식 > 제과,베이커리",
    "categoryGroupCode" : "FD6",
    "categoryGroupName" : "음식점",
    "phone" : "",
    "addressName" : "서울 강남구 삼성동 126",
    "roadAddressName" : "서울 강남구 삼성로99길 14",
    "x" : "127.0539458847033",
    "y" : "37.509257914764206",
    "placeUrl" : "http://place.map.kakao.com/15276774",
    "distance" : ""
  }, {
    "id" : "27299817",
    "placeName" : "코오롱원앤온리타워",
    "categoryName" : "부동산 > 빌딩",
    "categoryGroupCode" : "",
    "categoryGroupName" : "",
    "phone" : "",
    "addressName" : "서울 강서구 마곡동 727-519",
    "roadAddressName" : "서울 강서구 마곡동로 110",
    "x" : "126.834964147382",
    "y" : "37.5645610068493",
    "placeUrl" : "http://place.map.kakao.com/27299817",
    "distance" : ""
  }, {
    "id" : "1722178480",
    "placeName" : "원앤온리헤어",
    "categoryName" : "가정,생활 > 미용 > 미용실",
    "categoryGroupCode" : "",
    "categoryGroupName" : "",
    "phone" : "02-332-2459",
    "addressName" : "경기 고양시 덕양구 향동동 437-3",
    "roadAddressName" : "경기 고양시 덕양구 꽃내음3길 47",
    "x" : "126.89298583582",
    "y" : "37.6042895592976",
    "placeUrl" : "http://place.map.kakao.com/1722178480",
    "distance" : ""
  } ]
}
```

## 네이버

```
{
  "lastBuildDate" : "Wed, 27 Jul 2022 02:51:04 +0900",
  "total" : 5,
  "start" : 1,
  "display" : 5,
  "items" : [ {
    "title" : "<b>원앤온리</b>커피",
    "link" : "http://instagram.com/oneandonlycoffee",
    "category" : "음식점>카페,디저트",
    "description" : "",
    "address" : "서울특별시 서대문구 대현동 37-7 1층",
    "roadAddress" : "서울특별시 서대문구 이화여대7길 31-4 1층",
    "mapx" : 306886,
    "mapy" : 551230
  }, {
    "title" : "<b>원앤온리</b>헤어살롱",
    "link" : "",
    "category" : "생활,편의>미용실",
    "description" : "",
    "address" : "서울특별시 종로구 효제동 167",
    "roadAddress" : "서울특별시 종로구 종로39길 45 1층",
    "mapx" : 312272,
    "mapy" : 552724
  }, {
    "title" : "<b>원앤온리</b>",
    "link" : "https://www.instagram.com/one.n.only_gd",
    "category" : "카페,디저트>카페",
    "description" : "",
    "address" : "서울특별시 강동구 둔촌동 603-4 지하1층",
    "roadAddress" : "서울특별시 강동구 천호대로200길 54 지하1층",
    "mapx" : 324950,
    "mapy" : 548393
  }, {
    "title" : "<b>원앤온리</b>",
    "link" : "",
    "category" : "음식점>카페,디저트",
    "description" : "",
    "address" : "서울특별시 노원구 상계동 65-55",
    "roadAddress" : "서울특별시 노원구 덕릉로115다길 10",
    "mapx" : 318780,
    "mapy" : 563210
  }, {
    "title" : "<b>원앤온리</b>",
    "link" : "http://www.instagram.com/jejuoneandonly",
    "category" : "카페,디저트>카페",
    "description" : "",
    "address" : "제주특별자치도 서귀포시 안덕면 사계리 86",
    "roadAddress" : "제주특별자치도 서귀포시 안덕면 산방로 141",
    "mapx" : 243555,
    "mapy" : 72788
  } ]
}

```

# 같은 장소, 다른 장소명
청어람 망원점
```
{
    "keyword": "청어람 망원점",
    "total": 2,
    "locals": [
        {
            "title": "청어람 본점",
            "address": "서울 마포구 망원동 482-3",
            "roadAddress": "서울 마포구 망원로 97"
        },
        {
            "title": "청어람 망원점",
            "address": "서울특별시 마포구 망원동 482-3",
            "roadAddress": "서울특별시 마포구 망원로 97"
        }
    ]
}
```