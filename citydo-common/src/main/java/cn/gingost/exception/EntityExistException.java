package cn.gingost.exception;

import org.springframework.util.StringUtils;

/**
 * @author mm
 * @date 2018-11-23
 */
public class EntityExistException extends RuntimeException {

    public EntityExistException(Class clazz, String field) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), field));
    }

    private static String generateMessage(String entity, String field) {
        String prefixName="";
        String suffixName="";
        switch (StringUtils.capitalize(entity)){
            case "Role":
                prefixName="角色";break;
            case "Job":
                prefixName="职位";break;
            case "Menu":
                prefixName="菜单";break;
            case "User":
                prefixName="用户";break;
            case "Dept":
                prefixName="部门";break;
        }
        switch (field){
            case "username":
                suffixName="名";break;
            case "permission":
                suffixName="权限";break;
            case "phone":
                suffixName="电话号码";break;
            case "email":
                suffixName="邮箱";break;
            case "name":
                suffixName="名";break;
            case "componentName":
                suffixName="组件名";break;
        }
        //return StringUtils.capitalize(entity)+ " with " + field + " "+ val + " existed";
        return  prefixName+suffixName+""+"已经存在";


    }
}