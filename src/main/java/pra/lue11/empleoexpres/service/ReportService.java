package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.utils.JasperReportUtil;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author luE11 on 19/09/23
 */
@AllArgsConstructor
@Service
public class ReportService {
    private final String JOB_CANDIDATES_REPORT = "job_candidates_report";
    private final String ALL_PUBLISHERS_REPORT = "publisher_list_report";

    private PublisherService publisherService;

    public byte[] generateJobReport(Job job, String baseUrl) throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("companyName", job.getPublisher().getCompanyName());
        params.put("companyLogoUrl", job.getPublisher().getLogoUrl()); // if not use default?
        params.put("job", job);
        params.put("candidates", job.getCandidates());
        params.put("baseUrl", baseUrl);
        return JasperReportUtil.generateReport(JOB_CANDIDATES_REPORT, params);
    }

    public byte[] generateAllPublishersReport(String baseUrl) throws SQLException, JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("baseUrl", baseUrl);
        params.put("publishers", publisherService.getAllPublishers());
        return JasperReportUtil.generateReport(ALL_PUBLISHERS_REPORT, params);
    }

}
