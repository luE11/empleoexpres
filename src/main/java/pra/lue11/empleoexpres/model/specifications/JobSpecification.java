package pra.lue11.empleoexpres.model.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import pra.lue11.empleoexpres.model.Place;
import pra.lue11.empleoexpres.model.Publisher;
import pra.lue11.empleoexpres.model.Study;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.JobState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luE11 on 17/08/23
 */
public class JobSpecification implements Specification<User> {

    protected String title;
    protected String state;
    protected Integer daysAgo;
    protected Double salaryMin;
    protected Double salaryMax;
    protected Double yearsOfExperience;
    protected String jobMode;
    protected Integer locationId;
    protected Integer studyId;
    protected Integer publisherId;
    protected Boolean publisherIn;
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(title!=null)
            predicates.add(cb.like(root.get("title"), title));
        if(state!=null)
            predicates.add(cb.equal(root.get("state"), state));
        if(daysAgo!=null){
            LocalDateTime dateOffset = LocalDateTime.now().minusDays(daysAgo);
            // Restar daysAgo a la fecha actual y comparar fecha de publicación mayor o igual a la actual
            predicates.add(cb.greaterThanOrEqualTo(root.get("pubDate"), dateOffset));
        }
        if(salaryMin!=null || salaryMax!=null)
            if(salaryMin!=null && salaryMax!=null)
                predicates.add(cb.between(root.get("salary"), salaryMin, salaryMax));
            else if(salaryMin!=null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), salaryMin));
            else
                predicates.add(cb.lessThanOrEqualTo(root.get("salary"), salaryMax));
        if(yearsOfExperience!=null)
            predicates.add(cb.greaterThanOrEqualTo(root.get("yearsOfExperience"), yearsOfExperience));
        if(jobMode!=null)
            predicates.add(cb.equal(root.get("jobMode"), jobMode));
        if(locationId!=null)
            predicates.add(cb.equal(root.get("location").get("id"), locationId));
        if(studyId!=null)
            predicates.add(cb.equal(root.get("profession").get("id"), studyId));
        if(publisherId!=null) {
            publisherIn = publisherIn!=null ? publisherIn : true;
            if (publisherIn)
                predicates.add(cb.equal(root.get("publisher").get("id"), publisherId));
            else
                predicates.add(cb.notEqual(root.get("publisher").get("id"), publisherId));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
