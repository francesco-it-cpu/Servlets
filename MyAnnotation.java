import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;



@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String name();
    
    String value();
}
