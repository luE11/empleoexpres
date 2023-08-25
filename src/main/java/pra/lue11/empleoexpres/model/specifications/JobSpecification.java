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
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author luE11 on 17/08/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobSpecification implements Specification<Job> {

    protected static final LinkedHashMap<String, Integer> DAYS_AGO_OPTIONS = new LinkedHashMap<>(4);
    protected static final LinkedHashMap<String, Integer> EXPERIENCE_OPTIONS = new LinkedHashMap<>(6);

    static {
        DAYS_AGO_OPTIONS.put("Hoy", 1);
        DAYS_AGO_OPTIONS.put("Últimos 3 días", 3);
        DAYS_AGO_OPTIONS.put("Última semana", 7);
        DAYS_AGO_OPTIONS.put("Últimos 30 días", 30);

        EXPERIENCE_OPTIONS.put("Sin experiencia", 0);
        EXPERIENCE_OPTIONS.put("Un año", 1);
        EXPERIENCE_OPTIONS.put("Dos años", 2);
        EXPERIENCE_OPTIONS.put("Cinco años", 5);
        EXPERIENCE_OPTIONS.put("Diez años", 10);
        EXPERIENCE_OPTIONS.put("Quince años", 15);
    }

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
    protected Boolean publisherExclude;
    @Override
    public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(title!=null)
            predicates.add(cb.like(root.get("title"), "%"+title+"%"));
        if(state!=null && state.compareTo("")!=0)
            predicates.add(cb.equal(root.get("state"), JobState.valueOf(state)));
        if(daysAgo!=null && daysAgo>0){
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
        if(yearsOfExperience!=null && yearsOfExperience>=0)
            predicates.add(cb.lessThanOrEqualTo(root.get("yearsOfExperience"), yearsOfExperience));
        if(jobMode!=null && jobMode.compareTo("")!=0)
            predicates.add(cb.equal(root.get("jobMode"), JobModality.valueOf(jobMode)));
        if(locationId!=null && locationId>0)
            predicates.add(cb.equal(root.get("location").get("id"), locationId));
        if(studyId!=null && studyId>0)
            predicates.add(cb.equal(root.get("profession").get("id"), studyId));
        if(publisherId!=null && publisherId>0) {
            publisherExclude = publisherExclude !=null ? publisherExclude : false;
            if (publisherExclude)
                predicates.add(cb.notEqual(root.get("publisher").get("id"), publisherId));
            else
                predicates.add(cb.equal(root.get("publisher").get("id"), publisherId));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    public boolean isEmpty(){
        return title==null && state.compareTo("ACTIVE")==0 && daysAgo==null && salaryMin==null
            && salaryMax==null && yearsOfExperience==null && jobMode==null && locationId==null
                && studyId==null && publisherId==null && publisherExclude ==null;
    }

    public static LinkedHashMap<String, Integer> getDaysAgoOptions() {
        return DAYS_AGO_OPTIONS;
    }
    public static LinkedHashMap<String, Integer> getExperienceOptions() {
        return EXPERIENCE_OPTIONS;
    }

    /**
     * Checks that max salary is greater than min salary if both are specified
     * @return
     */
    public boolean isSalaryValid(){
        if(salaryMin!=null && salaryMax!=null){
            return salaryMax >= salaryMin;
        }
        return true;
    }

    public void resetSalary(){
        this.salaryMin = null;
        this.salaryMax = null;
    }

}
