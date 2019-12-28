package Com.Server.department.api;

import Com.Server.Base.BaseWork;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Mark {

    public Response list() {
        return given().param("access_token", BaseWork.getInstance().getToken())
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/tag/list")
                .then().log().all()
                .body("errcode", equalTo(0)).extract().response();
    }

    public Response create(String name) {
        // TODO :为什么 access_token 不可以传到body里 和参数一起掺入后台。
        //  传到body 会提示 缺少access_token。
        System.out.println("getToken :  " + BaseWork.getInstance().getToken());
        HashMap<String, Object> map = new HashMap<>();
        map.put("tagname", name);
        return given().
                queryParam("access_token", BaseWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/tag/create")
                .then()
                .body("errcode", equalTo(0)).extract().response();
    }

    public Response delete(int id) {
        System.out.println("-----id:  " + id);
        return given().param("access_token", BaseWork.getInstance().getToken())
                .param("tagid", id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/tag/delete")
                .then()
                .body("errcode", equalTo(0)).extract().response();
    }

    /**
     * 更新标签名字
     */
    public Response updateMarkTitle(int id, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("tagid", id);
        map.put("tagname", name);
        return given().queryParam("access_token", BaseWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(map)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/tag/update")
                .then().log().all()
                .body("errcode", equalTo(0))
                .extract().response();
    }

    /**
     * 获取标签成员
     */
    public Response getMember(int id) {
        return given().param("access_token", BaseWork.getInstance().getToken())
                .param("tagid", id)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/tag/get")
                .then().log().all()
                .body("errcode", equalTo(0))
                .extract()
                .response();
    }

    /**
     * 增加标签成员
     */
    public Response addMember(int id, ArrayList<String> userList) {
        Map<String, Object> map = new HashMap<>();
        map.put("tagid", id);
        map.put("userlist", userList);
        return this.deleteMember(map, "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers");
    }

    /**
     * 删除标签成员
     */
    public Response deleteMember(int id, ArrayList<String > userList) {
        Map<String, Object> map = new HashMap<>();
        map.put("tagid", id);
        map.put("userlist", userList);
        return this.deleteMember(map, "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers");
    }

    private Response deleteMember(Map<String, Object> map, String url) {
        return given().queryParam("access_token", BaseWork.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(map)
                .when().log().all()
                .post(url)
                .then().log().all()
                .body("errcode", equalTo(0))
                .extract()
                .response();
    }
}
