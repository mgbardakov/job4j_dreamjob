package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {

        @Test
        public void whenAddUserThenStoreIt() throws ServletException, IOException {
            Store memStore = MemStore.instOf();
            PowerMockito.mockStatic(PsqlStore.class);
            when(PsqlStore.instOf()).thenReturn(memStore);
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            when(req.getParameter("id")).thenReturn("0");
            when(req.getParameter("name")).thenReturn("Nick Vujicic");
            when(req.getParameter("city_id")).thenReturn("0");
            new CandidateServlet().doPost(req, resp);
            assertThat(memStore.findAllCandidates().iterator().next().getName(), is("Nick Vujicic"));
        }
}