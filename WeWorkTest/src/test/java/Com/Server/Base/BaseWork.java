package Com.Server.Base;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseWork {
    private static BaseWork work;
    String token;

    public static BaseWork getInstance() {
        if (work == null) {
            System.out.println("work is null");
            work = new BaseWork();
        }
        return work;
    }

    public String getToken() {
        if (token == null) {
            System.out.println("token is null");
            token = given().
                    param("corpid", "ww003cad287a08e57a")
                    .param("corpsecret", "H1JU2AzceOW1kBExwuTRYgtwvPk4zlnMW7WArzPYwwk")
                    .when()
                    .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                    .then().log().all().body("errcode", equalTo(0))
                    .extract().response().path("access_token");
        }
        System.out.println("token------" + token);
        return token;
    }
}
