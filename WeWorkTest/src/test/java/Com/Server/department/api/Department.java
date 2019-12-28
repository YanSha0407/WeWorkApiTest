package Com.Server.department.api;

import Com.Server.Base.BaseWork;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Department {
    public int deptID = 2;
    public Response list(int pID) {
        return given().param("access_token", BaseWork.getInstance().getToken())
                .param("id", pID)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .body("errcode", equalTo(0)).extract().response();
    }

    public Response add(String name, int pID) {
        HashMap<String, Object> map = new HashMap();
        map.put("name", name);
        map.put("parentid", pID);
        System.out.println(map);
        return given()
                .queryParam("access_token", BaseWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .body("errcode", equalTo(0)).extract().response();
    }

    public Response add(String name) {
        return add(name, deptID);
    }

    public Response delete(int pID) {
        return given().param("access_token", BaseWork.getInstance().getToken())
                .param("id", pID)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all()
                .body("errcode", equalTo(0)).extract().response();
    }
}
