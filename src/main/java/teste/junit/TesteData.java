package teste.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

import br.com.project.report.util.DateUtils;

public class TesteData {
	
	@Test
	public void testData() {
		try {
		    assertEquals("21062022", DateUtils.getDateAtualReportName());	
		    
		    assertEquals("2022-06-21", DateUtils.formatDateSql(Calendar.getInstance().getTime()));
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
