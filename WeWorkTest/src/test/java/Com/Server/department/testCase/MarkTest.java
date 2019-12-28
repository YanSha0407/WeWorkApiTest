package Com.Server.department.testCase;

import Com.Server.department.api.Mark;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarkTest {
    static Mark mark = new Mark();
    static int tagid;
    static ArrayList<String> tagUserList;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("beforeEach   ------ tagid : " + tagid);
    }

    /**
     * 创建标签
     */
    @Order(1)
    @Test
    public void create() {
        tagid = mark.create("TestWork001").then().extract().path("tagid");
        System.out.println("------tagid :   " + tagid);
    }

    /**
     * 获取标签列表
     */
    @Order(2)
    @Test
    public void list() {
        mark.list().then().body("errmsg", equalTo("ok"));
        System.out.println("list ------tagid : " + tagid);
    }

    /**
     * 更新标签名字
     */
    @Order(3)
    @Test
    public void updateMarkTitle() {

        mark.updateMarkTitle(tagid, "TestWork007").then().body("errmsg", equalTo("updated"));
    }

    /**
     * 增加标签成员
     */
    @Order(4)
    @Test
    public void addMember() {
//        ArrayList<Map<String, String>> list = new ArrayList<>();
//        Map<String,String> map1 = new HashMap<>();
//        map1.put( "userid", "13001277999");
//        map1.put("name", "韩梅梅");
//        list.add(map1);
        tagUserList = new ArrayList<>();
        tagUserList.add("13001277999");
        mark.addMember(tagid, tagUserList).then().body("errmsg", equalTo("ok"));
    }

    /**
     * 获取标签成员
     */
    @Order(5)
    @Test
    public void getMember() {
        ArrayList<HashMap<String, String>> maps = mark.getMember(1).then().extract().body().path("userlist");
        mark.getMember(1).then().body("errmsg", equalTo("ok"));
        System.out.println("maps------" + maps);
    }

    /**
     * 删除标签成员
     */
    @Order(6)
    @Test
    public void deleteMember() {
        mark.deleteMember(tagid, tagUserList).then().body("errmsg", equalTo("deleted"));
    }

    /**
     * 删除标签
     */
    @Order(7)
    @Test
    public void delete() {
        mark.delete(tagid).then().body("errmsg", equalTo("deleted"));
    }

    @AfterEach
    public void afterEach() {
        System.out.println("afterEach   ------ tagid : " + tagid);
    }

    @AfterAll
    public static void afterAll() {
        //数据清理
        ArrayList<Integer> list = mark.list().then()
                .extract().body().path("taglist.tagid");
        System.out.println(list);
        if (list.size() > 0) {
            for (Integer i :
                    list) {
                mark.delete(i);
            }
        }
    }
}
