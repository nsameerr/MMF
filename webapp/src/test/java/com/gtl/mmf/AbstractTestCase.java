package com.gtl.mmf;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/applicationContext-BAO.xml",
		"file:src/main/webapp/WEB-INF/applicationContext-DAO.xml","classpath:sprg-main-cfg.xml"})
public abstract class AbstractTestCase {

}
