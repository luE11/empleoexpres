package pra.lue11.empleoexpres.model.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.model.enums.JobState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luE11 on 17/08/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobSpecification implements Specification<Job> {

    protected String title;
    protected String state = "ACTIVE";
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
    public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(title!=null)
            predicates.add(cb.like(root.get("title"), "%"+title+"%"));
        if(state!=null)
            predicates.add(cb.equal(root.get("state"), JobState.valueOf(state)));
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
            predicates.add(cb.equal(root.get("jobMode"), JobModality.valueOf(jobMode)));
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
