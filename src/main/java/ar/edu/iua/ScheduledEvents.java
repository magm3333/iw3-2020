package ar.edu.iua;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ar.edu.iua.authtoken.IAuthTokenBusiness;
import ar.edu.iua.business.IGraphBusiness;
import ar.edu.iua.business.exception.BusinessException;

@Configuration
@EnableScheduling
public class ScheduledEvents {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Scheduled(fixedDelay = 5000, initialDelay = 3000)
	public void dummy() {
		log.info("Ejecutando tarea....");
	}

	@Autowired
	private IAuthTokenBusiness authTokenBusiness;

	@Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
	public void purgeAuthTokens() {
		try {
			authTokenBusiness.purgeTokens();
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}
	}

	
	@Autowired
	private IGraphBusiness graphService;
	
	@Scheduled(fixedDelay = 5000, initialDelay = 1000)
	// @Scheduled(cron = " 0 0/1 * 1/1 * ? *")
	public void estados() {
		graphService.pushGraphData();
		
	}

}
