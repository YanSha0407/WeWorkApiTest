package Server;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestWork {
    public static String token;
    public int deptID = 2;
    @BeforeAll
    public static void beforeAll() {
        token = given().
                param("corpid", "ww003cad287a08e57a")
                .param("corpsecret", "H1JU2AzceOW1kBExwuTRYgtwvPk4zlnMW7WArzPYwwk")
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().body("errcode", equalTo(0))
                .extract().response().path("access_token");
             System.out.println("token------"+token);
    }

    @Test
    public void addDept(){
        HashMap<String,Object> map = new HashMap();
        map.put("name","燕莎1");
        map.put("parentid",deptID);
        System.out.println(map);
        given()
                .queryParam("access_token",token)
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .body("errcode", equalTo(0));
    }
}
