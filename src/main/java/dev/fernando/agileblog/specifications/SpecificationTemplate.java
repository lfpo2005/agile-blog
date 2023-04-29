package dev.fernando.agileblog.specifications;

import dev.fernando.agileblog.models.DictionaryModel;
import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}


    @And({
            @Spec(path = "title", spec = LikeIgnoreCase.class)
    })
    public interface PostSpec extends Specification<PostModel> {}

    @And({
            @Spec(path = "word", spec = LikeIgnoreCase.class)
    })
    public interface DictionarySpec extends Specification<DictionaryModel> {}
}
