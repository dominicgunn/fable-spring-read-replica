package sh.fable.blog.readrouter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import sh.fable.persistence.aop.ReadOnlyRouteInterceptor;
import sh.fable.persistence.routing.RoutingDataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadRouterApplicationTests {

	@Autowired
	private DataSource dataSource;
	private ReadOnlyRouteInterceptor readOnlyRouteInterceptor;
	private Transactional transactionalMock;
	private ProceedingJoinPoint proceedingJoinPointMock;
	private RoutingDataSource routingDataSource;

	@Before
	public void setup() {
		readOnlyRouteInterceptor = new ReadOnlyRouteInterceptor();
		transactionalMock = mock(Transactional.class);
		proceedingJoinPointMock = mock(ProceedingJoinPoint.class);
		routingDataSource = (RoutingDataSource) dataSource;
	}

	@Test
	public void replicaRoutingIsPossible() throws Throwable {
		when(transactionalMock.readOnly()).thenReturn(true);
		when(proceedingJoinPointMock.proceed()).then(invocation -> {
			assertEquals(RoutingDataSource.Route.REPLICA, routingDataSource.determineCurrentLookupKey());
			return null;
		});
		readOnlyRouteInterceptor.proceed(proceedingJoinPointMock, transactionalMock);
	}

	@Test
	public void defaultRoutingIsPossible() throws Throwable {
		when(transactionalMock.readOnly()).thenReturn(false);
		when(proceedingJoinPointMock.proceed()).then(invocation -> {
			assertNull(routingDataSource.determineCurrentLookupKey());
			return null;
		});
		readOnlyRouteInterceptor.proceed(proceedingJoinPointMock, transactionalMock);
	}

	@Test
	public void dataSourceIsClearedAgain() throws Throwable {
		RoutingDataSource.setReplicaRoute();
		assertNotNull(routingDataSource.determineCurrentLookupKey());
		readOnlyRouteInterceptor.proceed(proceedingJoinPointMock, transactionalMock);
		assertNull(routingDataSource.determineCurrentLookupKey());
	}

	@Test
	public void contextLoads() {
	}

}
