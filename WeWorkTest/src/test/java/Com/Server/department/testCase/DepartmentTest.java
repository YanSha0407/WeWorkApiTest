package Com.Server.department.testCase;
import Com.Server.department.api.Department;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;

public class DepartmentTest {
    static Department department =  new  Department();
    @BeforeAll
    public static void beforeAll(){
        //数据清理
        ArrayList<Integer> ids = department.list(department.deptID).then()
                .extract().body().path("department.findAll {d->d.parentid==" + department.deptID + " }.id");
        System.out.println(ids);
        ids.forEach(id -> department.delete(id));
    }
    @Test
    public void list() {
        department.list(department.deptID).then().body("errmsg",equalTo("ok"));
    }

    @Test
    public void add() {
        department.add("燕莎2").then().body("errmsg",equalTo("created"));
    }

    @Test
    public void delete() {

    }
}
