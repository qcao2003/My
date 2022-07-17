package com;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService = new UserService();

    @Test
    public void testQuery(){
        System.out.println("SUCESS！！！");
        if(userService.addUser("ABC")==1){
            System.out.println("YEAH!");
        };
    }

    @Test
    public void Info0(){
        try {
            //获取Person的Class对象
            Person person = new Person();
            Class<?> c = person.getClass();
            //判断person对象上是否有Info注解
            if (c.isAnnotationPresent(Info.class)) {
                System.out.println("Person类上配置了Info注解！");
                //获取该对象上Info类型的注解
                Info infoAnno = (Info) c.getAnnotation(Info.class);
                System.out.println("person.name: " + infoAnno.value() + ",person.isDelete: " + infoAnno.isDelete() + ",person.age: " + infoAnno.number());
            } else {
                System.out.println("Person类上没有配置Info注解！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Info(isDelete = true)
    public void Info1(){
        try{
            Class<?> c = getClass();
            Info infoAnno = (Info) c.getAnnotation(Info.class);
            System.out.println(infoAnno.value());
        }catch (Exception e){
            System.out.println("Person类上没有配置Info注解！");
        }
    }

    @Test
    public void Info2(){

    }
}
