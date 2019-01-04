# PHP-JSONParsingForAndroid

이 프로젝트는 ANDROID의 AsynkTask 클래스를 이용해 PHP파일의 JSON Object를 받아온 후 ListView에 출력을 하는 코드로 구성되어 있습니다.
This project consists of code that outputs JSON objects from PHP files to ListView using the AsyncTask class of ANDROID.

다음 코드는 PHP파일의 코드입니다.
The following is the code for the PHP file.


<?php
   
    if($_SERVER["REQUEST_METHOD"] == 'GET'){
    
        $result = array();
        array_push($result, array("menuName"=>"해물 순두부찌개", "price"=>8000, "isSpecial"=>false));
        array_push($result, array("menuName"=>"들깨 순두부찌개", "price"=>7000, "isSpecial"=>false));
        array_push($result, array("menuName"=>"두부전골", "price"=>9000, "isSpecial"=>false));
        array_push($result, array("menuName"=>"돼지고기 순두부찌개", "price"=>7000, "isSpecial"=>false));
        array_push($result, array("menuName"=>"물냉면", "price"=>5000, "isSpecial"=>false));
        array_push($result, array("menuName"=>"비빔냉면", "price"=>5500, "isSpecial"=>false));
        array_push($result, array("menuName"=>"잔치국수", "price"=>5000, "isSpecial"=>false));
        array_push($result, array("menuName"=>"비빔국수", "price"=>5500, "isSpecial"=>false));
        array_push($result, array("menuName"=>"순대전골", "price"=>15000, "isSpecial"=>true));
        array_push($result, array("menuName"=>"부대찌개", "price"=>15000, "isSpecial"=>false));
        //$result = json_encode($menuData, JSON_UNESCAPED_UNICODE);
        echo json_encode(array("result"=>$result), JSON_UNESCAPED_UNICODE);
    }
?>
