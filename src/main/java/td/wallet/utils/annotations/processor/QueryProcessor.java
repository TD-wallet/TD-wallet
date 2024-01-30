package td.wallet.utils.annotations.processor;

import lombok.RequiredArgsConstructor;
import td.wallet.utils.QueryTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@RequiredArgsConstructor
public class QueryProcessor<T> {
    private final QueryTemplate qt;

    private List<Field> getAnnotatedFields(Class<T> clazz) {
        return null;
    }

    private boolean isAnnotationsPresent(Field field, List<Annotation> annotations) {
        Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
        return new HashSet<>(annotations).containsAll(Arrays.stream(fieldAnnotations).toList());
    }

    private boolean isOneOfAnnotationsPresent(Field field, List<Annotation> annotations) {
        Annotation[] fieldsAnnotations = field.getDeclaredAnnotations();
        for (Annotation fieldsAnnotation : fieldsAnnotations) {
            if (annotations.contains(fieldsAnnotation)) {
                return true;
            }
        }
        return false;
    }
}
