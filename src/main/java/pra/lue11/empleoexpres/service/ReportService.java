package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.JobHasCandidate;
import pra.lue11.empleoexpres.utils.JasperReportUtil;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author luE11 on 19/09/23
 */
@AllArgsConstructor
@Service
public class ReportService {
    private final String JOB_CANDIDATES_REPORT = "job_candidates_report";

    private JobService jobService;

    public byte[] generateJobReport(Job job, String baseUrl) throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("companyName", job.getPublisher().getCompanyName());
        params.put("companyLogoUrl", job.getPublisher().getLogoUrl()); // if not use default?
        params.put("job", job);
        params.put("candidates", job.getCandidates());
        params.put("baseUrl", baseUrl);
        /* .getCvUrl()
            .getState().getState()
            .getUpdatedAt()
            .getPerson().getFullName()
            .getPerson().getUser().getId() link a perfil?
         */
        return JasperReportUtil.generateReport(JOB_CANDIDATES_REPORT, params);
    }

}
