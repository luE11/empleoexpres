package pra.lue11.empleoexpres.utils;

import net.sf.jasperreports.engine.*;
import java.io.InputStream;
import java.util.Map;

/**
 * @author luE11 on 19/09/23
 */
public class JasperReportUtil {

    public static byte[] generateReport(String reportName, Map<String, Object> params) throws JRException {
        InputStream in = JasperReportUtil.class.getResourceAsStream("/reports/"+reportName+".jrxml");
        // path of the jasper report
        // dynamic parameters
        JasperPrint empReport =
                JasperFillManager.fillReport
                        (
                                JasperCompileManager.compileReport(
                                        in
                                        // dynamic parameters
                                ), params,
                                new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(empReport);
    }
}