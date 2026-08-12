package com.example.velsis.specification;

import com.example.velsis.dto.request.UserFilter;
import com.example.velsis.model.UserModel;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserModel> withFilters(UserFilter filter){
        return (root, query, criteriaBuilder) -> {
            if (filter.username() != null && !filter.username().isEmpty()) {
                return criteriaBuilder
                        .like(criteriaBuilder.lower(root.get("username")), "%" + filter.username().toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}
