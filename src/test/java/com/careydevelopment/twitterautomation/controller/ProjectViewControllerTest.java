package com.careydevelopment.twitterautomation.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.util.Constants;

@WebAppConfiguration
//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
public class ProjectViewControllerTest {

	//@Autowired
	//WebApplicationContext wac;

	@InjectMocks
	ProjectViewController projectViewController;

	MockMvc mockMvc;

	//@Autowired
	//MockHttpSession session;

	@Mock
	View mockView;
	
	@Mock
	ProjectRepository projectRepository;

	@Mock
	ProjectUrlRepository projectUrlRepository;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		//mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc = MockMvcBuilders.standaloneSetup(projectViewController).setSingleView(mockView).build();
	}

	@Test
	public void testViewProjectWin() {
		TwitterUser owner = ControllerHelper.getBasicUser();

		try {
			Project project = new Project();
			project.setId(1l);
			project.setName("name");
			project.setOwner(owner);

			when(projectRepository.findOne(1l)).thenReturn(project);

			List<ProjectUrl> projectUrls = new ArrayList<ProjectUrl>();
			
			when(this.projectUrlRepository.findByProject(project)).thenReturn(projectUrls);
			
			mockMvc.perform(get("/projectView")
					.sessionAttr(Constants.TWITTER_ENTITY, owner)
					.param("projectId", "1")
					.accept(MediaType.TEXT_PLAIN))
					.andExpect(status().is(200))
					.andExpect(view().name("projectView"))
					.andExpect(model().attribute("project",hasProperty("id",is (1l))))
					.andExpect(model().attribute("urls", hasSize(0)));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
