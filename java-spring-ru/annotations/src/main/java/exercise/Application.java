package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method: Address.class.getDeclaredMethods()) {
            if(method.isAnnotationPresent(Inspect.class)) {
                try {
                    // Выполняем метод с аннотацией LogExecutionTime
                    method.invoke(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String type = method.getReturnType().toString();
                if(type.equals("class java.lang.String")) {
                    type = type.split("[ .]")[3];
                }
                System.out.println("Method " + method.getName() + " returns a value of type " + type);
            }
        }
        // END
    }
}
