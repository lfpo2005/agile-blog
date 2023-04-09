package dev.fernando.agileblog.specifications;

import dev.fernando.agileblog.models.CategoryModel;
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
            @Spec(path = "name", spec = LikeIgnoreCase.class)
    })
    public interface CategorySpec extends Specification<CategoryModel> {}

    @And({
            @Spec(path = "title", spec = LikeIgnoreCase.class)
    })
    public interface PostSpec extends Specification<PostModel> {}

    public static Specification<PostModel> postCategoryId(final UUID categoryId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<PostModel> post = root;
            Root<CategoryModel> category = query.from(CategoryModel.class);
            Expression<Collection<PostModel>> categoriesPosts = category.get("posts");
            return cb.and(cb.equal(category.get("categoryId"), categoryId), cb.isMember(post, categoriesPosts));
        };
    }
}
