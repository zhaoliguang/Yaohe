package runze.yaohe.util;
import java.lang.String;
import java.lang.reflect.Field;
/**
 * ��һ������ת���ɿ�����url���ݵ�����
 * @author lp
 *
 */
public class ObjectToUrlString {

	public static String objectToUrlString(Object object){ 
		String string = "";
		Field[] fields = object.getClass().getDeclaredFields();
		for(Field field:fields){
			try {
				field.setAccessible(true);
				Object temp_object =  field.get(object);
				string = string + "&" +field.getName()+"="+temp_object.toString();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return string;
	}

}
