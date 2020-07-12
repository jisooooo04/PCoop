package pcoop.backend.chat;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class MyApplicationContextAware  implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;  //스프링 컨테이너의 주소를 가지는애
    //얘를 통해 겟빈(의존성 검색)을 할 수 있게됨
    
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
