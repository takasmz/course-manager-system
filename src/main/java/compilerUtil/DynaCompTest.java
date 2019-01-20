package compilerUtil;

import java.lang.reflect.Method;
import java.util.Map;

public class DynaCompTest
{
    public static void main(String[] args) throws Exception {
        String fullName = "DynaClass";
        StringBuilder src = new StringBuilder();
        src.append("public class DynaClass {\n");
        src.append("    public String solution(String[] inputs) {\n");
        src.append("String ouput = \"\";");
        src.append(" for(int i=0;i<inputs.length;i++){ \n");
        src.append("ouput += inputs[i];}");
        src.append("        return ouput + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");
 
        System.out.println(src);
        DynamicEngine de = DynamicEngine.getInstance();
        @SuppressWarnings("unchecked")
		Map<String,Object> map =  (Map<String, Object>) de.javaCodeToObject(fullName,src.toString());
        Method method = (Method) map.get("method");
        Object instance = map.get("instance");
        System.out.println(instance);
        String[] a = new String[2];
        a[0] = "1";
        a[1] = "2";
        //a[2] = "3";
        String result = (String) method.invoke(instance,(Object) a);
        System.out.println(result);
    }
}
